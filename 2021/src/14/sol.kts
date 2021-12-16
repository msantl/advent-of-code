package `14`

import java.io.File

data class Input(val seq: String, val template: Map<String, Char>)

fun readInput(filename: String): Input {
    val input = File(filename).readLines()
    val seq = input.first()
    val template = mutableMapOf<String, Char>()

    for (i in 2 until input.size) {
        val (from, to, _) = input[i].split(" -> ")
        template[from] = to[0]
    }
    return Input(seq, template)
}

fun first(filename: String) {
    val input = readInput(filename)
    var seq = input.seq

    for (i in 0 until 10) {
        var newSeq = ""
        for (j in seq.indices) {
            newSeq += seq.substring(j, j + 1)
            if (j + 1 >= seq.length) {
                break
            }
            val key = seq.substring(j, j + 2)
            if (input.template.containsKey(key)) {
                newSeq += input.template.getValue(key)
            }
        }
        seq = newSeq
    }

    val stats = seq.groupingBy { it }.eachCount()

    val minCnt = stats.minOf { it.value }
    val maxCnt = stats.maxOf { it.value }

    println(maxCnt - minCnt)
}

first("test.in") // 1588
first("input.in") // 2509

val cache = mutableMapOf<Pair<Pair<Char, Char>, Int>, Map<Char, Long>>()

fun solve(
    pair: Pair<Char, Char>,
    iters: Int,
    template: Map<String, Char>
): Map<Char, Long> {
    val stats = mutableMapOf<Char, Long>()

    if (iters == 0) {
        stats[pair.first] = stats.getOrDefault(pair.first, 0L) + 1
        stats[pair.second] = stats.getOrDefault(pair.second, 0L) + 1
        return stats
    }

    if (cache.containsKey(Pair(pair, iters))) {
        return cache.getValue(Pair(pair, iters))
    }

    val key = String(charArrayOf(pair.first, pair.second))
    if (template.containsKey(key)) {
        val newChar = template.getValue(key)

        val lhs = solve(Pair(pair.first, newChar), iters - 1, template)
        lhs.forEach { (t, u) -> stats[t] = stats.getOrDefault(t, 0L) + u }

        val rhs = solve(Pair(newChar, pair.second), iters - 1, template)
        rhs.forEach { (t, u) -> stats[t] = stats.getOrDefault(t, 0L) + u }

        stats[newChar] = stats.getValue(newChar) - 1
    } else {
        stats[pair.first] = stats.getOrDefault(pair.first, 0L) + 1
        stats[pair.second] = stats.getOrDefault(pair.second, 0L) + 1
    }

    cache[Pair(pair, iters)] = stats
    return stats
}

fun second(filename: String) {
    cache.clear()
    val input = readInput(filename)
    val seq = input.seq

    val stats = mutableMapOf<Char, Long>()
    for (pair in seq.zipWithNext()) {
        val newStats = solve(pair, 40, input.template)
        newStats.forEach { (t, u) -> stats[t] = stats.getOrDefault(t, 0L) + u }

        stats[pair.second] = stats.getValue(pair.second) - 1
    }

    stats[seq.last()] = stats.getValue(seq.last()) + 1

    val minCnt = stats.minOf { it.value }
    val maxCnt = stats.maxOf { it.value }

    println(maxCnt - minCnt)
}

second("test.in") // 2188189693529
second("input.in") //
