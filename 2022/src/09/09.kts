package `2022_09`

import java.io.File
import kotlin.math.abs

val directions = mapOf(
    "D" to Pair(0, -1),
    "U" to Pair(0, 1),
    "L" to Pair(-1, 0),
    "R" to Pair(1, 0)
)

fun isAway(a: Pair<Int, Int>, b: Pair<Int, Int>): Boolean {
    return if (abs(a.first - b.first) >= 2) return true
    else if (abs(a.second - b.second) >= 2) return true
    else false
}

fun distanceComponents(a: Pair<Int, Int>, b: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(a.first - b.first, a.second - b.second)
}

fun first(filename: String) {
    val input: List<String> = File(filename).readLines()

    var H = Pair(0, 0)
    var T = Pair(0, 0)

    val tracker = mutableSetOf<Pair<Int, Int>>(
        Pair(0, 0)
    )

    for (line in input) {
        val parts = line.split(" ")
        val dir = directions.getValue(parts[0])
        val count = parts[1].toInt()

        for (i in 1..count) {
            val distComponents = distanceComponents(H, T)

            H = Pair(H.first + dir.first, H.second + dir.second)

            if (isAway(H, T)) {
                T = Pair(T.first + distComponents.first, T.second + distComponents.second)
                tracker.add(T)
            }
        }
    }

    println(tracker.size)
}

first("test.in") // 13
first("first.in") // 6057

fun normalize(a: Pair<Int, Int>): Pair<Int, Int> {
    val first = if (a.first >= 1) 1
    else if (a.first <= -1) -1
    else 0

    val second = if (a.second >= 1) 1
    else if (a.second <= -1) -1
    else 0

    return Pair(first, second)
}

fun second(filename: String) {
    val input: List<String> = File(filename).readLines()

    val ropes = mutableMapOf<Int, Pair<Int, Int>>()
    for (i in 0..9) {
        ropes.put(i, Pair(0, 0))
    }

    val tracker = mutableSetOf<Pair<Int, Int>>(
        Pair(0, 0)
    )

    for (line in input) {
        val parts = line.split(" ")
        val dir = directions.getValue(parts[0])
        val count = parts[1].toInt()

        for (i in 1..count) {
            var head = ropes.getValue(0)
            var newHead = Pair(head.first + dir.first, head.second + dir.second)
            ropes.put(0, newHead)

            for (rope in 1..9) {
                val tail = ropes.getValue(rope)

                val prevDistanceComp = distanceComponents(head, tail)
                val headDistanceComp = distanceComponents(newHead, head)

                val newDir = normalize(
                    Pair(
                        prevDistanceComp.first + headDistanceComp.first,
                        prevDistanceComp.second + headDistanceComp.second
                    )
                )

                val newTail = if (isAway(newHead, tail)) {
                    Pair(tail.first + newDir.first, tail.second + newDir.second)
                } else {
                    tail
                }

                if (rope == 9) {
                    tracker.add(newTail)
                }
                ropes.put(rope, newTail)

                head = tail
                newHead = newTail
            }

//            println("------ $line -------")
//            for (y in 15 downTo -15) {
//                for (x in -15..15) {
//                    val ids = ropes.entries.filter { it.value == Pair(x, y) }.map { it.key }.sorted()
//                    if (ids.size > 0) {
//                        print(ids.first())
//                    } else {
//                        print(".")
//                    }
//                }
//                println()
//            }

        }
    }


    println(tracker.size)
}

second("test.in") // 1
second("test2.in") // 36
second("first.in")
