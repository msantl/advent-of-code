import java.io.File

fun main(filename: String) {
    val input = File(filename).readLines().map { it.toInt() }.sorted()

    val jolts = input.max()!! + 3
    val dp = mutableListOf<Long>()

    dp.add(1L)
    for (i in 1..jolts) {
        dp.add(0L)
    }

    for (j in input) {
        for (k in 1..3) {
            if (j >= k) dp[j] += dp[j - k]
        }
    }
    for (k in 1..3) {
        if (jolts >= k) dp[jolts] += dp[jolts - k]
    }


    println(dp[jolts])
}

main("second.in")
