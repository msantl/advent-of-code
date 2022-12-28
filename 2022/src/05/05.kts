package `2022_05`

import java.io.File

fun first(filename: String) {
    val input = File(filename).readLines()
    val stacks = mutableMapOf<Int, MutableList<Char>>()

    for (i in 0..10) {
        stacks.put(i, mutableListOf())
    }

    var inputModeCommands = false

    for (line in input) {
        var foundSetupData = false
        if (!inputModeCommands) {
            for (i in 1..line.length - 2) {
                if (line[i - 1] == '[' && line[i + 1] == ']') {
                    val group = i / 4 + 1;
                    stacks[group]!!.add(line[i])
                    foundSetupData = true
                }
            }
        }

        if (inputModeCommands) {
            // Read commands and perform actions
            val parts = line.split(" ")
            if (parts[0] != "move") continue

            val count = parts[1].toInt()
            val from = parts[3].toInt()
            val to = parts[5].toInt()

            val takeFrom = stacks[from]!!

            stacks[to] = (takeFrom.take(count).reversed() + stacks[to]!!).toMutableList()
            stacks[from] = (takeFrom.takeLast(takeFrom.size - count)).toMutableList()

        } else if (!foundSetupData) {
            inputModeCommands = true
            continue
        }
    }

    val sol = stacks.values.filter { it.isNotEmpty() }.map { it.first() }.joinToString("")
    println(sol)
}

first("test.in")
first("first.in")


fun second(filename: String) {
    val input = File(filename).readLines()
    val stacks = mutableMapOf<Int, MutableList<Char>>()

    for (i in 0..10) {
        stacks.put(i, mutableListOf())
    }

    var inputModeCommands = false

    for (line in input) {
        var foundSetupData = false
        if (!inputModeCommands) {
            for (i in 1..line.length - 2) {
                if (line[i - 1] == '[' && line[i + 1] == ']') {
                    val group = i / 4 + 1;
                    stacks[group]!!.add(line[i])
                    foundSetupData = true
                }
            }
        }

        if (inputModeCommands) {
            // Read commands and perform actions
            val parts = line.split(" ")
            if (parts[0] != "move") continue

            val count = parts[1].toInt()
            val from = parts[3].toInt()
            val to = parts[5].toInt()

            val takeFrom = stacks[from]!!

            stacks[to] = (takeFrom.take(count) + stacks[to]!!).toMutableList()
            stacks[from] = (takeFrom.takeLast(takeFrom.size - count)).toMutableList()

        } else if (!foundSetupData) {
            inputModeCommands = true
            continue
        }
    }

    val sol = stacks.values.filter { it.isNotEmpty() }.map { it.first() }.joinToString("")
    println(sol)
}

second("test.in")
second("first.in")
