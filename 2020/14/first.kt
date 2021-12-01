import java.io.File
import java.util.regex.Pattern
import java.util.regex.Matcher

fun applyMask(value: Long, mask: String): Long {
    var result = 0L

    for ((pos, bit) in mask.withIndex()) {
        when (bit) {
            'X' -> {
                result = result * 2 + ((value shr (35 - pos)) and 1)
            }
            '0' -> {
                result = result * 2
            }
            '1' -> {
                result = result * 2 + 1
            }
            else -> throw Exception("Unexpected bitmask character")
        }
    }
    return result
}

fun main(filename: String) {
    val input = File(filename).readLines()

    var mask: String? = null
    var memory = mapOf<Int, Long>()

    val maskPattern = Pattern.compile("mask = (\\p{Alnum}+)")
    val writePattern = Pattern.compile("mem\\[(\\d+)\\] = (\\p{Alnum}+)")

    input.forEach {
        val (type, match) = mapOf(
            "mask" to maskPattern.matcher(it), "write" to writePattern.matcher(it)
        ).filter { (_, v) -> v.matches() }.entries.first()

        when (type) {
            "mask" -> {
                mask = match.group(1)
            }
            "write" -> {
                val addr = match.group(1)?.toInt()!!
                val value = match.group(2)?.toLong()!!

                val maskedValue = applyMask(value, mask!!)
                memory = memory.plus(addr to maskedValue)
            }
            else -> {
                throw Exception("Unexpected command!")
            }
        }
    }

    println(memory.values.filter { it != 0L }.sum())

}

main("first.in")
