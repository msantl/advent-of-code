import java.io.File
import kotlin.math.abs

fun gcd(x: Long, y: Long): Long {
    var a: Long = x
    var b: Long = y

    while (b > 0) {
        val t: Long = b
        b = a % b
        a = t
    }
    return a
}

fun lcm(a: Long, b: Long): Long {
    return a * b / gcd(a, b)
}

fun solve(axis: List<Int>): Long {
    var positions: List<Int> = axis
    var velocities: List<Int> = generateSequence(0) { it + 1 }
        .take(positions.size)
        .map { _ -> 0 }
        .toList()

    var step: Long = 0
    var period: MutableMap<Pair<List<Int>, List<Int>>, Int> = mutableMapOf()

    period.put(positions to velocities, 0)

    while (true) {
        step += 1
        var newPositions: List<Int> = emptyList()
        var newVelocities: List<Int> = emptyList()

        for (i in 0 until axis.size) {
            var newX = velocities.get(i)
            for (j in 0 until axis.size) {
                if (i == j) continue

                if (positions.get(i) < positions.get(j)) newX += 1
                else if (positions.get(i) > positions.get(j)) newX -= 1
            }

            newPositions = newPositions.plus(positions.get(i) + newX)
            newVelocities = newVelocities.plus(newX)
        }
        positions = newPositions
        velocities = newVelocities

        val key = positions to velocities
        if (period.containsKey(key)) {
            val prev = period.getValue(key)
            return step - prev
        }
    }
}

fun main() {
    var positions: List<Triple<Int, Int, Int>> = File("second.in").readLines()
        .map { it.trim() }
        .map { it.substring(1, it.length - 1) }
        .map { it ->
            {
                val (x, y, z) = it.split(", ")
                    .map { it.split("=").get(1) }
                    .map { it.toInt() }
                    .take(3)
                    .toList()
                Triple(x, y, z)
            }
        }
        .map { it.invoke() }
        .toList()

    var x_axis = positions.map { it.first }.toList()
    var y_axis = positions.map { it.second }.toList()
    var z_axis = positions.map { it.third }.toList()

    val sol: Long = lcm(lcm(solve(x_axis), solve(y_axis)), solve(z_axis))
    println(sol)
}

main()
