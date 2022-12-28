package `2022_01`

import java.io.File

fun first(filename: String) {
    val sol = File(filename).readLines().plus("")

    var currentElf = 0
    var maxElf = 0
    for (row in sol) {
        if (row == "") {
            if (currentElf > maxElf) {
                maxElf = currentElf
            }
            currentElf = 0
        } else {
            currentElf += row.toInt()
        }
    }

    println(maxElf)

}

first("test.in")
first("first.in")


fun second(filename: String) {
    val sol = File(filename).readLines().plus("")
    val elfs = mutableListOf<Int>()

    var currentElf = 0
    for (row in sol) {
        if (row == "") {
            elfs.add(currentElf)
            currentElf = 0
        } else {
            currentElf += row.toInt()
        }
    }

    println(elfs.sorted().takeLast(3).sum())
}

second("test.in")
second("first.in")
