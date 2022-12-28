package `2022_16`

import java.io.File
import java.util.PriorityQueue

fun parseInput(filename: String): MutableMap<String, Pair<Int, List<String>>> {
    val graph: MutableMap<String, Pair<Int, List<String>>> = mutableMapOf<String, Pair<Int, List<String>>>()
    val input: List<String> = File(filename).readLines()
    val lineRegex = Regex("Valve ([A-Z]+) has flow rate=([0-9]+); tunnels? leads? to valves? (([A-Z],? ?)+)")

    for (line in input) {
        val (valve, rate, neighbours) = lineRegex.find(line)!!.destructured
        val neighboursList = neighbours.split(", ")

        graph.put(valve, Pair(rate.toInt(), neighboursList))
    }

    return graph
}

val bio: MutableMap<Triple<Long, Int, Int>, Int> = mutableMapOf<Triple<Long, Int, Int>, Int>()

fun getValveId(graph: MutableMap<String, Pair<Int, List<String>>>, valve: String): Int {
    return graph.keys.withIndex().filter { it.value == valve }.first().index
}

fun dfs(
    graph: MutableMap<String, Pair<Int, List<String>>>,
    openValves: Long,
    valve: String,
    time: Int,
): Int {
    if (time == 0) return 0
    val (rate, neighbours) = graph.getValue(valve)

    val valveId = getValveId(graph, valve)

    val key = Triple(openValves, valveId, time)
    if (key in bio) return bio[key]!!

    // If valve is not open, try opening it and stay at the same position
    var sol = if (openValves and (1L shl valveId) != 0L || rate == 0) Int.MIN_VALUE else dfs(
        graph,
        openValves or (1L shl valveId),
        valve,
        time - 1,
    ) + (time - 1) * rate

    for (next in neighbours) {
        // Try moving to all neighbours
        sol = Math.max(
            sol, dfs(
                graph,
                openValves,
                next,
                time - 1,
            )
        )
    }

    bio.put(key, sol)
    return sol
}

fun dfs2(
    graph: MutableMap<String, Pair<Int, List<String>>>,
    distance: Map<Pair<String, String>, Int>,
    openValves: Long,
    valve1: String,
    time1: Int,
    valve2: String,
    time2: Int,
): Int {
    val v1 = getValveId(graph, valve1)
    val v2 = getValveId(graph, valve2)

    val key = Triple(openValves, v1 * 57 + v2, time1 * 27 + time2)

    if (key in bio) return bio.getValue(key)
    var sol = 0
    var temp = 0

    for (n1 in distance.filter { it.key.first == valve1 }.map { it.key.second }) {
        if (n1 == valve2) continue

        val dist = distance.getValue(Pair(valve1, n1))
        if (time1 <= dist) continue

        val n1Id = getValveId(graph, n1)
        if (openValves and (1L shl n1Id) != 0L) continue

        temp = dfs2(
            graph,
            distance,
            openValves or (1L shl n1Id),
            n1,
            time1 - dist - 1,
            valve2,
            time2
        ) + (time1 - dist - 1) * graph.getValue(n1).first

        if (temp > sol) sol = temp
    }

    for (n2 in distance.filter { it.key.first == valve2 }.map { it.key.second }) {
        if (n2 == valve1) continue

        val dist = distance.getValue(Pair(valve2, n2))
        if (time2 <= dist) continue

        val n2Id = getValveId(graph, n2)
        if (openValves and (1L shl n2Id) != 0L) continue

        temp = dfs2(
            graph,
            distance,
            openValves or (1L shl n2Id),
            valve1,
            time1,
            n2,
            time2 - dist - 1
        ) + (time2 - dist - 1) * graph.getValue(n2).first

        if (temp > sol) sol = temp
    }

    bio.put(key, sol)
    return sol
}

data class State(
    val score: Int,
    val openValves: Long,
    val valve1: String,
    val valve2: String,
    val time1: Int,
    val time2: Int
) : Comparable<State> {
    override fun compareTo(other: State): Int {
        if (score < other.score) return 1
        else if (score > other.score) return -1
        else return 0
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is State) return false
        if (
            score == other.score &&
            openValves == other.openValves &&
            valve1 == other.valve1 &&
            valve2 == other.valve2 &&
            time1 == other.time1 &&
            time2 == other.time2
        ) return true
        return false
    }
}

