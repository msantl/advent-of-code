import java.io.File
import java.util.stream.Collectors

fun main() {
    var input = File("first.in").readText().trim().map { it.toInt() - '0'.toInt() }
    val pattern: List<Int> = listOf(0, 1, 0, -1)

    for (i in 0..99) {
        var output: MutableList<Int> = mutableListOf()
        for (j in 0 until input.size) {
            val mulPattern = pattern.stream()
                .map { List(j + 1) { _ -> it } }
                .flatMap { it.stream() }
                .collect(Collectors.toList())


            var sum: Int = 0
            for (k in 0 until input.size) {
                val curr = input.get(k) * mulPattern.get((k + 1) % mulPattern.size)
                sum += curr
            }
            output.add(Math.abs(sum) % 10)
        }

        input = output
    }

    println(input.joinToString("").substring(0, 8))
}

main()