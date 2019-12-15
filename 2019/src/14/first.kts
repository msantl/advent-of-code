import java.io.File
import kotlin.math.max

val node: MutableMap<String, List<Triple<String, Int, Int>>> = mutableMapOf()
val counter: MutableMap<String, Int> = mutableMapOf()

fun costOf(count: Int, type: String) {
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
    var produced = 0

    for (dep in node.getOrDefault(type, emptyList())) {
        val rounds = div(needed, dep.third)
        produced = max(produced, rounds * dep.third)
        costOf(rounds * dep.second, dep.first)
    }

    counter.put(type, produced - needed)
}

fun div(a: Int, b: Int): Int {
    return Math.ceil(a.toDouble() / b).toInt()
}

fun main() {
    val input: List<String> = File("first.in").readLines()
    for (line in input) {
        val (first, second) = line.split(" => ")
        val (result_cost, result_type) = second.split(" ")

        val factors = first.split(", ")
        for (elements in factors) {
            val (cost, type) = elements.split(" ")
            val dep = node.getOrDefault(result_type, emptyList())
            node.put(result_type, dep.plus(Triple(type, cost.toInt(), result_cost.toInt())))
        }
    }

    costOf(1, "FUEL")
    println(counter.getOrDefault("ORE", -1)) // 248794
}

main()