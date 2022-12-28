package `2022_20`

import java.io.File

fun mix(res: List<Long>, n: Int): List<Long> {
    var list: MutableList<Long> = res.toMutableList()
    var indexList: MutableList<Int> = mutableListOf<Int>()
    indexList.addAll(0..res.size - 1)


    for (k in 1..n) {
        for ((index, value) in res.withIndex()) {
            val currIndex = indexList.indexOf(index)

            indexList.removeAt(currIndex)
            list.removeAt(currIndex)

            val mod = list.size
            val newIndex = ((((currIndex + value) % mod) + mod) % mod).toInt()

            indexList.add(newIndex, index)
            list.add(newIndex, value)
        }
    }

    return list.toList()
}

fun getList(filename: String): List<Long> {
    return File(filename).readLines().map { it.toLong() }
}

fun first(filename: String) {
    val listOfNumbers = getList(filename)

    val mixedList = mix(listOfNumbers, 1)

    val len: Int = mixedList.size
    val idOfZero = mixedList.indexOf(0L)

    val idsToCheck: List<Int> = listOf(1000, 2000, 3000)
    var sum: Long = 0

    for (id in idsToCheck) {
        sum += mixedList[(idOfZero + id) % len]
    }
    println(sum)
}

first("test.in") // 3
first("first.in")


fun second(filename: String) {
    val listOfNumbers = getList(filename).map { it * 811589153L }

    val mixedList = mix(listOfNumbers, 10)

    val len: Int = mixedList.size
    val idOfZero = mixedList.indexOf(0L)

    val idsToCheck: List<Int> = listOf(1000, 2000, 3000)
    var sum: Long = 0

    for (id in idsToCheck) {
        sum += mixedList[(idOfZero + id) % len]
    }
    println(sum)
}

second("test.in") // 1623178306
second("first.in")
