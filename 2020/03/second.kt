import java.io.File

fun countTrees(input: List<String>, dx: Int, dy: Int): Int {
    val R = input.size
    val C = input[0].length
    var (cx, cy) = listOf(0, 0)

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

    return solution
}

fun main(filename: String) {
    val input = File(filename).readLines()
    val slopes = listOf(Pair(1, 1), Pair(3, 1), Pair(5, 1), Pair(7, 1), Pair(1, 2))

    var solution = 1
    for ((dx, dy) in slopes) {
        solution *= countTrees(input, dx, dy)
    }
    println(solution)
}

main("second.in")
