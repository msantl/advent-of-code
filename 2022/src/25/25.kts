package `2022_25`

import java.io.File
import java.util.PriorityQueue

fun fromSnafu(num: String): Long {
    var res: Long = 0
    var mul: Long = 1L
    for (ch in num.reversed()) {
        res = res + mul * when (ch) {
            '2' -> 2L
            '1' -> 1L
            '-' -> -1L
            '=' -> -2L
            else -> 0L
        }
        mul *= 5L
    }
    return res
}

val decToSnafu: Map<Int, Char> = mapOf<Int, Char>(-2 to '=', -1 to '-', 0 to '0', 1 to '1', 2 to '2')

data class State(
    val diff: Long,
    val number: Long,
    val mul: Long,
    val snafu: String
) : Comparable<State> {
    override fun compareTo(other: State): Int {
        if (diff < other.diff) return -1
        else if (diff > other.diff) return 1
        else return 0
    }
}

fun toSnafuDijkstra(num: Long): String {
    val pq: PriorityQueue<State> = PriorityQueue()

    var mul: Long = 1L
    while (5L * mul <= num) mul *= 5L

    pq.add(State(num, 0L, mul, ""))

    while (true) {
        val curr: State? = pq.peek()
        if (curr == null) break
        pq.remove(curr)

        if (curr.mul == 0L) {
            if (curr.diff == 0L) return curr.snafu
            continue
        }

        val value = curr.number

        for (i in -2..2) {
            val newValue = value + i * curr.mul
            val next: Long = Math.abs(num - newValue)
            val snafu = "${curr.snafu}${decToSnafu.getValue(i)}"

            pq.add(State(next, newValue, curr.mul / 5L, snafu))
        }

    }

    throw Exception("Failed to find the result")
}

fun toBaseFive(num: Long): String {
    var res: String = ""
    var rem: Long = num
    while (rem > 0) {
        res = (rem % 5).toString() + res
        rem /= 5
    }
    return res
}

fun first(filename: String) {
    val sum = File(filename).readLines().map { fromSnafu(it) }.sum()
    println("$sum = ${toSnafuDijkstra(sum)}")
}

first("test.in") // 4890, 2=-1=0
first("first.in")
