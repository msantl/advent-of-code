package `2022_22`

import java.io.File

fun getInput(filename: String): Pair<List<String>, List<Pair<Char, Int>>> {
    val input: List<String> = File(filename).readLines()
    val moves: MutableList<Pair<Char, Int>> = mutableListOf<Pair<Char, Int>>()

    val rawMoves: String = input.last()

    var movesId: Int = 0
    var num: Int = 0
    var c: Char = 'N'
    while (movesId < rawMoves.length) {
        if (rawMoves[movesId] == 'R' || rawMoves[movesId] == 'L') {
            moves.add(Pair(c, num))

            num = 0
            c = rawMoves[movesId]
            movesId += 1
        } else {
            num = num * 10 + rawMoves[movesId].minus('0')
            movesId += 1
        }
    }

    if (num > 0) {
        moves.add(Pair(c, num))
    }

    return Pair(input.take(input.size - 2), moves)
}

// R = 0, D = 1, L = 2, U = 3
var dir: List<Pair<Int, Int>> = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))

fun first(filename: String) {
    val (grid, moves) = getInput(filename)

    var facing: Int = 0
    var pos: Pair<Int, Int> = Pair(0, 0)

    for (i in 0..grid[0].length - 1) {
        if (grid[0][i] == '.') {
            pos = Pair(0, i)
            break
        }
    }

    for ((turn, step) in moves) {
        if (turn == 'R') facing = (facing + 1) % 4
        else if (turn == 'L') facing = (facing + 3) % 4

        for (i in 1..step) {
            var next = Pair(pos.first + dir[facing].first, pos.second + dir[facing].second)

            if (facing in listOf(1, 3)) {
                // up or down
                if (next.first < 0 || next.first >= grid.size || next.second >= grid[next.first].length || grid[next.first][next.second] == ' ') {
                    // check if we can wrap
                    // but before the wrap happens, check if the field is valid
                    if (facing == 1) {
                        for (j in 0..grid.size - 1) {
                            if (next.second >= grid[j].length) continue
                            if (grid[j][next.second] == ' ') continue
                            if (grid[j][next.second] == '#') break


                            pos = Pair(j, next.second)
                            break
                        }
                    } else {
                        for (j in grid.size - 1 downTo 0) {
                            if (next.second >= grid[j].length) continue
                            if (grid[j][next.second] == ' ') continue
                            if (grid[j][next.second] == '#') break

                            pos = Pair(j, next.second)
                            break
                        }
                    }
                } else if (grid[next.first][next.second] == '.') {
                    pos = next
                } else {
                    // otherwise we're stuck
                    break
                }

            } else {
                // left or right
                val col = grid[next.first].length

                if (next.second < 0 || next.second >= col || grid[next.first][next.second] == ' ') {
                    if (facing == 0) {
                        for (j in 0..col - 1) {
                            if (grid[next.first][j] == ' ') continue
                            if (grid[next.first][j] == '#') break

                            pos = Pair(next.first, j)
                            break
                        }
                    } else {
                        for (j in col - 1 downTo 0) {
                            if (grid[next.first][j] == ' ') continue
                            if (grid[next.first][j] == '#') break

                            pos = Pair(next.first, j)
                            break
                        }
                    }
                } else if (grid[next.first][next.second] == '.') {
                    pos = next
                } else {
                    // otherwise we're stuck
                    break
                }
            }
        }
    }


    println(1000 * (pos.first + 1) + 4 * (pos.second + 1) + facing)
}

first("test.in") // 6032
first("first.in")

