import java.io.File

fun main(filename: String) {
    val input = File(filename).readLines()
    val (dx, dy) = listOf(3, 1)
    var (cx, cy) = listOf(0, 0)

    val R = input.size
    val C = input[0].length

    var solution = 0
    while (cy < R) {
        val (nx, ny) = listOf((cx + dx) % C, cy + dy)
        if (ny >= R) break;

        if (input[ny][nx] == '#') {
            solution += 1
        }

        cx = nx
        cy = ny
    }

    println(solution)
}

main("first.in")
