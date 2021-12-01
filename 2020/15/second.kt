import java.io.File

fun main(filename: String) {
    val input = File(filename).readLines().first().split(",").map { it.toInt() }

    val lastSeen = mutableMapOf<Int, Int>()
    var f : Int? = null

    for (i in 1 .. 30000000) {
        var newF = 0
        if (i - 1 < input.size) {
            newF = input[i - 1]
        } else {
            if (lastSeen.containsKey(f)) {
                val lastSeenF = lastSeen.get(f)!!
                newF = i - 1 - lastSeenF
            }
        }

        if (f != null) {
            lastSeen[f] = i - 1
        }
        f = newF
    }

    println(f)

}

main("second.in")
