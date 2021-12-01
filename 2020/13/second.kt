import java.io.File
import java.math.BigInteger

fun extendedEuclid(a: BigInteger, b: BigInteger): Pair<BigInteger, BigInteger> {
    var r1 = a
    var x1 = BigInteger.ONE
    var y1 = BigInteger.ZERO

    var r2 = b
    var x2 = BigInteger.ZERO
    var y2 = BigInteger.ONE

    while (r2 != BigInteger.ZERO) {
        val q = r1 / r2
        val r3 = r1 - q * r2
        val x3 = x1 - q * x2
        val y3 = y1 - q * x2

        r1 = r2
        r2 = r3

        x1 = x2
        x2 = x3

        y1 = y2
        y2 = y3
    }

    return Pair(x1, y1)
}

fun chineseRemainder(input: List<Pair<BigInteger, BigInteger>>): BigInteger {
    val nums = input.map { (num, _) -> num }
    val rems = input.map { (_, rem) -> rem }
    val prod = nums.fold(BigInteger.ONE) { acc, i -> acc * i } !!
    var sum = BigInteger.ZERO !!
    for (i in 0 until input.size) {
        val p = prod / nums[i]
        var (Mi, _) = extendedEuclid(p, nums[i])
        if (Mi < BigInteger.ZERO) Mi += nums[i]
        sum += rems[i] * Mi * p
    }
    return sum % prod
}

fun main(filename: String) {
    val input = File(filename).readLines()
    assert(input.size == 2)
    val (_, busesList) = input
    val buses = busesList.split(",")
        .withIndex()
        .filter { (_, it) -> it != "x" }
        .map { (i, it) -> Pair(i, it.toInt()) }
        .map { (i, it) -> Pair(it.toBigInteger(), ((it - i) % it).toBigInteger()) }

    println(chineseRemainder(buses))
}

main("second.in")
