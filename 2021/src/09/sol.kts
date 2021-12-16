package `09`

import java.io.File
import kotlin.math.max

val dx = listOf(0, 0, 1, -1)
val dy = listOf(1, -1, 0, 0)

fun getHeightsFromInput(filename: String): MutableMap<Pair<Int, Int>, Int> {
    val result = mutableMapOf<Pair<Int, Int>, Int>()
    File(filename).readLines()
        .forEachIndexed { x, s ->
            s.forEachIndexed { y, c ->
                result[Pair(x, y)] = c.digitToInt()
            }
        }
    return result
}

fun first(filename: String) {
    val heights = getHeightsFromInput(filename)
    val rows = heights.keys.maxOf { it.first }
    val cols = heights.keys.maxOf { it.second }

    val lowPoints = mutableListOf<Int>()
    for (i in 0..rows) {
        for (j in 0..cols) {
            var countSmallerThan = 0
            val curr = heights.getValue(Pair(i, j))
            for (k in 0 until 4) {
                if (curr < heights.getOrDefault(
                        Pair(i + dx[k], j + dy[k]),
                        Int.MAX_VALUE
                    )
                ) {
                    countSmallerThan += 1
                }
            }
            if (countSmallerThan == 4) {
                lowPoints.add(curr)
            }
        }
    }
    println(lowPoints.sumOf { it + 1 })
}

first("test.in") // 15
first("input.in") // 570

val visited = mutableMapOf<Pair<Int, Int>, Int>()

fun bfs(
    sx: Int,
    sy: Int,
    rows: Int,
    cols: Int,
    heights: MutableMap<Pair<Int, Int>, Int>
): Int {
    val queue = mutableListOf(Pair(sx, sy))
    var basinSize = 0

    visited[Pair(sx, sy)] = 1

    while (queue.isNotEmpty()) {
        val curr = queue.first()
        queue.remove(curr)

        val (cx, cy) = curr
        basinSize += 1

        for (k in 0 until 4) {
            val nx = cx + dx[k]
            val ny = cy + dy[k]
            val next = Pair(nx, ny)

            if (nx >= 0 && ny >= 0 && nx <= rows && ny <= cols &&
                heights.getValue(next) != 9 &&
                !visited.containsKey(next)
            ) {
                queue.add(next)
                visited[next] = visited.getValue(curr) + 1
            }
        }
    }

    return basinSize
}

fun second(filename: String) {
    val heights = getHeightsFromInput(filename)
    val rows = heights.keys.maxOf { it.first }
    val cols = heights.keys.maxOf { it.second }

    val basinSizes = mutableListOf<Int>()

    for (i in 0..rows) {
        for (j in 0..cols) {
            val curr = heights.getValue(Pair(i, j))
            if (curr != 9 && !visited.containsKey(Pair(i, j))) {
                basinSizes.add(bfs(i, j, rows, cols, heights))
            }
        }
    }

    println(basinSizes.sorted().takeLast(3).fold(1) { acc, i -> acc * i })
}


second("test.in") // 1134
second("input.in") // 899392