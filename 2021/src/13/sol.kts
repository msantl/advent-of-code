package `13`

import java.io.File

data class Input(val dots: Set<Pair<Int, Int>>, val folds: List<Pair<Int, Int>>)

fun readInput(filename: String): Input {
    val input = File(filename).readLines()
    var readingFolds = false
    val dots = mutableSetOf<Pair<Int, Int>>()
    val folds = mutableListOf<Pair<Int, Int>>()

    for (i in input.indices) {
        if (input[i].isEmpty()) {
            readingFolds = true
        } else if (readingFolds) {
            val (type, value, _) = input[i].split(' ').last().split('=')
            if (type == "x") {
                folds.add(Pair(0, value.toInt()))
            } else {
                folds.add(Pair(1, value.toInt()))
            }
        } else {
            val (x, y, _) = input[i].split(',')
            dots.add(Pair(x.toInt(), y.toInt()))
        }
    }

    return Input(dots, folds)
}

fun first(filename: String) {
    val input = readInput(filename)

    val fold = input.folds.first()

    val newDots = mutableSetOf<Pair<Int, Int>>()
    if (fold.first == 1) {
        val y = fold.second
        for (dot in input.dots) {
            if (dot.second > y) {
                newDots.add(Pair(dot.first, 2 * y - dot.second))
            } else {
                newDots.add(dot)
            }
        }
    } else {
        val x = fold.second
        for (dot in input.dots) {
            if (dot.first > x) {
                newDots.add(Pair(2 * x - dot.first, dot.second))
            } else {
                newDots.add(dot)
            }
        }
    }
    println(newDots.size)
}

first("test.in") // 17
first("input.in") // 837

fun second(filename: String) {
    val input = readInput(filename)
    var dots = input.dots

    for (fold in input.folds) {
        val newDots = mutableSetOf<Pair<Int, Int>>()

        if (fold.first == 1) {
            val y = fold.second
            for (dot in dots) {
                if (dot.second > y) {
                    newDots.add(Pair(dot.first, 2 * y - dot.second))
                } else {
                    newDots.add(dot)
                }
            }
        } else {
            val x = fold.second
            for (dot in dots) {
                if (dot.first > x) {
                    newDots.add(Pair(2 * x - dot.first, dot.second))
                } else {
                    newDots.add(dot)
                }
            }
        }

        dots = newDots
    }

    val rows = dots.maxOf { it.second }
    val cols = dots.maxOf { it.first }

    for (r in 0..rows) {
        for (c in 0..cols) {
            if (Pair(c, r) in dots) {
                print("X")
            } else {
                print(" ")
            }
        }
        println("")
    }
    println("")
}

second("test.in") // 0
second("input.in") // EPZGKCHU
