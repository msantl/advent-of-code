import java.io.File

fun first(filename: String) {
    val sol = File(filename).readLines().map { it.toInt() }
        .zipWithNext().count { (a, b) -> b > a }
    println(sol)
}

first("test.in")
first("input.in")

fun zipWithOffset(lst: List<Int>, offset: Int) : List<Pair<Int, Int>> {
    val res = mutableListOf<Pair<Int, Int>>()
    for (i in 0 until lst.size - offset) {
        res.add(Pair(lst[i], lst[i + offset]))
    }
    return res
}

fun second(filename: String) {
    val offset = 3
    val input = File(filename).readLines().map { it.toInt() }

    val sol = zipWithOffset(input, offset).count { (a, b) -> b > a}
    println(sol)
}

second("test.in")
second("input.in")
