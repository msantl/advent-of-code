package `2022_02`

import java.io.File

// A for Rock, B for Paper, and C for Scissors


fun first(filename: String) {
    // Round score
    // 0 if you lost, 3 if the round was a draw, and 6 if you won
    val outcomes = mapOf<Pair<String, String>, Int>(
        Pair("A", "X") to 3,
        Pair("A", "Y") to 6,
        Pair("A", "Z") to 0,

        Pair("B", "X") to 0,
        Pair("B", "Y") to 3,
        Pair("B", "Z") to 6,

        Pair("C", "X") to 6,
        Pair("C", "Y") to 0,
        Pair("C", "Z") to 3,
    )

    // My score
    // 1 for Rock, 2 for Paper, and 3 for Scissors
    val extraScore = mapOf<String, Int>(
        "X" to 1,
        "Y" to 2,
        "Z" to 3
    )

    val sol = File(filename)
        .readLines()
        .map { it -> it.split(" ") }
        .map { it -> outcomes.getOrDefault(Pair(it[0], it[1]), 0) + extraScore.getOrDefault(it[1], 0) }
        .sum()

    println(sol)
}

first("test.in")
first("first.in")


fun second(filename: String) {
    // My score
    // 1 for Rock, 2 for Paper, and 3 for Scissors
    val extraScore = mapOf<String, Int>(
        "A" to 1,
        "B" to 2,
        "C" to 3

    )
    val outcome = mapOf<String, Int>(
        "X" to 0,
        "Y" to 3,
        "Z" to 6
    )

    val shapeNeededForOutcome = mapOf<Pair<String, String>, String>(
        Pair("A", "X") to "C",
        Pair("A", "Y") to "A",
        Pair("A", "Z") to "B",

        Pair("B", "X") to "A",
        Pair("B", "Y") to "B",
        Pair("B", "Z") to "C",

        Pair("C", "X") to "B",
        Pair("C", "Y") to "C",
        Pair("C", "Z") to "A",
    )

    val sol = File(filename)
        .readLines()
        .map { it -> it.split(" ") }
        .map { it ->
            outcome.getOrDefault(it[1], 0) +
                    extraScore.getOrDefault(shapeNeededForOutcome.getOrDefault(Pair(it[0], it[1]), "A"), 0)
        }
        .sum()

    println(sol)
}

second("test.in")
second("first.in")
