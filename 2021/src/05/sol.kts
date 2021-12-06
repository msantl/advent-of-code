package `05`

import java.io.File
import kotlin.math.abs

data class Point(val x: Int, val y: Int)

data class Line(val a: Point, val b: Point)

fun convertInputToPoint(input: String): Point {
    val coords = input.split(',').map { it.toInt() }
    return Point(coords[0], coords[1])
}

fun convertInputToLine(input: String): Line {
    val points = input.split(" -> ").map { convertInputToPoint(it) }
    return Line(points[0], points[1])
}

fun readInput(filename: String): List<Line> {
    return File(filename).readLines().map { convertInputToLine(it) }
}

fun solve(lines: List<Line>): Int {
    val points = mutableMapOf<Point, Int>()
    for (line in lines) {
        val p0 = line.a
        val p1 = line.b

        val xDelta = p1.x - p0.x
        val yDelta = p1.y - p0.y

        if (xDelta == 0 && yDelta == 0) {
            points[p0] = points.getOrDefault(p0, 0) + 1
        } else if (xDelta != 0 && yDelta == 0) {
            val xAbs = abs(xDelta)
            val xSign = xDelta / xAbs

            for (step in 0..xAbs) {
                val point = Point(p0.x + step * xSign, p0.y)
                points[point] = points.getOrDefault(point, 0) + 1
            }
        } else if (yDelta != 0 && xDelta == 0) {
            val yAbs = abs(yDelta)
            val ySign = yDelta / yAbs

            for (step in 0..yAbs) {
                val point = Point(p0.x, p0.y + step * ySign)
                points[point] = points.getOrDefault(point, 0) + 1
            }
        } else {
            val xAbs = abs(xDelta)
            val xSign = xDelta / xAbs

            val yAbs = abs(yDelta)
            val ySign = yDelta / yAbs

            if (xAbs != yAbs) {
                println("Something is wrong! $p0 and $p1")
            }

            for (step in 0..xAbs) {
                val point =
                    Point(p0.x + step * xSign, p0.y + step * ySign)
                points[point] = points.getOrDefault(point, 0) + 1
            }
        }
    }

    return points.filterValues { it > 1 }.size
}

fun first(filename: String) {
    val lines =
        readInput(filename).filter {
            (it.b.x == it.a.x && it.b.y != it.a.y) || (it.b.x != it.a.x && it.b.y == it.a.y)
        }
    println(solve(lines))
}

first("test.in") // 5
first("input.in") // 6311

fun second(filename: String) {
    val lines = readInput(filename)
    println(solve(lines))
}

second("test.in") // 12
second("input.in") // 19929
