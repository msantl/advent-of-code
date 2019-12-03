import java.io.File

fun main() {
    val input = File("second.in").readLines()
    assert(input.size == 2)
    val wire1: List<String> = input.get(0).split(",").map { it.trim() }
    val wire2: List<String> = input.get(1).split(",").map { it.trim() }

    var visited: HashMap<Pair<Int, Int>, Int> = HashMap<Pair<Int, Int>, Int>()

    var position: Pair<Int, Int> = Pair(0, 0)
    var steps: Int = 0

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
            steps++
            visited.putIfAbsent(position, steps)
        }
    }

    var result: Int = Int.MAX_VALUE

    position = Pair(0, 0)
    steps = 0

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

            steps++
            if (visited.containsKey(position)) {
                if (steps + visited.getOrDefault(position, 0) < result) {
                    result = steps + visited.getOrDefault(position, 0)
                }
            }
        }
    }

    print(result)
}

main()