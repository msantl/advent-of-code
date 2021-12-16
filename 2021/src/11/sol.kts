package `11`

import java.io.File

fun readInput(filename: String): Map<Pair<Int, Int>, Int> {
    val result = mutableMapOf<Pair<Int, Int>, Int>()
    File(filename).readLines().withIndex()
        .forEach { row ->
            row.value.withIndex().forEach { col ->
                result[Pair(row.index, col.index)] = col.value.digitToInt()
            }
        }
    return result
}

val deltas = listOf(
    Pair(1, 0),
    Pair(0, 1),
    Pair(-1, 0),
    Pair(0, -1),
    Pair(1, 1),
    Pair(1, -1),
    Pair(-1, 1),
    Pair(-1, -1),
)

fun add(lhs: Pair<Int, Int>, rhs: Pair<Int, Int>) =
    Pair(lhs.first + rhs.first, lhs.second + rhs.second)

fun printOctopuses(octopuses: Map<Pair<Int, Int>, Int>, rows: Int, cols: Int) {
    for (i in 0..rows) {
        for (j in 0..cols) {
            print(" ${octopuses.getValue(Pair(i, j))} ")
        }
        println("")
    }
    println("")
}

fun first(filename: String) {
    val octopuses = readInput(filename)
    val rows = octopuses.keys.maxOf { it.first }
    val cols = octopuses.keys.maxOf { it.second }

    var curr = octopuses.toMutableMap()
    var flashes = 0
    for (i in 1..100) {
        val totalFlashed = mutableSetOf<Pair<Int, Int>>()
        curr.keys.forEach { curr[it] = curr.getValue(it) + 1 }

        var addedNew = true
        while (addedNew) {
            addedNew = false
            val flashThisStep =
                curr.entries.filter { it.value > 9 }.map { it.key }.toSet()
                    .minus(totalFlashed)

            if (flashThisStep.isNotEmpty()) {
                flashThisStep.forEach {
                    for (k in 0 until 8) {
                        val next = add(it, deltas[k])
                        if (next.first >= 0 && next.first <= rows && next.second >= 0 && next.second <= cols) {
                            curr[next] = curr.getValue(next) + 1
                        }
                    }
                }
                addedNew = true
                totalFlashed.addAll(flashThisStep)
            }
        }

        flashes += totalFlashed.size
        totalFlashed.forEach { curr[it] = 0 }
    }
    println(flashes)
}

//first("small.in")
first("test.in") // 1656
first("input.in") // 1775

fun second(filename: String) {
    val octopuses = readInput(filename)
    val rows = octopuses.keys.maxOf { it.first }
    val cols = octopuses.keys.maxOf { it.second }

    var curr = octopuses.toMutableMap()
    var allFlashed = false
    var stepCounter = 0

    while (!allFlashed) {
        stepCounter += 1
        val totalFlashed = mutableSetOf<Pair<Int, Int>>()
        curr.keys.forEach { curr[it] = curr.getValue(it) + 1 }

        var addedNew = true
        while (addedNew) {
            addedNew = false
            val flashThisStep =
                curr.entries.filter { it.value > 9 }.map { it.key }.toSet()
                    .minus(totalFlashed)

            if (flashThisStep.isNotEmpty()) {
                flashThisStep.forEach {
                    for (k in 0 until 8) {
                        val next = add(it, deltas[k])
                        if (next.first >= 0 && next.first <= rows && next.second >= 0 && next.second <= cols) {
                            curr[next] = curr.getValue(next) + 1
                        }
                    }
                }
                addedNew = true
                totalFlashed.addAll(flashThisStep)
            }
        }

        totalFlashed.forEach { curr[it] = 0 }

        if (totalFlashed.size == curr.entries.size) {
            allFlashed = true
        }
    }

    println(stepCounter)
}

second("test.in") // 195
second("input.in") //