fun dijkstra(
    graph: MutableMap<String, Pair<Int, List<String>>>,
    distance: Map<Pair<String, String>, Int>,
    openValves: Long,
    valve1: String,
    time1: Int,
    valve2: String,
    time2: Int,
): Int {
    bio.clear()
    val pq: PriorityQueue<State> = PriorityQueue()

    val start = State(0, 0L, valve1, valve2, time1, time2)
    pq.add(start)

    var sol = 0

    while (true) {
        val state = pq.peek()
        if (state == null) break

        sol = Math.max(sol, state.score)

        pq.remove(state)



        for (n1 in distance.filter { it.key.first == state.valve1 }.map { it.key.second }) {
            if (n1 == state.valve2) continue

            val dist = distance.getValue(Pair(state.valve1, n1))
            if (state.time1 <= dist) continue

            val n1Id = getValveId(graph, n1)
            if (state.openValves and (1L shl n1Id) != 0L) continue

            val v1 = getValveId(graph, n1)
            val v2 = getValveId(graph, state.valve2)

            val newScore = state.score + (state.time1 - dist - 1) * graph.getValue(n1).first
            val key = Triple(openValves, v1 * 57 + v2, (state.time1 - dist - 1) * 27 + state.time2)
            if (bio.getOrDefault(key, 0) >= newScore) continue
            bio.put(key, newScore)

            pq.add(
                State(
                    state.score + (state.time1 - dist - 1) * graph.getValue(n1).first,
                    state.openValves or (1L shl n1Id),
                    n1,
                    state.valve2,
                    state.time1 - dist - 1,
                    state.time2
                )
            )
        }

        for (n2 in distance.filter { it.key.first == state.valve2 }.map { it.key.second }) {
            if (n2 == state.valve1) continue

            val dist = distance.getValue(Pair(state.valve2, n2))
            if (state.time2 <= dist) continue

            val n2Id = getValveId(graph, n2)
            if (state.openValves and (1L shl n2Id) != 0L) continue

            val v1 = getValveId(graph, state.valve1)
            val v2 = getValveId(graph, n2)

            val newScore = state.score + (state.time2 - dist - 1) * graph.getValue(n2).first

            val key = Triple(openValves, v1 * 57 + v2, state.time1 * 27 + (state.time2 - dist - 1))
            if (bio.getOrDefault(key, 0) >= newScore) continue
            bio.put(key, newScore)

            pq.add(
                State(
                    newScore,
                    state.openValves or (1L shl n2Id),
                    state.valve1,
                    n2,
                    state.time1,
                    state.time2 - dist - 1
                )
            )
        }
    }

    return sol
}

fun first(filename: String) {
    val graph = parseInput(filename)
    bio.clear()

    println(dfs(graph, 0L, "AA", 30))
}

first("test.in") // 1651
first("first.in")

fun second(filename: String) {
    val graph = parseInput(filename)

    val distance: MutableMap<Pair<String, String>, Int> = mutableMapOf<Pair<String, String>, Int>()

    for (i in graph.keys) {
        distance.put(Pair(i, i), 0)
        for (j in graph.getValue(i).second) {
            distance.put(Pair(i, j), 1)
        }
    }

    val MAX = 100000
    for (k in graph.keys) {
        for (i in graph.keys) {
            for (j in graph.keys) {
                if (
                    distance.getOrDefault(Pair(i, j), MAX) >
                    distance.getOrDefault(Pair(i, k), MAX) +
                    distance.getOrDefault(Pair(k, j), MAX)
                ) {
                    distance.put(
                        Pair(i, j),
                        distance.getOrDefault(Pair(i, k), MAX) +
                                distance.getOrDefault(Pair(k, j), MAX)
                    )
                }
            }
        }
    }

    val nonNullDistance = distance.filter { graph.getValue(it.key.second).first > 0 && it.key.first != it.key.second }
    println(
        dijkstra(
            graph,
            nonNullDistance,
            0L,
            "AA",
            26,
            "AA",
            26
        )
    )
}

second("test.in") // 1707
second("first.in")

