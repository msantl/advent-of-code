package `2022_10`

import java.io.File

fun first(filename: String) {
    var score = 0
    val input: List<String> = File(filename).readLines()
    var X = 1

    val cyclesToWatch: List<Int> = listOf(20, 60, 100, 140, 180, 220)

    var cmdPtr = 0
    var cycle = 0

    var sleepCycles = 0
    var buffer: Int? = null

    while (cmdPtr < input.size || buffer != null) {
        cycle += 1

        if (sleepCycles > 0) {
            sleepCycles -= 1
        } else {
            if (buffer != null) {
                X += buffer
                buffer = null
            }

            if (cmdPtr < input.size) {
                val cmd = input[cmdPtr].split(" ")
                if (cmd[0] == "noop") {
                    // do nothing
                } else if (cmd[0] == "addx") {
                    sleepCycles = 1
                    buffer = cmd[1].toInt()
                }
                cmdPtr += 1
            }
        }

        if (cycle in cyclesToWatch) {
            score += X * cycle
        }
    }

    println(score)
}

first("test.in")
first("test1.in") // 13140
first("first.in")


fun second(filename: String) {
    val input: List<String> = File(filename).readLines()
    var X = 1

    var cmdPtr = 0
    var cycle = 0

    var sleepCycles = 0
    var buffer: Int? = null

    while (cycle < 240) {
        cycle += 1

        if (sleepCycles > 0) {
            sleepCycles -= 1
        } else {
            if (buffer != null) {
                X += buffer!!
                buffer = null
            }

            if (cmdPtr < input.size) {
                val cmd = input[cmdPtr].split(" ")
                if (cmd[0] == "noop") {
                    // do nothing
                } else if (cmd[0] == "addx") {
                    sleepCycles = 1
                    buffer = cmd[1].toInt()
                }
                cmdPtr += 1
            }
        }

        var horizontalPosition = (40 + cycle - 1) % 40;
//        println("Drawing pixel $horizontalPosition in cycle $cycle")

        if (X - 1 <= horizontalPosition && horizontalPosition <= X + 1) {
            print("#")
        } else {
            print(".")
        }

        if (cycle % 40 == 0) {
            println()
        }
    }

    println()
    println()
}

second("test1.in")
second("first.in") // EKRHEPUZ
