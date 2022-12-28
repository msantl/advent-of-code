package `2022_24`

import java.io.File

// assumption: no wind will go up/down in the first and last colum!

enum class Direction(val dx: Int, val dy: Int) {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    NONE(0, 0)
}

data class Blizzard(val x: Int, val y: Int, val dir: Direction) {
    fun move(n: Int, ROWS: Int, COLS: Int): Blizzard {
        return Blizzard((((x + n * dir.dx) % ROWS) + ROWS) % ROWS, (((y + n * dir.dy) % COLS) + COLS) % COLS, dir)
    }
}

fun bfs(
    start: Pair<Int, Int>,
    end: Pair<Int, Int>,
    ROWS: Int,
    COLS: Int,
    blizzards: MutableList<Blizzard>,
    offset: Int
): Int {
    val queue: MutableList<Pair<Pair<Int, Int>, Int>> = mutableListOf<Pair<Pair<Int, Int>, Int>>()
    val dist: MutableMap<Pair<Pair<Int, Int>, Int>, Int> = mutableMapOf<Pair<Pair<Int, Int>, Int>, Int>()

    dist.put(Pair(start, 0), 0)
    queue.add(Pair(start, 0))

    while (!queue.isEmpty()) {
        val currKey = queue.removeAt(0)

        val (curr, time) = currKey

        if (curr == end) {
            return time
        }

        val newBlizards = blizzards.map { it.move(time + 1 + offset, ROWS, COLS) }.map { Pair(it.x, it.y) }
        for (dir in Direction.values()) {
            val next = Pair(curr.first + dir.dx, curr.second + dir.dy)
            if (next in newBlizards) continue
            if (next.first < 0 || next.first >= ROWS || next.second < 0 || next.second >= COLS) {
                if (next !in listOf(start, end)) continue
            }

            val key = Pair(next, time + 1)
            if (key in dist) continue

            dist.put(key, dist.getValue(currKey) + 1)
            queue.add(key)
        }
    }

    throw Exception("Couldn't find a path")
}

fun first(filename: String) {
    val input: List<String> = File(filename).readLines()

    val blizzards: MutableList<Blizzard> = mutableListOf<Blizzard>()

    var rows: Int = 0
    var cols: Int = 0
    for (line in input) {
        cols = 0
        for (ch in line) {
            val bliz = when (ch) {
                '<' -> Blizzard(rows - 1, cols - 1, Direction.LEFT)
                '>' -> Blizzard(rows - 1, cols - 1, Direction.RIGHT)
                '^' -> Blizzard(rows - 1, cols - 1, Direction.UP)
                'v' -> Blizzard(rows - 1, cols - 1, Direction.DOWN)
                else -> null
            }

            if (bliz != null) blizzards.add(bliz)
            cols += 1
        }
        rows += 1
    }

    val start: Pair<Int, Int> = Pair(-1, 0)
    val end: Pair<Int, Int> = Pair(rows - 2, cols - 3)

    println(bfs(start, end, rows - 2, cols - 2, blizzards, 0))
}

first("test.in") // 18
first("first.in")


fun second(filename: String) {
    val input: List<String> = File(filename).readLines()

    val blizzards: MutableList<Blizzard> = mutableListOf<Blizzard>()

    var rows: Int = 0
    var cols: Int = 0
    for (line in input) {
        cols = 0
        for (ch in line) {
            val bliz = when (ch) {
                '<' -> Blizzard(rows - 1, cols - 1, Direction.LEFT)
                '>' -> Blizzard(rows - 1, cols - 1, Direction.RIGHT)
                '^' -> Blizzard(rows - 1, cols - 1, Direction.UP)
                'v' -> Blizzard(rows - 1, cols - 1, Direction.DOWN)
                else -> null
            }

            if (bliz != null) blizzards.add(bliz)
            cols += 1
        }
        rows += 1
    }

    val start: Pair<Int, Int> = Pair(-1, 0)
    val end: Pair<Int, Int> = Pair(rows - 2, cols - 3)

    val first = bfs(start, end, rows - 2, cols - 2, blizzards, 0)
    val second = bfs(end, start, rows - 2, cols - 2, blizzards, first)
    val third = bfs(start, end, rows - 2, cols - 2, blizzards, first + second)

    println(first + second + third)
}

second("test.in") // 54
second("first.in")
