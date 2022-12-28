package `2022_12`

import java.io.File

val DIR = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))

fun bfs(
    start: Pair<Int, Int>,
    goal: Pair<Int, Int>,
    grid: MutableMap<Pair<Int, Int>, Int>,
    R: Int,
    S: Int,
): Int {
    val dist: MutableMap<Pair<Int, Int>, Int> = mutableMapOf<Pair<Int, Int>, Int>()
    val queue: MutableList<Pair<Int, Int>> = mutableListOf<Pair<Int, Int>>()

    queue.add(start)
    dist.put(start, 0)

    while (queue.size > 0) {
        val curr = queue.removeAt(0)
        val currDist = dist.getValue(curr)

        if (curr == goal) {
            return currDist
        }

        for (dir in DIR) {
            val next = Pair(curr.first + dir.first, curr.second + dir.second)
            if (next.first < 0 || next.first >= R) continue
            if (next.second < 0 || next.second >= S) continue

            if (grid.getValue(next) - grid.getValue(curr) > 1) continue

            if (next !in dist || dist.getValue(next) > currDist + 1) {
                dist.put(next, currDist + 1)
                queue.add(next)
            }
        }
    }
    throw Exception("Couln't find path from $start to $goal")
}

fun first(filename: String) {
    val input: List<String> = File(filename).readLines()

    val grid: MutableMap<Pair<Int, Int>, Int> = mutableMapOf<Pair<Int, Int>, Int>()
    var start: Pair<Int, Int>? = null
    var end: Pair<Int, Int>? = null

    for (i in 0..input.size - 1) {
        for (j in 0..input[i].length - 1) {
            val chr = if (input[i][j] == 'S') {
                start = Pair(i, j)
                'a'
            } else if (input[i][j] == 'E') {
                end = Pair(i, j)
                'z'
            } else {
                input[i][j]
            }
            grid.put(Pair(i, j), chr.minus('a'))
        }
    }

    val sol = bfs(start!!, end!!, grid, input.size, input[0].length)
    println(sol)
}

first("test.in") // 31
first("first.in") // 339


fun second(filename: String) {
    val input: List<String> = File(filename).readLines()

    val grid: MutableMap<Pair<Int, Int>, Int> = mutableMapOf<Pair<Int, Int>, Int>()
    var starts: MutableList<Pair<Int, Int>> = mutableListOf<Pair<Int, Int>>()
    var end: Pair<Int, Int>? = null

    for (i in 0..input.size - 1) {
        for (j in 0..input[i].length - 1) {
            val chr = if (input[i][j] == 'S') {
                starts.add(Pair(i, j))
                'a'
            } else if (input[i][j] == 'E') {
                end = Pair(i, j)
                'z'
            } else if (input[i][j] == 'a') {
                starts.add(Pair(i, j))
                'a'
            } else {
                input[i][j]
            }
            grid.put(Pair(i, j), chr.minus('a'))
        }
    }

    var sol = Int.MAX_VALUE
    for (start in starts) {
        try {
            val curr = bfs(start!!, end!!, grid, input.size, input[0].length)
            if (curr < sol) {
                sol = curr
            }
        } catch (e: Exception) {
            // skip
        }
    }
    println(sol)
}

second("test.in") // 29
second("first.in") // 332
