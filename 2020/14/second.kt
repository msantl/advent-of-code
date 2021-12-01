import java.io.File
import java.util.regex.Pattern
import java.util.regex.Matcher

fun applyMask(value: Long, mask: String): List<Long> {
    var results = listOf(0L)

    for ((pos, bit) in mask.withIndex()) {
        when (bit) {
            'X' -> {
                results = results.map { it -> 2 * it + 1 }.plus(results.map { it -> 2 * it + 0 })
            }
            '0' -> {
                results = results.map { it -> 2 * it + ((value shr (35 - pos)) and 1) }
            }
            '1' -> {
                results = results.map { it -> 2 * it + 1 }
            }
            else -> throw Exception("Unexpected bitmask character")
        }
    }
    return results
}

fun main(filename: String) {
    val input = File(filename).readLines()

    var mask: String? = null
    var memory = mapOf<Long, Long>()

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
                val addr = match.group(1)?.toLong()!!
                val value = match.group(2)?.toLong()!!

                val maskedAddrs = applyMask(addr, mask!!)
                maskedAddrs.forEach {
                    memory = memory.plus(it to value)
                }
            }
            else -> {
                throw Exception("Unexpected command!")
            }
        }
    }

    println(memory.values.filter { it != 0L }.sum())

}

main("second.in")