fun findNextCubeFor(
    currentCube: Int,
    facing: Int,
    pos: Pair<Int, Int>
): Triple<Int, Int, Pair<Int, Int>> {
    // R = 0, D = 1, L = 2, U = 3
    if (currentCube == 0 && facing == 0) return Triple(1, 0, Pair(pos.first, 0))
    else if (currentCube == 0 && facing == 1) return Triple(2, 1, Pair(0, pos.second))
    else if (currentCube == 0 && facing == 2) return Triple(3, 0, Pair(49 - pos.first, 0))
    else if (currentCube == 0 && facing == 3) return Triple(5, 0, Pair(pos.second, 0))

    // R = 0, D = 1, L = 2, U = 3
    else if (currentCube == 1 && facing == 0) return Triple(4, 2, Pair(49 - pos.first, 49))
    else if (currentCube == 1 && facing == 1) return Triple(2, 2, Pair(pos.second, 49))
    else if (currentCube == 1 && facing == 2) return Triple(0, 2, Pair(pos.first, 49))
    else if (currentCube == 1 && facing == 3) return Triple(5, 3, Pair(49, pos.second))

    // R = 0, D = 1, L = 2, U = 3
    else if (currentCube == 2 && facing == 0) return Triple(1, 3, Pair(49, pos.first))
    else if (currentCube == 2 && facing == 1) return Triple(4, 1, Pair(0, pos.second))
    else if (currentCube == 2 && facing == 2) return Triple(3, 1, Pair(0, pos.first))
    else if (currentCube == 2 && facing == 3) return Triple(0, 3, Pair(49, pos.second))

    // R = 0, D = 1, L = 2, U = 3
    else if (currentCube == 3 && facing == 0) return Triple(4, 0, Pair(pos.first, 0))
    else if (currentCube == 3 && facing == 1) return Triple(5, 1, Pair(0, pos.second))
    else if (currentCube == 3 && facing == 2) return Triple(0, 0, Pair(49 - pos.first, 0))
    else if (currentCube == 3 && facing == 3) return Triple(2, 0, Pair(pos.second, 0))

    // R = 0, D = 1, L = 2, U = 3
    else if (currentCube == 4 && facing == 0) return Triple(1, 2, Pair(49 - pos.first, 49))
    else if (currentCube == 4 && facing == 1) return Triple(5, 2, Pair(pos.second, 49))
    else if (currentCube == 4 && facing == 2) return Triple(3, 2, Pair(pos.first, 49))
    else if (currentCube == 4 && facing == 3) return Triple(2, 3, Pair(49, pos.second))

    // R = 0, D = 1, L = 2, U = 3
    else if (currentCube == 5 && facing == 0) return Triple(4, 3, Pair(49, pos.first))
    else if (currentCube == 5 && facing == 1) return Triple(1, 1, Pair(0, pos.second))
    else if (currentCube == 5 && facing == 2) return Triple(0, 1, Pair(0, pos.first))
    else if (currentCube == 5 && facing == 3) return Triple(3, 3, Pair(49, pos.second))
    else throw Exception("Unknown wrap!")
}

fun findNextCubeForSmall(
    currentCube: Int,
    facing: Int,
    pos: Pair<Int, Int>
): Triple<Int, Int, Pair<Int, Int>> {
    // R = 0, D = 1, L = 2, U = 3
    if (currentCube == 0 && facing == 0) return Triple(5, 2, Pair(3 - pos.first, 3))
    else if (currentCube == 0 && facing == 1) return Triple(3, 1, Pair(0, pos.second))
    else if (currentCube == 0 && facing == 2) return Triple(2, 1, Pair(0, pos.first))
    else if (currentCube == 0 && facing == 3) return Triple(1, 0, Pair(0, 3 - pos.second))

    // R = 0, D = 1, L = 2, U = 3
    else if (currentCube == 1 && facing == 0) return Triple(2, 0, Pair(pos.first, 0))
    else if (currentCube == 1 && facing == 1) return Triple(4, 3, Pair(3, 3 - pos.second))
    else if (currentCube == 1 && facing == 2) return Triple(5, 3, Pair(3, 3 - pos.first))
    else if (currentCube == 1 && facing == 3) return Triple(0, 1, Pair(0, 3 - pos.second))

    // R = 0, D = 1, L = 2, U = 3
    else if (currentCube == 2 && facing == 0) return Triple(3, 0, Pair(pos.first, 0))
    else if (currentCube == 2 && facing == 1) return Triple(4, 0, Pair(3 - pos.second, 0))
    else if (currentCube == 2 && facing == 2) return Triple(1, 2, Pair(pos.first, 3))
    else if (currentCube == 2 && facing == 3) return Triple(0, 0, Pair(pos.second, 0))

    // R = 0, D = 1, L = 2, U = 3
    else if (currentCube == 3 && facing == 0) return Triple(5, 1, Pair(0, 3 - pos.first))
    else if (currentCube == 3 && facing == 1) return Triple(4, 1, Pair(0, pos.second))
    else if (currentCube == 3 && facing == 2) return Triple(2, 2, Pair(pos.first, 3))
    else if (currentCube == 3 && facing == 3) return Triple(0, 3, Pair(3, pos.second))

    // R = 0, D = 1, L = 2, U = 3
    else if (currentCube == 4 && facing == 0) return Triple(5, 0, Pair(pos.first, 0))
    else if (currentCube == 4 && facing == 1) return Triple(1, 3, Pair(3, 3 - pos.second))
    else if (currentCube == 4 && facing == 2) return Triple(2, 3, Pair(3, 3 - pos.first))
    else if (currentCube == 4 && facing == 3) return Triple(3, 3, Pair(3, pos.second))

    // R = 0, D = 1, L = 2, U = 3
    else if (currentCube == 5 && facing == 0) return Triple(0, 0, Pair(3 - pos.first, 3))
    else if (currentCube == 5 && facing == 1) return Triple(1, 3, Pair(3, 3 - pos.second))
    else if (currentCube == 5 && facing == 2) return Triple(4, 2, Pair(pos.first, 3))
    else if (currentCube == 5 && facing == 3) return Triple(3, 2, Pair(3 - pos.second, 3))
    else throw Exception("Unknown wrap!")
}

