package template

import java.io.File

fun readInput(filename: String) =
    File(filename).readLines()

fun first(filename: String) {
    val input = readInput(filename)
    println(input)
}

first("test.in") //
first("input.in") //

fun second(filename: String) {
    val input = readInput(filename)
    println(input)
}

second("test.in") //
second("input.in") //
