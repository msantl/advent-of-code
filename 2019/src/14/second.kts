import java.io.File
import kotlin.math.max

val node: MutableMap<String, List<Triple<String, Long, Long>>> = mutableMapOf()
var counter: MutableMap<String, Long> = mutableMapOf()

fun costOf(count: Long, type: String) {
    if (type == "ORE") {
        counter.put(type, counter.getOrDefault(type, 0) + count)
        return
    }

    val available = counter.getOrDefault(type, 0)
    if (available >= count) {
        counter.put(type, available - count)
        return
    }

    val needed = count - available
    var produced: Long = 0

    for (dep in node.getOrDefault(type, emptyList())) {
        val rounds = div(needed, dep.third)
        produced = max(produced, rounds * dep.third)
        costOf(rounds * dep.second, dep.first)
    }

    counter.put(type, produced - needed)
}

fun div(a: Long, b: Long): Long {
    return Math.ceil(a.toDouble() / b).toLong()
}

fun main() {
    val input: List<String> = File("second.in").readLines()
    for (line in input) {
        val (first, second) = line.split(" => ")
        val (result_cost, result_type) = second.split(" ")

        val factors = first.split(", ")
        for (elements in factors) {
            val (cost, type) = elements.split(" ")
            val dep = node.getOrDefault(result_type, emptyList())
            node.put(result_type, dep.plus(Triple(type, cost.toLong(), result_cost.toLong())))
        }
    }

    var lo: Long = 1
    var hi: Long = 1000000000000

    val maxOre = 1000000000000

    while (lo < hi) {
        val fuel = (lo + hi) / 2
        counter = mutableMapOf()
        costOf(fuel, "FUEL")

        val ore = counter.getOrDefault("ORE", -1)
        if (ore <= maxOre) {
            lo = fuel + 1
        } else {
            hi = fuel
        }
    }

    println("${lo - 1}")
}

main()