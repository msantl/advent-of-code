import java.io.File

fun main(filename: String) {
    val input = File(filename).readLines().map { it.split(" ") }

    var solution = 0
    input.forEach {
        val (i1, i2) = it[0].split("-").map { it.toInt() }
        val char = it[1].first()

        val first = it[2].getOrNull(i1 - 1)?.equals(char)
        val f = if (first == null) false else first
        val second = it[2].getOrNull(i2 - 1)?.equals(char)
        val s = if (second == null) false else second

        if (f xor s) solution += 1
    }
    println(solution)
}

main("second.in")
