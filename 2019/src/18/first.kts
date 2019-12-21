import java.io.File

fun main() {
    val map: MutableMap<Pair<Int, Int>, Char> = mutableMapOf()
    val bio: MutableMap<Triple<Int, Int, Long>, Int> = mutableMapOf()

    val d = listOf(0 to 1, 1 to 0, -1 to 0, 0 to -1)

    File("first.in").readLines().withIndex()
        .forEach { i -> i.value.withIndex().forEach { j -> map.put(i.index to j.index, j.value) } }

    val start = map.entries.filter { it.value == '@' }.first().key
    val totalKeys = map.entries.filter { it.value >= 'a' && it.value <= 'z' }.count()
    val queue: MutableList<Triple<Pair<Int, Int>, Int, Long>> = mutableListOf()

    queue.add(Triple(start, 0, 0))
    bio.put(Triple(start.first, start.second, 0), 0)

    while (!queue.isEmpty()) {
        val (curr, steps, keys) = queue.removeAt(0)

        var hasAllKeys = true
        for (bit in 0 until totalKeys) {
            if ((keys shr bit) and 1 == 0L) {
                hasAllKeys = false
                break
            }
        }

        if (hasAllKeys) {
            println(steps)
            break
        }

        for (dir in d) {
            val next = (curr.first + dir.first) to (curr.second + dir.second)
            if (!map.containsKey(next)) continue

            val field = map.getValue(next)
            if (field == '#') continue

            var newKeys = keys

            if (field >= 'a' && field <= 'z') {
                newKeys = keys or (1L shl (field.toInt() - 'a'.toInt()))
            } else if (field >= 'A' && field <= 'Z') {
                val hasKey = (keys shr (field.toInt() - 'A'.toInt())) and 1;
                if (hasKey == 0L)  {
                    continue
                }
            }

            val entry = Triple(next.first, next.second, newKeys)

            if (!bio.containsKey(entry) || bio.getValue(entry) > steps + 1) {
                bio.put(entry, steps + 1)
                queue.add(Triple(next, steps + 1, newKeys))
            }
        }
    }

}

main()