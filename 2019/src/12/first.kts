import java.io.File
import kotlin.math.abs

fun main() {
    var positions: List<Triple<Int, Int, Int>> = File("first.in").readLines()
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

    var velocities: List<Triple<Int, Int, Int>> = generateSequence(0) { it + 1 }
        .take(positions.size)
        .map { _ -> Triple(0, 0, 0) }
        .toList()

    for (step in 1..1000) {
        var newVelocities: List<Triple<Int, Int, Int>> = emptyList()
        var newPositions: List<Triple<Int, Int, Int>> = emptyList()
        for (i in 0 until positions.size) {
            var (newX, newY, newZ) = velocities.get(i)

            val (x, y, z) = positions.get(i)
            for (j in 0 until positions.size) {
                if (i == j) continue
                val (a, b, c) = positions.get(j)
                if (x > a) newX -= 1
                else if (x < a) newX += 1

                if (y > b) newY -= 1
                else if (y < b) newY += 1

                if (z > c) newZ -= 1
                else if (z < c) newZ += 1
            }
            newPositions = newPositions.plus(Triple(x + newX, y + newY, z + newZ))
            newVelocities = newVelocities.plus(Triple(newX, newY, newZ))
        }
        positions = newPositions
        velocities = newVelocities
    }

    var sol: Int = 0
    for (i in 0 until positions.size) {
        sol += positions.get(i).toList().map { abs(it) }.sum() * velocities.get(i).toList().map { abs(it) }.sum()
    }
    println(sol)
}

main()
