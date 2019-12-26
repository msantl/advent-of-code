import java.io.File

fun getIJ(v: Int, N: Int): Pair<Int, Int> {
    return (v / N) to (v % N)
}

fun getV(i: Int, j: Int, N: Int): Int {
    return (i * N + j)
}

fun main() {
    val dir: List<Pair<Int, Int>> = listOf(0 to 1, 1 to 0, 0 to -1, -1 to 0)
    var map: MutableMap<Pair<Int, Int>, Boolean> = mutableMapOf()

    File("first.in").readLines().withIndex().forEach { it ->
        it.value.trim().withIndex()
            .forEach { row -> map.put(it.index to row.index, if (row.value == '#') true else false) }
    }

    val bio: MutableMap<Long, Int> = mutableMapOf()
    var iteration = 0

    while (true) {
        var pot: Long = 1
        var mask: Long = 0
        for (i in 0 until 25) {
            if (map.getValue(getIJ(i, 5))) {
                mask += pot
            }
            pot *= 2
        }

        if (bio.containsKey(mask)) {
            println(mask)
            break
        }

        bio.put(mask, iteration)

       val newMap: MutableMap<Pair<Int, Int>, Boolean> = mutableMapOf()

        for (i in 0 until 25) {
            val (x, y) = getIJ(i, 5)
            var neighbors = 0
            for (d in dir) {
                val next = (x + d.first) to (y + d.second)
                if (map.getOrDefault(next, false)) neighbors += 1
            }

            if (map.getValue(x to y)) {
                if (neighbors != 1) {
                    newMap.put(x to y, false)
                } else {
                    newMap.put(x to y, true)
                }
            } else {
                if (neighbors == 1 || neighbors == 2) {
                    newMap.put(x to y, true)
                } else {
                    newMap.put(x to y, false)
                }
            }
        }

        map = newMap
        iteration += 1
    }
}

main()