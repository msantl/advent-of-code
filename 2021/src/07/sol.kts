package `07`

import java.io.File
import kotlin.math.abs

fun first(filename: String) {
    val crabPositions =
        File(filename).readLines().first().split(',').map { it.toInt() }
            .sorted()
    val median = crabPositions[crabPositions.size / 2]
    val sol = crabPositions.map { abs(it - median) }.sum()
    println(sol)
}

first("test.in") // 37
first("input.in") // 352254

fun calculateFuelConsumption(crabPositions: List<Int>, targetPosition: Int) =
    crabPositions.map { abs(it - targetPosition) }.sumOf { it * (it + 1) / 2 }

fun ternarySearch(crabPosition: List<Int>, lo: Int, hi: Int): Int {
    if (lo == hi) {
        return calculateFuelConsumption(crabPosition, lo)
    } else if (hi - lo == 1) {
        val loCost = calculateFuelConsumption(crabPosition, lo)
        val hiCost = calculateFuelConsumption(crabPosition, hi)
        return if (loCost < hiCost) {
            loCost
        } else {
            hiCost
        }
    }

    val left = (2 * lo + hi) / 3
    val right = (lo + 2 * hi) / 3

    return if (calculateFuelConsumption(crabPosition, left) < calculateFuelConsumption(crabPosition, right)) {
        ternarySearch(crabPosition, lo, right - 1)
    } else {
        ternarySearch(crabPosition, left + 1 , hi)
    }
}

fun second(filename: String) {
    val crabPositions =
        File(filename).readLines().first().split(',').map { it.toInt() }
            .sorted()
    val lo = crabPositions.first()
    val hi = crabPositions.last()

    println(ternarySearch(crabPositions, lo, hi))
}

second("test.in") // 168
second("input.in") // 99053143