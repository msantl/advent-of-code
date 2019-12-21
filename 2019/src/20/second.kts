import java.io.File

val map: MutableMap<Pair<Int, Int>, Char> = mutableMapOf()
val portals: MutableMap<String, List<Pair<Int, Int>>> = mutableMapOf()
val dir: List<Pair<Int, Int>> = listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)

val outer: MutableSet<Pair<Int, Int>> = mutableSetOf()

fun getPortal(pos: Pair<Int, Int>): Pair<Pair<Int, Int>, Pair<Int, Int>>? {
    val dd = listOf(1 to 0, 0 to 1)

    for (d in dd) {
        val next = (pos.first + d.first) to (pos.second + d.second)
        val mark = map.getOrDefault(next, ' ')

        if ('A' <= mark && mark <= 'Z') {
            // Find the point where the portal takes you
            for (gd in dir) {
                val point1 = (next.first + gd.first) to (next.second + gd.second)
                val point2 = (pos.first + gd.first) to (pos.second + gd.second)

                if (map.getOrDefault(point1, ' ') == '.') return next to point1
                if (map.getOrDefault(point2, ' ') == '.') return next to point2
            }
        }
    }

    return null
}

fun isOuter(a: Pair<Int, Int>, b: Pair<Int, Int>, n: Int, m: Int): Boolean {
    if (a.first == 0 || b.first == 0) return true
    if (a.first == n || b.first == n) return true

    if (a.second == 0 || b.second == 0) return true
    if (a.second == m || b.second == m) return true
    return false
}

fun main() {
    File("second.in").readLines().withIndex().toList()
        .forEach { indexedRow ->
            indexedRow.value.withIndex().toList()
                .forEach { indexedColumn -> map.put(indexedRow.index to indexedColumn.index, indexedColumn.value) }
        }

    val possiblePortals = map.entries.filter { it -> it.value >= 'A' && it.value <= 'Z' }.map { it.key }.toList()

    val N = map.keys.map { it.first }.max()!!
    val M = map.keys.map { it.second }.max()!!

    for (portal in possiblePortals) {
        val next = getPortal(portal)
        if (next == null) continue
        val (p, f) = next
        val code = map.getValue(portal).toString() + map.getValue(p).toString()

        val curr = portals.getOrDefault(code, emptyList())
        portals.put(code, curr.plus(f))

        if (isOuter(portal, p, N, M)) {
            outer.add(f)
        }
    }

    val start = portals.getOrDefault("AA", emptyList()).first()
    val end = portals.getOrDefault("ZZ", emptyList()).first()
    val queue: MutableList<Triple<Pair<Int, Int>, Int, Int>> = mutableListOf()
    val bio: MutableMap<Pair<Pair<Int, Int>, Int>, Int> = mutableMapOf()

    bio.put(start to 0, 0)
    queue.add(Triple(start, 0, 0))

    while (!queue.isEmpty()) {
        val (now, level, step) = queue.removeAt(0)
        if (level > 50) continue

        if (level == 0 && now == end) {
            println(step)
            break
        }

        for (d in dir) {
            val toPoint = (now.first + d.first) to (now.second + d.second)
            val to = map.getOrDefault(toPoint, ' ')

            if (to == '.') {
                if (bio.getOrDefault(toPoint to level, Int.MAX_VALUE) > step + 1) {
                    bio.put(toPoint to level, step + 1)
                    queue.add(Triple(toPoint, level, step + 1))
                }
            } else if (to >= 'A' && to <= 'Z') {
                val fromPoint = (toPoint.first + d.first) to (toPoint.second + d.second)
                val from = map.getOrDefault(fromPoint, ' ')

                val code =
                    if (d.first + d.second < 0) from.toString() + to.toString() else to.toString() + from.toString();
                val ps = portals.getValue(code)

                if (level == 0 && outer.contains(now)) continue
                if (level > 0 && code == "AA" || code == "ZZ") continue

                for (portal in ps) {
                    if (portal == now) continue

                    val nextLevel = if (outer.contains(now)) level - 1 else level + 1

                    if (bio.getOrDefault(portal to nextLevel, Int.MAX_VALUE) > step + 1) {
                        bio.put(portal to nextLevel, step + 1)
                        queue.add(Triple(portal, nextLevel, step + 1))
                    }
                }
            }

        }
    }
}

main()