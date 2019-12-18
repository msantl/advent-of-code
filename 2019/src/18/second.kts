import java.io.File

fun main() {
    val map: MutableMap<Pair<Int, Int>, Char> = mutableMapOf()
    val bio: MutableMap<Triple<Int, Int, Long>, Int> = mutableMapOf()

    val d = listOf(0 to 1, 1 to 0, -1 to 0, 0 to -1)

    File("second.in").readLines().withIndex()
        .forEach { i -> i.value.withIndex().forEach { j -> map.put(i.index to j.index, j.value) } }

    val startingPoints = map.entries.filter { it.value == '@' }.map { it.key }
    val totalKeys = map.entries.filter { it.value >= 'a' && it.value <= 'z' }.count()
    val queue: MutableList<Pair<List<Triple<Int, Int, Int>>, Long>> = mutableListOf()

    queue.add(startingPoints.map { it -> Triple(it.first, it.second, 0)} to 0L )
    for (start in startingPoints) {
        bio.put(Triple(start.first, start.second, 0), 0)
    }

    while (!queue.isEmpty()) {
        val (robots, keys) = queue.removeAt(0)

        var hasAllKeys = true
        for (bit in 0 until totalKeys) {
            if ((keys shr bit) and 1 == 0L) {
                hasAllKeys = false
                break
            }
        }

        if (hasAllKeys) {
            println(robots.map { it.third }.toList())
            println(robots.map {it.third}.sum())
            break
        }

        for (robot in robots) {
            val (x, y, steps) = robot

            for (dir in d) {
                val next = (x + dir.first) to (y + dir.second)
                if (!map.containsKey(next)) continue

                val field = map.getValue(next)
                if (field == '#') continue

                var newKeys = keys

                if (field >= 'a' && field <= 'z') {
                    newKeys = keys or (1L shl (field.toInt() - 'a'.toInt()))
                } else if (field >= 'A' && field <= 'Z') {
                    val hasKey = (keys shr (field.toInt() - 'A'.toInt())) and 1;
                    if (hasKey == 0L) {
                        continue
                    }
                }

                val entry = Triple(next.first, next.second, newKeys)

                if (!bio.containsKey(entry) || bio.getValue(entry) > steps + 1) {
                    bio.put(entry, steps + 1)
                    queue.add( robots.minus(robot).plus(Triple(next.first, next.second, steps + 1)) to newKeys )
                }
            }
        }
    }

}

main()