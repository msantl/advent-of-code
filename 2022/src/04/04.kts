package `2022_04`

import java.io.File
import kotlin.math.max
import kotlin.math.min

fun checkCompleteOverlap(x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
    return if (x1 <= x2 && y1 >= y2) true
    else if (x2 <= x1 && y2 >= y1) true
    else false
}

fun first(filename: String) {
    val sol = File(filename).readLines()
        .map { it.split(",") }
        .map { it -> Pair(it[0].split("-"), it[1].split("-")) }
        .map { it ->
            Pair(
                Pair(it.first[0].toInt(), it.first[1].toInt()),
                Pair(it.second[0].toInt(), it.second[1].toInt())
            )
        }
        .filter { checkCompleteOverlap(it.first.first, it.first.second, it.second.first, it.second.second) }
        .count()

    println(sol)
}

first("test.in")
first("first.in")


fun checkOverlap(x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
    val x = max(x1, x2)
    val y = min(y1, y2)
    return x <= y
}

fun second(filename: String) {
    val sol = File(filename).readLines()
        .map { it.split(",") }
        .map { it -> Pair(it[0].split("-"), it[1].split("-")) }
        .map { it ->
            Pair(
                Pair(it.first[0].toInt(), it.first[1].toInt()),
                Pair(it.second[0].toInt(), it.second[1].toInt())
            )
        }
        .filter { checkOverlap(it.first.first, it.first.second, it.second.first, it.second.second) }
        .count()

    println(sol)
}

second("test.in")
second("first.in")
