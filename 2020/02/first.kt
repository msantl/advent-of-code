import java.io.File

fun main(filename: String) {
    val input = File(filename).readLines().map { it.split(" ") }

    var solution = 0
    input.forEach {
        val (min, max) = it[0].split("-").map { it.toInt() }
        val char = it[1].first()
        val count = it[2].groupingBy { it }.eachCount().getOrDefault(char, 0)

        if (min <= count && count <= max) solution += 1

    }
    println(solution)
}

main("first.in")
