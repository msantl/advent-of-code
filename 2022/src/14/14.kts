package `2022_14`

import java.io.File

fun printGrid(grid: MutableMap<Pair<Int, Int>, Char>) {
    for (x in 0..10) {
        for (y in 493..504) {
            print(grid.getOrDefault(Pair(x, y), '.'))
        }
        println()
    }
}

val DIR: List<Pair<Int, Int>> = listOf(Pair(1, 0), Pair(1, -1), Pair(1, 1))

fun first(filename: String) {
    val input: List<String> = File(filename).readLines()

    val grid: MutableMap<Pair<Int, Int>, Char> = mutableMapOf<Pair<Int, Int>, Char>()

    var lowestPoint = 0

    for (line in input) {
        val parts: List<String> = line.split(" -> ")
        for (i in 1..parts.size - 1) {
            val from = parts[i - 1].split(",").map { it.toInt() }
            val to = parts[i].split(",").map { it.toInt() }

            if (from[0] != to[0]) {
                var x = if (from[0] < to[0]) from[0] else to[0]
                var y = if (from[0] > to[0]) from[0] else to[0]

                for (k in x..y) {
                    grid.put(Pair(from[1], k), '#')
                }

                if (from[1] > lowestPoint) lowestPoint = from[1]

            } else if (from[1] != to[1]) {
                var x = if (from[1] < to[1]) from[1] else to[1]
                var y = if (from[1] > to[1]) from[1] else to[1]

                for (k in x..y) {
                    grid.put(Pair(k, from[0]), '#')

                    if (k > lowestPoint) lowestPoint = k
                }
            }
        }
    }


    var flake = 0
    var flakePos: Pair<Int, Int>? = null
    var overflowDetected = false
    while (!overflowDetected) {
        if (flakePos == null) {
            flakePos = Pair(0, 500)
            flake += 1
        }

        while (true) {
            var canMove = false
            for (dir in DIR) {
                val next = Pair(flakePos!!.first + dir.first, flakePos!!.second + dir.second)
                if (grid.getOrDefault(next, '.') == '.') {
                    canMove = true
                    flakePos = next
                    break
                }
            }

            if (!canMove) {
                grid.put(flakePos!!, 'o')
                flakePos = null
                break
            }

            if (flakePos!!.first > lowestPoint) {
                overflowDetected = true
                break
            }
        }

//        printGrid(grid)
    }

    println(flake - 1)
}

first("test.in") // 24
first("first.in")


fun second(filename: String) {
    val input: List<String> = File(filename).readLines()

    val grid: MutableMap<Pair<Int, Int>, Char> = mutableMapOf<Pair<Int, Int>, Char>()

    var lowestPoint = 0

    for (line in input) {
        val parts: List<String> = line.split(" -> ")
        for (i in 1..parts.size - 1) {
            val from = parts[i - 1].split(",").map { it.toInt() }
            val to = parts[i].split(",").map { it.toInt() }

            if (from[0] != to[0]) {
                var x = if (from[0] < to[0]) from[0] else to[0]
                var y = if (from[0] > to[0]) from[0] else to[0]

                for (k in x..y) {
                    grid.put(Pair(from[1], k), '#')
                }

                if (from[1] > lowestPoint) lowestPoint = from[1]

            } else if (from[1] != to[1]) {
                var x = if (from[1] < to[1]) from[1] else to[1]
                var y = if (from[1] > to[1]) from[1] else to[1]

                for (k in x..y) {
                    grid.put(Pair(k, from[0]), '#')

                    if (k > lowestPoint) lowestPoint = k
                }
            }
        }
    }


    // Bottom of the cave
    lowestPoint += 2

    var flake = 0
    var flakePos: Pair<Int, Int>? = null
    while (true) {
        if (flakePos == null) {
            val newFlake = Pair(0, 500)
            if (grid.getOrDefault(newFlake, '.') == '.') {
                flakePos = newFlake
                flake += 1
            } else {
                break
            }
        }

        while (true) {
            var canMove = false
            for (dir in DIR) {
                val next = Pair(flakePos!!.first + dir.first, flakePos!!.second + dir.second)
                if (next.first < lowestPoint && grid.getOrDefault(next, '.') == '.') {
                    canMove = true
                    flakePos = next
                    break
                }
            }

            if (!canMove) {
                grid.put(flakePos!!, 'o')
                flakePos = null
                break
            }
        }

    }

    println(flake)
}

second("test.in") // 93
second("first.in")
