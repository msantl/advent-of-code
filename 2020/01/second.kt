import java.io.File

fun main(filename: String) {
    val input = File(filename).readLines().map { it.toInt() }
    val sums =
        input.map { a -> input.map { b -> a to b } }.flatten().map { Pair(it.first + it.second, it.first * it.second) }

    val solution =
        sums.map { s -> input.filter { c -> 2020 - s.first == c }.firstOrNull()?.times(s.second) }.filterNotNull().firstOrNull()

    println(solution)
}

main("second.in")
