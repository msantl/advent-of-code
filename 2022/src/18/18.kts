package `2022_18`

import java.io.File

data class Cube(val x: Int, val y: Int, val z: Int) {
    operator fun plus(other: Cube): Cube {
        return Cube(x + other.x, y + other.y, z + other.z)
    }
}

val directions: List<Cube> = listOf(
    Cube(1, 0, 0), Cube(-1, 0, 0),
    Cube(0, 1, 0), Cube(0, -1, 0),
    Cube(0, 0, 1), Cube(0, 0, -1)
)

fun first(filename: String) {
    val input: List<Cube> = File(filename).readLines().map { it.split(",") }
        .map { Cube(it[0].toInt(), it[1].toInt(), it[2].toInt()) }

    val cubes: MutableMap<Cube, Boolean> = mutableMapOf<Cube, Boolean>()

    var sol = 0
    for (cube in input) {
        var sidesCovered = 0
        for (dir in directions) {
            val checkForCube = cube + dir

            if (checkForCube in cubes) {
                sidesCovered += 1
            }
        }
        sol += 6 - 2 * sidesCovered
        cubes.put(cube, true)
    }

    println(sol)
}

first("test.in") // 64
first("first.in")


fun second(filename: String) {
    val input: List<Cube> = File(filename).readLines().map { it.split(",") }
        .map { Cube(it[0].toInt(), it[1].toInt(), it[2].toInt()) }

    val cubes: MutableMap<Cube, Boolean> = mutableMapOf<Cube, Boolean>()
    val checkIfAirBubble: MutableList<Pair<Cube, Int>> = mutableListOf<Pair<Cube, Int>>()

    var sol = 0
    for (cube in input) {
        var sidesCovered = 0
        for (dir in directions) {
            val checkForCube = cube + dir

            if (checkForCube in cubes) {
                sidesCovered += 1
            }
        }
        sol += 6 - 2 * sidesCovered
        cubes.put(cube, true)
    }

    val dist: MutableMap<Cube, Int> = mutableMapOf<Cube, Int>()

    for (cube in input) {
        for (dir in directions) {
            val checkForCube = cube + dir
            if (checkForCube !in cubes) {
                checkIfAirBubble.add(Pair(checkForCube, 0))
            }
        }
    }

    val airBubbles: MutableList<Cube> = mutableListOf<Cube>()

    while (!checkIfAirBubble.isEmpty()) {
        var (cube, distance) = checkIfAirBubble.removeAt(0)
        if (cube in dist) continue

        dist.put(cube, distance)

        if (distance > 30) {
            // can't move, not an air bubble
            continue
        }

        var canMove: Boolean = false

        for (dir in directions) {
            val next = cube + dir
            if (next in cubes) continue

            if (next !in dist) {
                checkIfAirBubble.add(Pair(next, distance + 1))
                canMove = true
            }
        }

        if (!canMove) {
            airBubbles.add(cube)
        }
    }

    val tracker: MutableSet<Cube> = mutableSetOf<Cube>()

    for (airBubble in airBubbles) {
        val cubesInBubble: MutableList<Cube> = mutableListOf<Cube>()
        val queue: MutableList<Pair<Cube, Int>> = mutableListOf<Pair<Cube, Int>>()

        dist.clear()
        dist.put(airBubble, 0)
        queue.add(Pair(airBubble, 0))

        while (!queue.isEmpty()) {
            val (cube, distance) = queue.removeAt(0)

            if (distance > 30) {
                cubesInBubble.clear()
                break
            }
            cubesInBubble.add(cube)

            for (dir in directions) {
                val next = cube + dir

                if (next in cubes) continue
                if (dist.getOrDefault(next, Int.MAX_VALUE) <= distance + 1) continue

                queue.add(Pair(next, distance + 1))
                dist.put(next, distance + 1)
            }
        }

        for (cube in cubesInBubble) {
            if (cube in tracker) continue

            tracker.add(cube)

            for (dir in directions) {
                val next = cube + dir

                if (next in cubes) {
                    sol -= 1
                }
            }
        }
    }

    println(sol)
}

second("test.in") // 58
second("first.in")
