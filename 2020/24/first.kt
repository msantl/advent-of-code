import java.io.File

data class HexTile(val x: Int, val y: Int, val z: Int) {
    fun isValidd() = (x + y + z == 0)

    fun move(dx: Int, dy: Int, dz: Int): HexTile {
        return HexTile(x + dx, y + dy, z + dz)
    }
}

val deltas = mapOf<String, Triple<Int, Int, Int>>(
    "e" to Triple(+1, -1, 0),
    "se" to Triple(0, -1, +1),
    "sw" to Triple(-1, 0, +1),
    "w" to Triple(-1, +1, 0),
    "nw" to Triple(0, +1, -1),
    "ne" to Triple(+1, 0, -1),
)

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

    println(colors.values.filter { it == true }.count())
}

main("first.in")
