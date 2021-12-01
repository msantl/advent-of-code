import java.io.File

val dir = mapOf("E" to Pair(1, 0), "W" to Pair(-1, 0), "N" to Pair(0, 1), "S" to Pair(0, -1))

fun main(filename: String) {
    val input = File(filename).readLines().toMutableList()

    var position = Pair(0, 0)
    var waypoint = Pair(10, 1)

    input.forEach {
        val cmd = it.take(1)
        val value = it.takeLast(it.length - 1).toInt()

        when (cmd) {
            "E", "S", "W", "N" -> {
                val (deltaX, deltaY) = dir.getOrDefault(cmd, Pair(0, 0))
                val (waypointX, waypointY) = waypoint
                waypoint = Pair(deltaX * value + waypointX, deltaY * value + waypointY)
            }
            "F" -> {
                val (positionX, positionY) = position
                val (waypointX, waypointY) = waypoint
                position = Pair(positionX + value * waypointX, positionY + value * waypointY)
            }
            "L" -> {
                assert(value % 90 == 0)
                for (i in 1..(value / 90)) {
                    val (waypointX, waypointY) = waypoint
                    waypoint = Pair(-1 * waypointY, waypointX)
                }
            }
            "R" -> {
                assert(value % 90 == 0)
                for (i in 1..(value / 90)) {
                    val (waypointX, waypointY) = waypoint
                    waypoint = Pair(waypointY, -1 * waypointX)
                }
            }
            else -> throw Exception("Unexpected command")
        }
    }

    val (positionX, positionY) = position
    println(Math.abs(positionX) + Math.abs(positionY))
}

main("second.in")
