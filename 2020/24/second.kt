import java.io.File

data class HexTile(val x: Int, val y: Int, val z: Int) {
    fun move(dx: Int, dy: Int, dz: Int) = HexTile(x + dx, y + dy, z + dz)
}

val deltas = mapOf<String, Triple<Int, Int, Int>>(
    "e" to Triple(+1, -1, 0),
    "se" to Triple(0, -1, +1),
    "sw" to Triple(-1, 0, +1),
    "w" to Triple(-1, +1, 0),
    "nw" to Triple(0, +1, -1),
    "ne" to Triple(+1, 0, -1),
)

fun getNeighbours(from: HexTile): List<HexTile> {
    val result = mutableListOf<HexTile>()
    val (x, y, z) = from
    for ((dx, dy, dz) in deltas.values) {
        result.add(HexTile(x + dx, y + dy, z + dz))
    }
    return result
}

val colors = mutableMapOf<HexTile, Boolean>()

fun main(filename: String) {
    var buffer = ""
    File(filename).readLines().forEach {
        var tile = HexTile(0, 0, 0)
        it.forEach { c ->
            when (c) {
                's', 'n' -> buffer += c
                'w', 'e' -> {
                    buffer += c
                    val (dx, dy, dz) = deltas.get(buffer) ?: throw Exception("Missing delta for $buffer")
                    tile = tile.move(dx, dy, dz)
                    buffer = ""
                }
                else -> throw Exception("Unexpected direction $c")
            }
        }
        colors[tile] = !colors.getOrDefault(tile, false)
    }

    var blackTiles = colors.filter { (_, v) -> v == true }.map { (k, _) -> k }.toSet()
    for (i in 1..100) {
        var newBlackTiles = mutableSetOf<HexTile>()
        val neighbourCount = mutableMapOf<HexTile, Int>()

        blackTiles.map { getNeighbours(it) }.flatten().forEach {
            neighbourCount[it] = neighbourCount.getOrDefault(it, 0) + 1
        }

        neighbourCount.forEach { (k, v) ->
            if (blackTiles.contains(k)) {
                if (v > 0 && v < 3) {
                    newBlackTiles.add(k)
                }
            } else {
                if (v == 2) {
                    newBlackTiles.add(k)
                }
            }
        }

        blackTiles = newBlackTiles
    }

    println(blackTiles.size)
}

main("second.in")
