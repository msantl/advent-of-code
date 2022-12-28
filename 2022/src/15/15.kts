package `2022_15`

import java.io.File

data class Point(val x: Int, val y: Int) {
    override fun equals(other: Any?): Boolean {
        if (other !is Point) return false
        if (other.x == x && other.y == y) return true
        return false
    }
}

fun distance(lhs: Point, rhs: Point): Int {
    return Math.abs(lhs.x - rhs.x) + Math.abs(lhs.y - rhs.y)
}

fun overlap(a: Pair<Int, Int>, b: Pair<Int, Int>): Boolean {
    val x = Math.max(a.first, b.first)
    val y = Math.min(a.second, b.second)

    return if (x <= y) true
    else false
}

val DIR = listOf(Pair(0, 1), Pair(1, 0), Pair(-1, 0), Pair(0, -1))

fun first(filename: String, rowToCheck: Int) {
    val input: List<String> = File(filename).readLines()
    val lineRegex = Regex("Sensor at x=(-?[0-9]+), y=(-?[0-9]+): closest beacon is at x=(-?[0-9]+), y=(-?[0-9]+)")

    val sensors: MutableList<Pair<Point, Int>> = mutableListOf<Pair<Point, Int>>()
    val beacons: MutableSet<Point> = mutableSetOf<Point>()

    for (line in input) {
        val (sx, sy, bx, by) = lineRegex.find(line)!!.destructured
        val sensor = Point(sx.toInt(), sy.toInt())
        val beacon = Point(bx.toInt(), by.toInt())

        sensors.add(Pair(sensor, distance(sensor, beacon)))
        beacons.add(beacon)
    }

    val intervals: MutableList<Pair<Int, Int>> = mutableListOf<Pair<Int, Int>>()

    for ((s, dist) in sensors) {
        val remainingDist = dist - Math.abs(s.y - rowToCheck)
        if (remainingDist < 0) continue
        val interval = Pair(s.x - remainingDist, s.x + remainingDist)
//        println("$s produces $interval with $dist and $remainingDist")

        intervals.add(interval)
    }


    val intervalsToSkip = mutableSetOf<Int>()
    while (true) {
        var canJoin = false

        for (i in 0..intervals.size - 1) {
            if (i in intervalsToSkip) continue
            var interval = intervals[i]
            for (j in i + 1..intervals.size - 1) {
                if (j in intervalsToSkip) continue
                if (overlap(interval, intervals[j])) {
                    intervalsToSkip.add(j)
                    interval =
                        Pair(
                            Math.min(interval.first, intervals[j].first),
                            Math.max(interval.second, intervals[j].second)
                        )
                    canJoin = true
                }
            }
            intervals[i] = interval
        }

        if (canJoin == false) {
            break
        }
    }

    var sol = 0
    for (i in 0..intervals.size - 1) {
        if (i in intervalsToSkip) continue
        sol += intervals[i].second - intervals[i].first + 1
    }

    sol -= beacons.filter { it.y == rowToCheck }.size
    println(sol)
}

first("test.in", 10) // 26
first("first.in", 2000000) // 5040643


fun getInput(filename: String): MutableList<Pair<Point, Int>> {
    val input: List<String> = File(filename).readLines()
    val lineRegex = Regex("Sensor at x=(-?[0-9]+), y=(-?[0-9]+): closest beacon is at x=(-?[0-9]+), y=(-?[0-9]+)")

    val sensors: MutableList<Pair<Point, Int>> = mutableListOf<Pair<Point, Int>>()

    for (line in input) {
        val (sx, sy, bx, by) = lineRegex.find(line)!!.destructured
        val sensor = Point(sx.toInt(), sy.toInt())
        val beacon = Point(bx.toInt(), by.toInt())

        sensors.add(Pair(sensor, distance(sensor, beacon)))
    }
    return sensors
}

fun solveFor(sensors: MutableList<Pair<Point, Int>>, rowToCheck: Int): List<Pair<Int, Int>> {
    val intervals: MutableList<Pair<Int, Int>> = mutableListOf<Pair<Int, Int>>()

    for ((s, dist) in sensors) {
        val remainingDist = dist - Math.abs(s.y - rowToCheck)
        if (remainingDist < 0) continue
        val interval = Pair(s.x - remainingDist, s.x + remainingDist)

        intervals.add(interval)
    }

    val intervalsToSkip: MutableSet<Int> = mutableSetOf<Int>()
    while (true) {
        var canJoin = false

        for (i in 0..intervals.size - 1) {
            if (i in intervalsToSkip) continue
            var interval = intervals[i]
            for (j in i + 1..intervals.size - 1) {
                if (j in intervalsToSkip) continue
                if (overlap(interval, intervals[j])) {
                    intervalsToSkip.add(j)
                    interval =
                        Pair(
                            Math.min(interval.first, intervals[j].first),
                            Math.max(interval.second, intervals[j].second)
                        )
                    canJoin = true
                }
            }
            intervals[i] = interval
        }

        if (canJoin == false) {
            break
        }
    }


    val sol: MutableList<Pair<Int, Int>> = mutableListOf<Pair<Int, Int>>()
    for (i in 0..intervals.size - 1) {
        if (i in intervalsToSkip) continue
        sol.add(intervals[i])
    }

    return sol
}

fun second(filename: String, maxCoord: Int) {
    val input = getInput(filename)
    for (y in 0..maxCoord) {
        val intervals = solveFor(input, y)

        if (intervals.size == 2) {
            val f = intervals[0]
            val s = intervals[1]

            val x = if (f.second + 2 == s.first) f.second + 1
            else s.second + 1

            val sol = y + 4000000L * x
            println(sol)

            break
        }
    }
}

second("test.in", 20) // 56000011
second("first.in", 4000000)
