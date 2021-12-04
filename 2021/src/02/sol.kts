package `02`

import java.io.File

fun first(filename: String) {
    val sol = File(filename).readLines().map { it.split(' ').take(2) }
        .map { (command, value) ->
            when (command) {
                "forward" -> Pair(0, value.toInt())
                "up" -> Pair(-1 * value.toInt(), 0)
                "down" -> Pair(value.toInt(), 0)
                else -> Pair(0, 0)
            }
        }
        .reduce { acc, pair ->
            Pair(
                acc.first + pair.first,
                acc.second + pair.second
            )
        }

    println(sol.first * sol.second)
}

first("test.in")
first("input.in")

fun second(filename: String) {
    val cmds = File(filename).readLines().map { it.split(' ').take(2) }

    var sol = Pair(0, 0)
    var aim = 0
    for ((command, value) in cmds) {
        sol = when (command) {
            "forward" -> Pair(sol.first + value.toInt(), sol.second + aim * value.toInt())
            "up" -> {
                aim -= value.toInt()
                sol
            }
            "down" -> {
                aim += value.toInt()
                sol
            }
            else -> sol
        }
    }

    println(sol.first * sol.second)
}

second("test.in")
second("input.in")