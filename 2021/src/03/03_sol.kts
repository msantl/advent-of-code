import java.io.File

fun first(filename: String) {
    val gamaBin = File(filename).readLines()
        .map { it.withIndex() }
        .flatMap { it.toList() }
        .groupBy { it.index }
        .map { (k, v) ->
            if (2 * v.sumOf { it.value.digitToInt() } >= v.size) Pair(k, 1) else Pair(k, 0)
        }

    val gama = gamaBin
        .map { it.second * (1 shl (gamaBin.size - it.first - 1)) }
        .sum()

    val eps = gamaBin
        .map { (1 - it.second) * (1 shl (gamaBin.size - it.first - 1)) }
        .sum()

    println(gama * eps)
}

first("test.in")
first("input.in")

fun getAllWithCommonBitAtIdxOrDefault(input: List<String>, idx: Int, default: Int, mostCommonFlag: Boolean): List<String> {
    val mostCommonBitSum = input.map { it.withIndex() }
        .flatMap { it.toList() }
        .filter { it.index == idx }
        .map { it.value.digitToInt() }
        .sum()

    val mostCommonBit = if (mostCommonBitSum * 2 == input.size) {
       default
    } else if (mostCommonBitSum * 2 > input.size) {
        if (mostCommonFlag) 1 else 0
    } else {
        if (mostCommonFlag) 0 else 1
    }

    return input.filter { it -> it.withIndex().filter { binary -> (binary.index == idx && binary.value.digitToInt() == mostCommonBit) }.size > 0 }
}

fun binaryStringToInt(input: String): Int {
    return input.withIndex()
        .map { it.value.digitToInt() * (1 shl (input.length - it.index - 1)) }
        .sum()
}

fun second(filename: String) {
    val input = File(filename).readLines()

    var o2 = input
    run {
        var idx = 0
        while (o2.size > 1) {
            o2 = getAllWithCommonBitAtIdxOrDefault(o2, idx, 1, true)
            idx += 1
        }
    }
    val o2Value = binaryStringToInt(o2.first())

    var co2 = input
    run {
        var idx = 0
        while (co2.size > 1) {
            co2 = getAllWithCommonBitAtIdxOrDefault(co2, idx, 0, false)
            idx += 1
        }
    }
    val co2Value = binaryStringToInt(co2.first())

    println(o2Value * co2Value)
}

second("test.in")
second("input.in")