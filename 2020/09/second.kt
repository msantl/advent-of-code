import java.io.File

fun main(filename: String) {
    val input = File(filename).readLines().map { it.toLong() }
    val invalidNumber = if (filename == "test.in") 127L else 133015568L

    val sums = mutableListOf<Long>()

    for (i in 0..input.size - 1) {
        sums.add(input[i] + if (i > 0) sums[i - 1] else 0)
    }

    for (i in 0..input.size - 1) {
        for (j in i + 2..input.size - 1) {
            if (sums[j] - sums[i] == invalidNumber) {

                var mini = Long.MAX_VALUE
                var maxi = Long.MIN_VALUE
                for (k in i + 1..j) {
                    mini = Math.min(mini, input[k])
                    maxi = Math.max(maxi, input[k])
                }
                println(mini + maxi)
                return
            }
        }
    }
}

main("second.in")
