package `2022_03`

import java.io.File

fun findLetterInBothHalves(str: String): Char {
    var lhs = ""
    var rhs = ""
    for (i in 0..(str.length / 2 - 1)) {
        lhs += str[i]
        rhs += str[str.length / 2 + i]
    }

    for (i in lhs) {
        if (rhs.find { it == i } == i) return i
    }

    return '.'
}

fun getScoreForLetter(chr: Char): Int {
    // Lowercase item types a through z have priorities 1 through 26.
    // Uppercase item types A through Z have priorities 27 through 52.
    return if ('a' <= chr && 'z' >= chr) chr.dec() - 'a'.dec() + 1
    else if ('A' <= chr && 'Z' >= chr) chr.dec() - 'A'.dec() + 27
    else -1
}

fun first(filename: String) {
    val sol = File(filename).readLines()
        .map { findLetterInBothHalves(it) }
        .map { getScoreForLetter(it) }
        .sum()

    println(sol)
}

first("test.in")
first("first.in")


fun second(filename: String) {
    val input = File(filename).readLines()

    var sol = 0
    for (i in 0..input.size / 3 - 1) {
        var badgeForGroup = '.'
        for (k in input[3 * i]) {
            if (input[3 * i + 1].find { it == k } == k && input[3 * i + 2].find { it == k } == k) {
                badgeForGroup = k
            }
        }
        sol += getScoreForLetter(badgeForGroup)
    }
    println(sol)
}

second("test.in")
second("first.in")
