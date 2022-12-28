package `2022_23`

import java.io.File

fun getInput(filename: String): MutableList<Pair<Int, Int>> {
    val sol: MutableList<Pair<Int, Int>> = mutableListOf<Pair<Int, Int>>()
    val input: List<String> = File(filename).readLines()

    var row: Int = 0
    for (line in input) {
        var col: Int = 0
        for (chr in line) {
            if (chr == '#') {
                sol.add(Pair(row, col))
            }
            col += 1
        }
        row += 1
    }
    return sol
}

enum class Direction(val x: Int, val y: Int) {
    N(-1, 0),
    NE(-1, 1),
    E(0, 1),
    SE(1, 1),
    S(1, 0),
    SW(1, -1),
    W(0, -1),
    NW(-1, -1)
}

val dir: List<Pair<List<Direction>, Direction>> = listOf(
    Pair(listOf(Direction.N, Direction.NE, Direction.NW), Direction.N),
    Pair(listOf(Direction.S, Direction.SE, Direction.SW), Direction.S),
    Pair(listOf(Direction.W, Direction.NW, Direction.SW), Direction.W),
    Pair(listOf(Direction.E, Direction.NE, Direction.SE), Direction.E)
)

fun first(filename: String) {
    val elves: MutableList<Pair<Int, Int>> = getInput(filename)

    for (round in 0..9) {
        val elvesToConsider: MutableList<Pair<Int, Int>> = mutableListOf<Pair<Int, Int>>()

        for (elf in elves) {
            var shouldConsider: Boolean = false
            for (x in -1..1) {
                for (y in -1..1) {
                    if (x == 0 && y == 0) continue
                    val next = Pair(elf.first + x, elf.second + y)
                    if (next in elves) {
                        shouldConsider = true
                    }
                }
            }

            if (shouldConsider) {
                elvesToConsider.add(elf)
            }
        }

        val proposals: MutableMap<Pair<Int, Int>, List<Pair<Int, Int>>> =
            mutableMapOf<Pair<Int, Int>, List<Pair<Int, Int>>>()

        for (elf in elvesToConsider) {
            for (dirId in 0..3) {
                val d = dir[(dirId + round) % 4]

                var allPosEmpty: Boolean = true

                for (pos in d.first) {
                    val next = Pair(elf.first + pos.x, elf.second + pos.y)
                    if (next in elves) {
                        allPosEmpty = false
                    }
                }

                if (allPosEmpty) {
                    val prop = Pair(elf.first + d.second.x, elf.second + d.second.y)
                    proposals.put(prop, proposals.getOrDefault(prop, listOf()).plus(elf))
                    break
                }
            }

        }

        for ((key, value) in proposals) {
            if (value.size != 1) continue

            elves.remove(value[0])
            elves.add(key)
        }
    }

    val minX = elves.map { it.first }.min()
    val maxX = elves.map { it.first }.max()
    val minY = elves.map { it.second }.min()
    val maxY = elves.map { it.second }.max()

    val sol = (maxX - minX + 1) * (maxY - minY + 1) - elves.size
    println(sol)

}

first("small.in")
first("test.in") // 110
first("first.in")


fun second(filename: String) {
    val elves: MutableList<Pair<Int, Int>> = getInput(filename)

    var round = 0
    while (true) {
        val elvesToConsider: MutableList<Pair<Int, Int>> = mutableListOf<Pair<Int, Int>>()

        for (elf in elves) {
            var shouldConsider: Boolean = false
            for (x in -1..1) {
                for (y in -1..1) {
                    if (x == 0 && y == 0) continue
                    val next = Pair(elf.first + x, elf.second + y)
                    if (next in elves) {
                        shouldConsider = true
                    }
                }
            }

            if (shouldConsider) {
                elvesToConsider.add(elf)
            }
        }

        if (elvesToConsider.isEmpty()) {
            break
        }

        val proposals: MutableMap<Pair<Int, Int>, List<Pair<Int, Int>>> =
            mutableMapOf<Pair<Int, Int>, List<Pair<Int, Int>>>()

        for (elf in elvesToConsider) {
            for (dirId in 0..3) {
                val d = dir[(dirId + round) % 4]

                var allPosEmpty: Boolean = true

                for (pos in d.first) {
                    val next = Pair(elf.first + pos.x, elf.second + pos.y)
                    if (next in elves) {
                        allPosEmpty = false
                    }
                }

                if (allPosEmpty) {
                    val prop = Pair(elf.first + d.second.x, elf.second + d.second.y)
                    proposals.put(prop, proposals.getOrDefault(prop, listOf()).plus(elf))
                    break
                }
            }

        }

        for ((key, value) in proposals) {
            if (value.size != 1) continue

            elves.remove(value[0])
            elves.add(key)
        }
        round += 1
    }

    println(round + 1)
}

second("test.in")
second("first.in")
