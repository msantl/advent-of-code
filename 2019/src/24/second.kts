import java.io.File

fun getIJ(v: Int, N: Int): Pair<Int, Int> {
    return (v / N) to (v % N)
}

fun getV(i: Int, j: Int, N: Int): Int {
    return (i * N + j)
}

fun getNeighbours(v: Int, level: Int, N: Int): List<Pair<Int, Int>> {
    val result: MutableList<Pair<Int, Int>> = mutableListOf()
    val dir: List<Pair<Int, Int>> = listOf(0 to 1, 1 to 0, 0 to -1, -1 to 0)

    val (i, j) = getIJ(v, N)

    for (d in dir) {
        val next = (i + d.first) to (j + d.second)

        if (getV(next.first, next.second, N) == 12) {
            // Middle tile -> level + 1
            if (d.first == 1) {
                for (x in 0..4) {
                    result.add((getV(0, x, 5)) to (level + 1))
                }
            } else if (d.first == -1) {
                for (x in 0..4) {
                    result.add((getV(4, x, 5)) to (level + 1))
                }
            } else if (d.second == 1) {
                for (x in 0..4) {
                    result.add((getV(x, 0, 5)) to (level + 1))
                }
            } else if (d.second == -1) {
                for (x in 0..4) {
                    result.add((getV(x, 4, 5)) to (level + 1))
                }
            }
        } else if (next.first < 0 || next.first >= N || next.second < 0 || next.second >= N) {
            // Outer tiles -> level - 1
            result.add((12 + d.first * 5 + d.second) to (level - 1))
        } else {
            result.add(getV(next.first, next.second, 5) to level)
        }
    }

    return result.toList()
}

fun main() {
    var map: MutableMap<Triple<Int, Int, Int>, Boolean> = mutableMapOf()

    File("second.in").readLines().withIndex().forEach { it ->
        it.value.trim().withIndex()
            .forEach { row -> map.put(Triple(it.index, row.index, 0), if (row.value == '#') true else false) }
    }

    var minLevel = -200
    var maxLevel = 200

    for (iteration in 0 until 200) {
        val newMap: MutableMap<Triple<Int, Int, Int>, Boolean> = mutableMapOf()

        for (level in minLevel..maxLevel) {
            for (i in 0 until 25) {
                if (i == 12) continue

                var neighbors = 0
                val allNeighbors = getNeighbours(i, level, 5)
                for (next in allNeighbors) {
                    val (v, nlevel) = next
                    val (nx, ny) = getIJ(v, 5)

                    if (map.getOrDefault(Triple(nx, ny, nlevel), false)) {
                        neighbors += 1
                    }
                }

                val (x, y) = getIJ(i, 5)
                if (map.getOrDefault(Triple(x, y, level), false)) {
                    if (neighbors == 1) {
                        newMap.put(Triple(x, y, level), true)
                    }
                } else {
                    if (neighbors == 1 || neighbors == 2) {
                        newMap.put(Triple(x, y, level), true)
                    }
                }
            }
        }
        map = newMap
    }

    var bugs = 0
    for (level in minLevel..maxLevel) {
        for (i in 0 until 25) {
            val (x, y) = getIJ(i, 5)
            if (map.getOrDefault(Triple(x, y, level), false)) {
                bugs += 1
            }
        }
    }
    println(bugs)
}

main()