import java.io.File

fun checkValid(number: Long, preamble: List<Long>): Boolean {
    val n = preamble.size - 1
    for (i in 0..n) {
        for (j in (i + 1)..n) {
            if (preamble[i] + preamble[j] == number) return true
        }
    }
    return false
}

fun main(filename: String) {
    val input = File(filename).readLines().map { it.toLong() }

    val preambleSize = 25
    var preamble = input.take(preambleSize)
    for (i in preambleSize..input.size-1) {
        if (!checkValid(input[i], preamble)) {
            println(input[i])
            break
        }
        preamble = preamble.takeLast(preambleSize - 1).plus(input[i])
    }
}

main("first.in")
