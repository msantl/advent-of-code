import java.io.File

val ACTIVE = '#'

fun getActiveNeighbours(
    me: Triple<Int, Int, Int>, space: Set<Triple<Int, Int, Int>>
): Int {
    val neighbours = getNeighbours(me)
    return neighbours.filter { space.contains(it) }.count()
}

fun getNeighbours(me: Triple<Int, Int, Int>): List<Triple<Int, Int, Int>> {
    var result = emptyList<Triple<Int, Int, Int>>()
    val delta = listOf(-1, 0, 1)
    val (x, y, z) = me
    for (dx in delta) {
        for (dy in delta) {
            for (dz in delta) {
                val neighbour = Triple(x + dx, y + dy, z + dz)
                if (neighbour != me) {
                    result = result.plus(neighbour)
                }
            }
        }
    }
    return result
}

fun main(filename: String) {
    var space = emptySet<Triple<Int, Int, Int>>()
    File(filename).readLines().withIndex().forEach { (i, it) ->
        it.withIndex().forEach { (j, c) ->
            if (c == ACTIVE) {
                space = space.plus(Triple(j, i, 0))
            }
        }
    }

    for (ITER in 0..5) {
        var newSpace = emptySet<Triple<Int, Int, Int>>()
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

main("first.in")
