import java.io.File

fun main(filename: String) {
    val input = File(filename).readLines()
    assert(input.size == 2)
    val (departureString, busesList) = input
    val departure = departureString.toInt()
    val buses = busesList.split(",").filter { it != "x" }.map { it.toInt() }

    var solution = 0
    var minWaitTime = Int.MAX_VALUE

    buses.forEach {
        val waitTime = it * Math.ceil(1.0 * departure / it).toInt() - departure
        if (minWaitTime > waitTime) {
            minWaitTime = waitTime
            solution = it * waitTime
        }
    }

    println(solution)

}

main("first.in")
