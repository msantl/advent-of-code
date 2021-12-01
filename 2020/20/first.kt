import java.io.File
import java.util.regex.Pattern

data class Tile(val id: Long, val grid: List<String>) {
    fun equals(other: Tile): Boolean {
        return id == other.id
    }

    fun rotate(n: Int): Tile {
        var tile = Tile(id = id, grid = grid)
        for (i in 0..n - 1) {
            tile = tile.rotate()
        }
        return tile
    }

    fun rotate(): Tile {
        var newGrid = mutableListOf<String>()
        for (row in 0..grid.size - 1) {
            newGrid.add(grid.map { it[it.length - row - 1] }.joinToString(separator = ""))
        }
        return Tile(id = id, grid = newGrid)
    }

    fun flipH(): Tile {
        return Tile(id = id, grid.asReversed())
    }

    fun flipV(): Tile {
        return Tile(id = id, grid.map { it.reversed() }.toList())
    }
}

val canGoDown = mutableMapOf<Pair<Long, Int>, MutableList<Pair<Long, Int>>>()
val canGoRight = mutableMapOf<Pair<Long, Int>, MutableList<Pair<Long, Int>>>()
val tiles = mutableListOf<Pair<Long, Int>>()
val tileLookup = mutableMapOf<Pair<Long, Int>, Tile>()

val memo = mutableMapOf<Pair<Int, Pair<Long, Int>>, List<Pair<Long, Int>>>()

fun dfs(used: List<Pair<Long, Int>>, n: Int, N: Int): List<Pair<Long, Int>> {
    if (n == N * N) {
        return used
    }

    val usedIds = used.map { it.first }.toSet()
    val tilesLeft = tiles.filter { usedIds.contains(it.first) == false }.toSet()

    val up = if (n < N) {
        tilesLeft
    } else {
        canGoDown.getOrDefault(used[n - N], mutableListOf<Pair<Long, Int>>())
            .filter { usedIds.contains(it.first) == false }.toSet()
    }

    val left = if ((n % N) == 0) {
        tilesLeft
    } else {
        canGoRight.getOrDefault(used[n - 1], mutableListOf<Pair<Long, Int>>())
            .filter { usedIds.contains(it.first) == false }.toSet()
    }

    for (nextTile in up.intersect(left)) {
        val branch = if (memo.contains(Pair(n + 1, nextTile))) {
            memo[Pair(n + 1, nextTile)] !!
        } else {
            val temp = dfs(used.plus(nextTile), n + 1, N)
            memo[Pair(n + 1, nextTile)] = temp
            temp
        }

        if (branch.size == N * N) {
            return branch
        }
    }
    return emptyList()
}

fun main(filename: String) {
    var currentTileId = 0L
    var currentTile = mutableListOf<String>()
    val tilePattern = Pattern.compile("Tile (\\d+):")

    var startingTiles = mutableListOf<Tile>()

    File(filename).readLines().forEach {
        val tileMatcher = tilePattern.matcher(it)

        if (tileMatcher.matches()) {
            currentTileId = tileMatcher.group(1).toLong()
        } else if (it.length == 0) {
            startingTiles.add(Tile(id = currentTileId, grid = currentTile))
            currentTile = mutableListOf()
            currentTileId = 0
        } else {
            currentTile.add(it)
        }
    }
    startingTiles.add(Tile(id = currentTileId, grid = currentTile))

    var N = 1
    for (i in 1..20) {
        if (i * i == startingTiles.size) {
            N = i
        }
    }

    for (tile in startingTiles) {
        var tileVariation = 0
        for (flip in listOf(0, 1, 2)) {
            var transformedTile = if (flip == 1) {
                tile.flipV()
            } else if (flip == 2) {
                tile.flipH()
            } else {
                tile
            }
            for (k in 0..3) {
                transformedTile = transformedTile.rotate()

                val tilePair = Pair(tile.id, tileVariation)
                tiles.add(tilePair)
                tileLookup[tilePair] = transformedTile
                tileVariation += 1
            }
        }
    }

    for (tile in tiles) {
        canGoDown[tile] = mutableListOf<Pair<Long, Int>>()
        canGoRight[tile] = mutableListOf<Pair<Long, Int>>()
    }

    for (tile1 in tiles) {
        val T1 = tileLookup.get(tile1)!!
        for (tile2 in tiles) {
            if (tile1.first == tile2.first) continue
            val T2 = tileLookup.get(tile2)!!

            if (T2.grid.map { it.first() }.joinToString(separator = "") == T1.grid.map { it.last() }
                    .joinToString(separator = "")) {
                canGoRight[tile1]?.add(tile2)
            }

            if (T2.grid.first() == T1.grid.last()) {
                canGoDown[tile1]?.add(tile2)
            }
        }
    }


    val solution = dfs(emptyList(), 0, N)
    println(solution.map { it.first })

    val s = solution.map { tileLookup[it]!! }
    println(s[0].id * s[N - 1].id * s[N * (N - 1)].id * s[N * N - 1].id)
}

main("first.in")
