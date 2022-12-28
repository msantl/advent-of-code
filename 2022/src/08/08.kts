package `2022_08`

import java.io.File

fun isVisible(row: Int, col: Int, grid: List<String>): Boolean {
    var isVisible = true
    for (i in 0..row - 1) {
        if (grid[i][col] >= grid[row][col]) {
            isVisible = false
            break
        }
    }
    if (isVisible) return true

    isVisible = true
    for (i in row + 1..grid.size - 1) {
        if (grid[i][col] >= grid[row][col]) {
            isVisible = false
            break
        }
    }
    if (isVisible) return true

    isVisible = true
    for (j in 0..col - 1) {
        if (grid[row][j] >= grid[row][col]) {
            isVisible = false
            break
        }
    }
    if (isVisible) return true

    isVisible = true
    for (j in col + 1..grid[row].length - 1) {
        if (grid[row][j] >= grid[row][col]) {
            isVisible = false
            break
        }
    }

    return isVisible
}

fun first(filename: String) {
    val input: List<String> = File(filename).readLines()

    var count = 0
    for (i in 0..input.size - 1) {
        for (j in 0..input[i].length - 1) {
            if (isVisible(i, j, input)) {
                count += 1
            }
        }
    }

    println(count)
}

first("test.in") // 21
first("first.in")

fun getScenicScore(row: Int, col: Int, grid: List<String>): Int {
    var totalScore = 1
    var score = 0

    score = 0
    for (i in row - 1 downTo 0) {
        score += 1
        if (grid[i][col] >= grid[row][col]) {
            break
        }
    }
    totalScore *= score


    score = 0
    for (i in row + 1..grid.size - 1) {
        score += 1
        if (grid[i][col] >= grid[row][col]) {
            break
        }
    }
    totalScore *= score

    score = 0
    for (j in col - 1 downTo 0) {
        score += 1
        if (grid[row][j] >= grid[row][col]) {
            break
        }
    }
    totalScore *= score

    score = 0
    for (j in col + 1..grid[row].length - 1) {
        score += 1
        if (grid[row][j] >= grid[row][col]) {
            break
        }
    }
    totalScore *= score

    return totalScore
}

fun second(filename: String) {
    val input: List<String> = File(filename).readLines()

    var bestScore = 0
    for (i in 0..input.size - 1) {
        for (j in 0..input[i].length - 1) {
            val score = getScenicScore(i, j, input)
            if (score > bestScore) {
                bestScore = score
            }
        }
    }

    println(bestScore)
}

second("test.in") // 8
second("first.in")
