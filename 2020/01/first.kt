import java.io.File

fun main(filename: String) {
    val input = File(filename).readLines().map { it.toInt() }
    val solution = input.map { lhs -> Pair(lhs, input.filter { rhs -> rhs == 2020 - lhs }.firstOrNull()) }
        .filter { it -> it.second != null }
        .map { it.first * it.second!! }
        .firstOrNull()

    println(solution)
}

main("first.in")
