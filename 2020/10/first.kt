import java.io.File

fun main(filename: String) {
    val input = File(filename).readLines().map { it.toInt() }.sorted()

    var curr = 0;
    val distribution = mutableListOf(3)

    input.forEach {
        assert(it - curr <= 3)
        distribution.add(it - curr)
        curr = it
    }

    val dist = distribution.groupBy { it }
    println(dist.getOrDefault(3, emptyList()).size * dist.getOrDefault(1, emptyList()).size)
}

main("first.in")
