package `2022_17`

import java.io.File

val sprites: List<List<Pair<Int, Int>>> = listOf(
    listOf(Pair(0, 0), Pair(0, 1), Pair(0, 2), Pair(0, 3)),
    listOf(Pair(0, 1), Pair(-1, 0), Pair(-1, 1), Pair(-1, 2), Pair(-2, 1)),
    listOf(Pair(0, 2), Pair(-1, 2), Pair(-2, 0), Pair(-2, 1), Pair(-2, 2)),
    listOf(Pair(0, 0), Pair(-1, 0), Pair(-2, 0), Pair(-3, 0)),
    listOf(Pair(0, 0), Pair(0, 1), Pair(-1, 0), Pair(-1, 1)),
)

val verticalOffset: List<Int> = listOf(4, 6, 6, 7, 5)

fun first(filename: String) {
    val input: String = File(filename).readLines().first()
    var inputPtr: Int = 0

    val grid: MutableMap<Pair<Int, Int>, Char> = mutableMapOf<Pair<Int, Int>, Char>()

    var rocks = 0

    var highestBrick = -1

    var lastSprint: Int = -1
    var currentSprite: Int? = null
    var offset: Pair<Int, Int> = Pair(0, 0)

    while (rocks < 2023) {
        if (currentSprite == null) {
            currentSprite = (lastSprint + 1) % sprites.size
            lastSprint = currentSprite

            offset = Pair(highestBrick + verticalOffset[currentSprite], 2)
            rocks += 1
        }

        val moveChar = input[inputPtr]
        inputPtr = (inputPtr + 1) % input.length


        val move =
            if (moveChar == '<') Pair(0, -1)
            else if (moveChar == '>') Pair(0, 1)
            else throw Exception("Invalid move character")

        var canMove = true

        for (baseCoord in sprites[currentSprite!!]) {
            var coord =
                Pair(baseCoord.first + offset.first + move.first, baseCoord.second + offset.second + move.second)

            // try moving in the direction of the wind
            if (coord.second < 0 || coord.second >= 7 || grid.getOrDefault(coord, '.') != '.') {
                canMove = false
                break
            }
        }

        if (canMove) {
            offset = Pair(offset.first + move.first, offset.second + move.second)
        }

        canMove = true
        for (baseCoord in sprites[currentSprite!!]) {
            var coord = Pair(baseCoord.first + offset.first - 1, baseCoord.second + offset.second)

            // try moving down
            if (coord.first < 0 || grid.getOrDefault(coord, '.') != '.') {
                canMove = false
                break
            }
        }

        if (canMove) {
            offset = Pair(offset.first - 1, offset.second)
        } else {
            for (baseCoord in sprites[currentSprite!!]) {
                var coord = Pair(baseCoord.first + offset.first, baseCoord.second + offset.second)
                grid.put(coord, '#')

                highestBrick = Math.max(highestBrick, coord.first)
            }

            currentSprite = null
        }
    }

    println(highestBrick + 1)
}

first("test.in") // 3068
first("first.in")


fun second(filename: String) {
    val input: String = File(filename).readLines().first()
    var inputPtr: Int = 0

    val grid: MutableMap<Pair<Long, Long>, Char> = mutableMapOf<Pair<Long, Long>, Char>()

    var rocks: Long = 0L

    var lastSprint: Int = sprites.size - 1
    var currentSprite: Int? = null
    var offset: Pair<Long, Long> = Pair(0, 0)

    // Layout of the floor
    // 5 4 5 3 2 1
    // current sprite
    // inputPtr

    // if those match we have a loop
    var key: Triple<Long, Int, Int> = Triple(0L, 0, 0)
    val bio: MutableMap<Triple<Long, Int, Int>, Pair<Long, Long>> =
        mutableMapOf<Triple<Long, Int, Int>, Pair<Long, Long>>()
    val heights: MutableList<Long> = mutableListOf(-1L, -1L, -1L, -1L, -1L, -1L, -1L)

    var highestBrick: Long = -1L
    var additionalHeight: Long = 0L

    val maxRocks: Long = 1000000000000L

    while (rocks <= maxRocks) {
        if (currentSprite == null) {
            currentSprite = (lastSprint + 1) % sprites.size
            lastSprint = currentSprite!!
            rocks += 1

            // check if we have seen this state already
            var height: Long = 0
            for (h in heights) {
                height = height * 10007L + highestBrick - h
            }

            key = Triple(height, lastSprint, inputPtr)
            if (key in bio) {
                val (prevHighestBrick, rockId) = bio.getValue(key)

                val rocksInBetween = rocks - rockId
                val heightInBetween = highestBrick - prevHighestBrick

                val k = (maxRocks - rocks) / rocksInBetween
                if (k > 0) {
                    println("Found match at $rocks $highestBrick, $key -> $prevHighestBrick $rockId")
                    rocks += k * rocksInBetween
                    additionalHeight += k * heightInBetween
                }
            }

            offset = Pair(highestBrick + verticalOffset[currentSprite!!], 2L)
        }

        val moveChar = input[inputPtr]
        inputPtr = (inputPtr + 1) % input.length

        val move =
            if (moveChar == '<') Pair(0, -1)
            else if (moveChar == '>') Pair(0, 1)
            else throw Exception("Invalid move character")

        var canMove = true

        for (baseCoord in sprites[currentSprite!!]) {
            var coord =
                Pair(baseCoord.first + offset.first + move.first, baseCoord.second + offset.second + move.second)

            // try moving in the direction of the wind
            if (coord.second < 0L || coord.second >= 7L || grid.getOrDefault(coord, '.') != '.') {
                canMove = false
                break
            }
        }

        if (canMove) {
            offset = Pair(offset.first + move.first, offset.second + move.second)
        }

        canMove = true
        for (baseCoord in sprites[currentSprite!!]) {
            var coord = Pair(baseCoord.first + offset.first - 1, baseCoord.second + offset.second)

            // try moving down
            if (coord.first < 0L || grid.getOrDefault(coord, '.') != '.') {
                canMove = false
                break
            }
        }

        if (canMove) {
            offset = Pair(offset.first - 1, offset.second)
        } else {
            bio.put(key, Pair(highestBrick, rocks))

            for (baseCoord in sprites[currentSprite!!]) {
                var coord = Pair(baseCoord.first + offset.first, baseCoord.second + offset.second)
                grid.put(coord, '#')

                highestBrick = Math.max(highestBrick, coord.first)

                heights[coord.second.toInt()] = Math.max(heights[coord.second.toInt()], coord.first)
            }

            currentSprite = null
        }
    }

    println(additionalHeight + highestBrick + 1)
}

second("test.in") // 1514285714288
second("first.in")
