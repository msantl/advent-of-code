import java.io.File
import kotlin.math.max

fun convertToInt(translatedString: String, dictionary: Map<Char, Char>): Int {
    return translatedString.map { c -> dictionary.get(c) }.joinToString(separator = "") { it.toString() }.toInt(2)
}

fun main(filename: String) {
    val input = File(filename).readLines()
    var solution = Int.MIN_VALUE

    input.forEach {
        val row = convertToInt(it.take(7), mapOf('F' to '0', 'B' to '1'))
        val seat = convertToInt(it.takeLast(3), mapOf('L' to '0', 'R' to '1'))

        val seatId = row * 8 + seat
        solution = max(solution, seatId)
    }

    println(solution)
}

main("first.in")
