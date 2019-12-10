import java.io.File
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.max
import kotlin.math.sqrt

fun getLineCoef(a: Pair<Double, Double>, b: Pair<Double, Double>): Double {
    // Ax + By + C = 0
    // (x2 - x1)·(y - y1) = (y2 - y1)·(x - x1),
    // (x2 - x1) y - (x2 - x1) y1 = (y2 - y1) x - (y2 - y1) x1
    // A = y2 - y1
    // B = x1 - x2
    // C = (x2 - x1) y1 - (y2 - y1) x1
    var A: Double = b.second - a.second
    var B: Double = a.first - b.first
    // y = kx + l
    // k = -A / B = tan alfa

    var angle: Double = if (a.first == b.first) {
        if (a.second > b.second) PI / 2 else -PI / 2
    } else {
        atan(-A / B)
    }

    if (a.first > b.first) angle += PI
    while (angle > 2 * PI) angle = angle - 2 * PI

    angle = PI / 2 - angle

    while (angle < 0.0) angle = angle + 2 * PI
    return angle
}

fun distance(a: Pair<Double, Double>, b: Pair<Double, Double>): Double {
    var A = (a.first - b.first)
    var B = (a.second - b.second)

    return sqrt(A * A - B * B);
}

fun main() {
    val input: List<String> = File("second.in").readLines()
    var points: List<Pair<Double, Double>> = emptyList()

    for (i in 0 until input.size) {
        val line: String = input.get(i)
        for (j in 0 until line.length) {
            if (line[j] == '#') {
                points = points.plus(j.toDouble() to 100 - i.toDouble())
            }
        }
    }
    var maxSeenId: Int = 0
    var maxSeen: Int = Int.MIN_VALUE

    for (i in 0 until points.size) {
        val counter: HashSet<Double> = hashSetOf()
        for (j in 0 until points.size) {
            if (i == j) continue

            counter.add(getLineCoef(points[i], points[j]))
        }

        if (maxSeen < counter.size) {
            maxSeen = counter.size
            maxSeenId = i
        }
    }

    var laser: MutableMap<Double, List<Pair<Double, Int>>> = mutableMapOf()

    for (j in 0 until points.size) {
        if (maxSeenId == j) continue

        val key: Double = getLineCoef(points[maxSeenId], points[j])
        val curr = laser.getOrDefault(key, emptyList())
        val value = curr.plus(distance(points[maxSeenId], points[j]) to j)

        laser.put(key, value)
    }

    var order: MutableList<Pair<Double, Int>> = mutableListOf()

    for (angle in laser.keys) {
        var sorted: List<Int> = laser.getValue(angle)
            .sortedBy { it.first }
            .map { it.second }
            .toList()

        for (i in 0 until sorted.size) {
            var position: Double = i * 2 * PI + angle
            order.add(position to sorted[i])
        }
    }

    var id = order.sortedBy { it.first }.map { it.second }.toList()

    var sol = points[id[199]]
    println(100 * sol.first + 100 - sol.second)
}

main()