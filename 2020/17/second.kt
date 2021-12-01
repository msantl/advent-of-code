import java.io.File

val ACTIVE = '#'

data class Cube(val x: Int, val y: Int, val z: Int, val w: Int)

fun getActiveNeighbours(
    me: Cube, space: Set<Cube>
): Int {
    val neighbours = getNeighbours(me)
    return neighbours.filter { space.contains(it) }.count()
}

fun getNeighbours(me: Cube): List<Cube> {
    var result = emptyList<Cube>()
    val delta = listOf(-1, 0, 1)
    val (x, y, z, w) = me
    for (dx in delta) {
        for (dy in delta) {
            for (dz in delta) {
                for (dw in delta) {
                    val neighbour = Cube(x + dx, y + dy, z + dz, w + dw)
                    if (neighbour != me) {
                        result = result.plus(neighbour)
                    }
                }
            }
        }
    }
    return result
}

fun main(filename: String) {
    var space = emptySet<Cube>()
    File(filename).readLines().withIndex().forEach { (i, it) ->
        it.withIndex().forEach { (j, c) ->
            if (c == ACTIVE) {
                space = space.plus(Cube(j, i, 0, 0))
            }
        }
    }

    for (ITER in 0..5) {
        var newSpace = emptySet<Cube>()
        val candidates = space.plus(space.map { getNeighbours(it) }.flatMap { it })
        candidates.forEach { it ->
            val count = getActiveNeighbours(it, space)
            if (space.contains(it)) {
                if (listOf(2, 3).contains(count)) {
                    newSpace = newSpace.plus(it)
                }
            } else {
                if (listOf(3).contains(count)) {
                    newSpace = newSpace.plus(it)
                }
            }
        }
        space = newSpace
    }

    println(space.count())
}

main("second.in")
