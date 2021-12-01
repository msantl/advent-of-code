import java.io.File

val MOD = 20201227L

fun powerOf(x: Long, p: Long): Long {
    if (p == 0L) return 1L
    if (p == 1L) return x;

    val pot = powerOf(x, p / 2)
    val sub = (pot * pot) % MOD
    if (p % 2 == 1L) return (x * sub) % MOD
    else return sub % MOD
}

fun findLoopSize(subject: Long, target: Long) : Long {
    var loopSize = 1L

    while (true) {
        if (powerOf(subject, loopSize) == target) {
            return loopSize
        }
        loopSize += 1
    }
}

fun main(filename: String) {
    val (card, door) = File(filename).readLines().map { it.toLong() }


    val cardLoopSize = findLoopSize(7L, card)
    val doorLoopSize = findLoopSize(7L, door)

    println(powerOf(door, cardLoopSize))
    println(powerOf(card, doorLoopSize))
}

main("first.in")
