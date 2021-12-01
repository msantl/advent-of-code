import java.io.File

val direction =
    listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0), Pair(1, 1), Pair(1, -1), Pair(-1, 1), Pair(-1, -1))

val EMPTY = 'L'
val OCCUPIED = '#'
val FLOOR = '.'

fun getNumberOfOccupiedSeats(r: Int, c: Int, grid: List<String>): Int {
    val R = grid.size
    val C = grid[0].length
    var count = 0
    for ((dr, dc) in direction) {
        val nr = r + dr
        val nc = c + dc

        if (nc < 0 || nr < 0 || nc >= C || nr >= R) continue
        if (grid[nr][nc] == OCCUPIED) count += 1
    }
    return count
}

fun main(filename: String) {
    val input = File(filename).readLines().toMutableList()
    val R = input.size
    val C = input[0].length

    var grid = input

    while (true) {
        var changed = false
        val newGrid = mutableListOf<String>()
        for (r in 0..R - 1) {
            var row = ""
            for (c in 0..C - 1) {
                when (grid[r][c]) {
                    EMPTY -> {
                        val occupied = getNumberOfOccupiedSeats(r, c, grid)
                        if (occupied == 0) {
                            row += OCCUPIED
                            changed = true
                        } else {
                            row += EMPTY
                        }
                    }
                    OCCUPIED -> {
                        val occupied = getNumberOfOccupiedSeats(r, c, grid)
                        if (occupied >= 4) {
                            row += EMPTY
                            changed = true
                        } else {
                            row += OCCUPIED
                        }
                    }
                    FLOOR -> {
                        row += FLOOR
                    }
                }
            }
            newGrid.add(row)
        }
        grid = newGrid
        if (!changed) break
    }

    var solution = 0
    for (r in 0..R - 1) {
        for (c in 0..C - 1) {
            if (grid[r][c] == OCCUPIED) solution += 1
        }
    }

    println(solution)
}

main("first.in")
