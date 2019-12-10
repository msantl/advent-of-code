import java.io.File
import kotlin.math.abs

fun getLineCoef(a: Pair<Double, Double>, b: Pair<Double, Double>): Pair<Double, Boolean> {
    // Ax + By + C = 0
    // (x2 - x1)·(y - y1) = (y2 - y1)·(x - x1),
    // (x2 - x1) y - (x2 - x1) y1 = (y2 - y1) x - (y2 - y1) x1
    // A = y2 - y1
    // B = x1 - x2
    // C = (x2 - x1) y1 - (y2 - y1) x1
    var A: Double = b.second - a.second
    var B: Double = a.first - b.first
//    var C: Double = (b.first - a.first) * a.second - (b.second - a.second) * a.first

    return (-A / B) to (a.first < b.first)
}

fun main() {
    val input: List<String> = File("first.in").readLines()
    var points: List<Pair<Double, Double>> = emptyList()

    for (i in 0 until input.size) {
        val line: String = input.get(i)
        for (j in 0 until line.length) {
            if (line[j] == '#') {
                points = points.plus(j.toDouble() to i.toDouble())
            }
        }
    }
    var maxSeenId: Int = 0
    var maxSeen: Int = Int.MIN_VALUE

    for (i in 0 until points.size) {
        val counter: HashSet<Pair<Double, Boolean>> = hashSetOf()
        for (j in 0 until points.size) {
            if (i == j) continue

            counter.add(getLineCoef(points[i], points[j]))
        }

        if (maxSeen < counter.size) {
            maxSeen = counter.size
            maxSeenId = i
        }
    }

    println(maxSeen)
    println(points[maxSeenId])
}

main()