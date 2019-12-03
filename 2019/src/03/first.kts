import java.io.File

fun distance(coord: Pair<Int, Int>) : Int {
    return Math.abs(coord.first) + Math.abs(coord.second)
}

fun main() {
    val input = File("first.in").readLines()
    assert(input.size == 2)
    val wire1: List<String> = input.get(0).split(",").map { it.trim() }
    val wire2: List<String> = input.get(1).split(",").map { it.trim() }

    var position: Pair<Int, Int> = Pair(0, 0)
    var visited: HashSet<Pair<Int, Int>> = HashSet<Pair<Int, Int>>()

    for (command in wire1) {
        val direction: Char = command[0]
        val value: Int = command.substring(1).toInt()

        for (i in 1..value) {
            if (direction == 'R') {
                position = Pair(position.first + 1, position.second)
            } else if (direction == 'L') {
                position = Pair(position.first - 1, position.second)
            } else if (direction == 'U') {
                position = Pair(position.first, position.second + 1)
            } else if (direction == 'D') {
                position = Pair(position.first, position.second - 1)
            }

            visited.add(position)
        }
    }

    var result: Int = Int.MAX_VALUE

    position = Pair(0, 0)
    for (command in wire2) {
        val direction: Char = command[0]
        val value: Int = command.substring(1).toInt()

        for (i in 1..value) {
            if (direction == 'R') {
                position = Pair(position.first + 1, position.second)
            } else if (direction == 'L') {
                position = Pair(position.first - 1, position.second)
            } else if (direction == 'U') {
                position = Pair(position.first, position.second + 1)
            } else if (direction == 'D') {
                position = Pair(position.first, position.second - 1)
            }

            if (visited.contains(position)) {
                val current: Int = distance(position)
                if (current < result) {
                    result = current
                }

            }
        }
    }

    print(result)
}

main()