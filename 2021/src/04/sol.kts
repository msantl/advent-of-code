package `04`

import java.io.File
import kotlin.math.max

data class Sheet(val rows: Int, val cols: Int, val numbers: List<Int>) {
    fun getSumOfUnmarked(numbersToCheck: List<Int>) =
        numbers.filter { !numbersToCheck.contains(it) }.sum()

    fun isWinningFor(numbersToCheck: List<Int>): Boolean {
        return checkRows(numbersToCheck) || checkCols(numbersToCheck)
    }

    private fun checkRows(numbersToCheck: List<Int>): Boolean {
        for (i in 0 until rows) {
            var isWinning = true
            for (j in 0 until cols) {
                if (!numbersToCheck.contains(numbers[i * rows + j])) {
                    isWinning = false
                    break
                }
            }
            if (isWinning) {
                return true
            }
        }
        return false
    }

    private fun checkCols(numbersToCheck: List<Int>): Boolean {
        for (i in 0 until cols) {
            var isWinning = true
            for (j in 0 until rows) {
                if (!numbersToCheck.contains(numbers[j * rows + i])) {
                    isWinning = false
                    break
                }
            }
            if (isWinning) {
                return true
            }
        }
        return false
    }
}

fun readInput(filename: String): Pair<List<Int>, List<Sheet>> {
    val input = File(filename).readLines()

    val numbers = input[0].split(',').map { it.toInt() }
    val sheets = mutableListOf<Sheet>()

    var rowCnt = 0
    var colCnt = 0
    var sheetNumbers = mutableListOf<Int>()

    for (i in 2..input.size) {
        if (i == input.size || input[i].isEmpty()) {
            sheets.add(Sheet(rowCnt, colCnt, sheetNumbers))
            rowCnt = 0
            colCnt = 0
            sheetNumbers = mutableListOf()
        } else {
            val numbersInRow = input[i].split(' ').filter { it.isNotEmpty() }
                .map { it.toInt() }
            rowCnt += 1
            colCnt = max(colCnt, numbersInRow.size)
            sheetNumbers.addAll(numbersInRow)
        }
    }
    return Pair(numbers, sheets)
}

fun first(filename: String) {
    val (numbers, sheets) = readInput(filename)

    val numbersToCheck = mutableListOf<Int>()
    for (number in numbers) {
        numbersToCheck.add(number)

        for (sheet in sheets) {
            if (sheet.isWinningFor(numbersToCheck)) {
                println(number * sheet.getSumOfUnmarked(numbersToCheck))
                return
            }
        }
    }
    println("Something went wrong!")
}

first("test.in") // 4512
first("input.in")

fun second(filename: String) {
    val (numbers, sheets) = readInput(filename)
    val scores = mutableListOf<Int>()
    val winningSheets = mutableSetOf<Int>()

    val numbersToCheck = mutableListOf<Int>()
    for (number in numbers) {
        numbersToCheck.add(number)

        for ((id, sheet) in sheets.withIndex()) {
            if (winningSheets.contains(id)) continue
            if (sheet.isWinningFor(numbersToCheck)) {
                val sheetScore = number * sheet.getSumOfUnmarked(numbersToCheck)
                scores.add(sheetScore)
                winningSheets.add(id)
            }
        }
    }

    println(scores.last())
}

second("test.in") // 1924
second("input.in")