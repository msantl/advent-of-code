import java.io.File

fun main(filename: String) {
    val input = File(filename).readLines()

    val groupCount = mutableMapOf<Char, Int>()
    var solution = 0

    input.forEach {
        if (it.length == 0) {
            solution += groupCount.keys.size
            groupCount.clear()
        } else {
            it.forEach { c -> groupCount.put(c, 1) }
        }
    }

    solution += groupCount.keys.size
    println(solution)
}

main("first.in")
