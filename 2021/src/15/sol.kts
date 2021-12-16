package `15`

import java.io.File

fun readInput(filename: String): Map<Pair<Int, Int>, Int> {
    val result = mutableMapOf<Pair<Int, Int>, Int>()
    File(filename).readLines().withIndex().forEach { row ->
        row.value.withIndex().forEach { col ->
            result[Pair(row.index, col.index)] = col.value.digitToInt()
        }
    }
    return result
}

val comparator = Comparator<Pair<Int, Pair<Int, Int>>> { a, b ->
    if (a.first > b.first) 1 else if (a.first < b.first) -1 else {
        if (a.second.first > b.second.first || (a.second.first == b.second.first && a.second.second > b.second.second)) 1
        else if (a.second.first < b.second.first || (a.second.first == b.second.first && a.second.second < b.second.second)) -1
        else 0
    }
}

val deltas = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))

fun solve(input: Map<Pair<Int, Int>, Int>): Int {
    val rows = input.keys.maxOf { it.first }
    val cols = input.keys.maxOf { it.second }

    val pQueue = sortedSetOf(comparator)
    val bestPath = mutableMapOf<Pair<Int, Int>, Int>()

    pQueue.add(Pair(0, Pair(0, 0)))
    bestPath[Pair(0, 0)] = 0

    while (pQueue.isNotEmpty()) {
        val curr = pQueue.first()
        pQueue.remove(curr)

        val currBest = curr.first
        val currRow = curr.second.first
        val currCol = curr.second.second

        if (currRow == rows && currCol == cols) {
            return currBest
        }

        for (k in 0..3) {
            val next =
                Pair(currRow + deltas[k].first, currCol + deltas[k].second)
            if (next.first < 0 || next.first > rows || next.second < 0 || next.second > cols) continue
            val nextBest = currBest + input.getValue(next)

            if (bestPath.getOrDefault(next, Int.MAX_VALUE) > nextBest) {
                bestPath[next] = nextBest
                pQueue.add(Pair(nextBest, next))
            }
        }
    }

    return Int.MAX_VALUE
}

fun first(filename: String) {
    val input = readInput(filename)
    println(solve(input))
}

first("test.in") // 40
first("input.in") // 513

fun enlargeInput(
    input: Map<Pair<Int, Int>, Int>,
    factor: Int
): Map<Pair<Int, Int>, Int> {
    val result = mutableMapOf<Pair<Int, Int>, Int>()

    result.putAll(input)

    val rows = input.keys.maxOf { it.first } + 1
    val cols = input.keys.maxOf { it.second } + 1

    for (fx in 0 until factor) {
        for (fy in 0 until factor) {
            if (fx == 0 && fy == 0) continue
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    val currRow = rows * fx + i
                    val currCol = cols * fy + j
                    var prevRow: Int
                    var prevCol: Int

                    if (fx == 0 && fy > 0) {
                        prevRow = i
                        prevCol = cols * (fy - 1) + j
                    } else if (fx > 0 && fy == 0) {
                        prevRow = rows * (fx - 1) + i
                        prevCol = j
                    } else {
                        prevRow = currRow
                        prevCol = cols * (fy - 1) + j
                    }

                    val prevValue = result.getValue(Pair(prevRow, prevCol))
                    result[Pair(currRow, currCol)] = if (prevValue == 9) 1 else prevValue + 1
                }
            }
        }
    }
    return result
}

fun second(filename: String) {
    val input = readInput(filename)
    val realInput = enlargeInput(input, 5)
    println(solve(realInput))
}

second("test.in") // 315
second("input.in") // 2853