fun second(
    filename: String,
    cubeSize: Int,
    wrapFor: (Int, Int, Pair<Int, Int>) -> Triple<Int, Int, Pair<Int, Int>>
) {
    val (rawGrid, moves) = getInput(filename)

    val grid: MutableMap<Pair<Int, Int>, Char> = mutableMapOf<Pair<Int, Int>, Char>()

    for (i in 0..rawGrid.size - 1) {
        for (j in 0..rawGrid[i].length - 1) {
            if (rawGrid[i][j] == '.') grid.put(Pair(i, j), '.')
            else if (rawGrid[i][j] == '#') grid.put(Pair(i, j), '#')
        }
    }

    val cubes: MutableList<MutableMap<Pair<Int, Int>, Char>> = mutableListOf<MutableMap<Pair<Int, Int>, Char>>()
    val cubeStart: MutableMap<Int, Pair<Int, Int>> = mutableMapOf<Int, Pair<Int, Int>>()
    var cubeId: Int = 0

    for (i in 0..3) {
        for (j in 0..3) {
            if (Pair(i * cubeSize, j * cubeSize) !in grid) continue
            val currCube: MutableMap<Pair<Int, Int>, Char> = mutableMapOf<Pair<Int, Int>, Char>()

            for (k in 0..cubeSize - 1) {
                for (l in 0..cubeSize - 1) {
                    currCube.put(Pair(k, l), grid.getValue(Pair(i * cubeSize + k, j * cubeSize + l)))
                }
            }

            cubes.add(currCube)
            cubeStart.put(cubeId, Pair(i * cubeSize, j * cubeSize))

            cubeId += 1
        }
    }

    var facing: Int = 0
    var currentCube: Int = 0
    var pos: Pair<Int, Int> = Pair(0, 0)

    for ((turn, step) in moves) {
        if (turn == 'R') facing = (facing + 1) % 4
        else if (turn == 'L') facing = (facing + 3) % 4

        for (st in 1..step) {
            var next = Pair(pos.first + dir[facing].first, pos.second + dir[facing].second)

            if (next.first < 0 || next.first >= cubeSize || next.second < 0 || next.second >= cubeSize) {
                // find the next cube and orientation
                val (nextCube, nextFacing, positionInNextCube) = wrapFor(currentCube, facing, pos)

                if (cubes[nextCube].getValue(positionInNextCube) == '.') {
                    facing = nextFacing
                    currentCube = nextCube
                    pos = positionInNextCube
                } else if (cubes[nextCube].getValue(positionInNextCube) == '#') {
                    // we hit the block
                    break
                } else {
                    throw Exception("Something went wrong!")
                }

                next = positionInNextCube
                facing = nextFacing
            } else if (cubes[currentCube].getValue(next) == '.') {
                pos = next
            } else if (cubes[currentCube].getValue(next) == '#') {
                // we hit the block
                break
            } else {
                throw Exception("Something went wrong!")
            }
        }
    }

    val actualCubeStart = cubeStart.getValue(currentCube)
    val actualPos = Pair(actualCubeStart.first + pos.first, actualCubeStart.second + pos.second)
    println(1000 * (actualPos.first + 1) + 4 * (actualPos.second + 1) + facing)
}

second("test.in", 4, ::findNextCubeForSmall) // 5031
second("first.in", 50, ::findNextCubeFor)
