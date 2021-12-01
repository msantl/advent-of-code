import java.io.File

val dir = mapOf("E" to Pair(1, 0), "W" to Pair(-1, 0), "N" to Pair(0, 1), "S" to Pair(0, -1))

fun turnLeft(d: String): String {
    when (d) {
        "E" -> return "N"
        "W" -> return "S"
        "N" -> return "W"
        "S" -> return "E"
        else -> throw Exception("unexpected direction")
    }
}

fun turnRight(d: String): String {
    when (d) {
        "E" -> return "S"
        "W" -> return "N"
        "N" -> return "E"
        "S" -> return "W"
        else -> throw Exception("unexpected direction")
    }
}

fun main(filename: String) {
    val input = File(filename).readLines().toMutableList()

    var direction = "E"
    var position = Pair(0, 0)

    input.forEach {
        val cmd = it.take(1)
        val value = it.takeLast(it.length - 1).toInt()

        val (deltaX, deltaY) = when (cmd) {
            "E", "S", "W", "N" -> {
                dir.getOrDefault(cmd, Pair(0, 0))
            }
            "F" -> {
                dir.getOrDefault(direction, Pair(0, 0))
            }
            "L" -> {
                assert(value % 90 == 0)
                for (i in 1..(value / 90)) {
                    direction = turnLeft(direction)
                }
                Pair(0, 0)
            }
            "R" -> {
                assert(value % 90 == 0)
                for (i in 1..(value / 90)) {
                    direction = turnRight(direction)
                }
                Pair(0, 0)
            }
            else -> Pair(0, 0)
        }

        val (positionX, positionY) = position
        position = Pair(deltaX * value + positionX, deltaY * value + positionY)
    }

    val (positionX, positionY) = position
    println(Math.abs(positionX) + Math.abs(positionY))
}

main("first.in")
