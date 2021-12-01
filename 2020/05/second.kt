import java.io.File
import kotlin.math.max

fun convertToInt(translatedString: String, dictionary: Map<Char, Char>): Int {
    return translatedString.map { c -> dictionary.get(c) }.joinToString(separator = "") { it.toString() }.toInt(2)
}

fun main(filename: String) {
    val input = File(filename).readLines()
    val seats = input.map {
        val row = convertToInt(it.take(7), mapOf('F' to '0', 'B' to '1'))
        val seat = convertToInt(it.takeLast(3), mapOf('L' to '0', 'R' to '1'))
        row * 8 + seat
    }.toSet()


    for (i in 1..1024) {
        if (!seats.contains(i) && seats.contains(i - 1) && seats.contains(i + 1)) {
            println(i)
        }
    }
}

main("second.in")
