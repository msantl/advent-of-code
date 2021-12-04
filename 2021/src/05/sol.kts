package `05`

import java.io.File

fun first(filename: String) {
    val input = File(filename).readLines()
    println(input)
}

first("test.in")
first("input.in")

fun second(filename: String) {
    val input = File(filename).readLines()
    println(input)
}

second("test.in")
second("input.in")