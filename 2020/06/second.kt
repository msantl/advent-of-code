import java.io.File

fun main(filename: String) {
    val input = File(filename).readLines()

    val groupCount = mutableMapOf<Char, Int>()
    var groupSize = 0
    var solution = 0

    input.forEach {
        if (it.length == 0) {
            solution += groupCount.values.filter { it == groupSize }.size
            groupCount.clear()
            groupSize = 0
        } else {
            it.forEach { c -> groupCount.put(c, groupCount.getOrDefault(c, 0) + 1) }
            groupSize += 1
        }
    }

    solution += groupCount.values.filter { it == groupSize }.size

    println(solution)
}

main("second.in")
