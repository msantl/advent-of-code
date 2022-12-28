package `2022_11`

import java.io.File

class Monkey(
    val items: MutableList<Long>,
    val op: (Long) -> Long,
    val test: (Long) -> Boolean,
    val onTrue: Int,
    val onFalse: Int
) {
    override fun toString(): String {
        return "$items => $onTrue, $onFalse"
    }
}

fun first(filename: String) {
    val input: List<String> = File(filename).readLines()
    var i = 0

    val monkeys: MutableMap<Int, Monkey> = mutableMapOf()
    val monkeyIds: MutableList<Int> = mutableListOf()

    while (i < input.size) {
        val parts = input[i].split(" ")
        if (parts[0] == "Monkey") {
            val monkeyId = parts[1].removeSuffix(":").toInt()
            monkeyIds.add(monkeyId)

            val startingItems = input[i + 1].split(": ")[1].split(", ").map { it.toLong() }.toMutableList()

            val operation = input[i + 2].split(" ")
            val operationFunc = if (operation[6] == "*") {
                if (operation[7] == "old") {
                    { number: Long -> number * number }
                } else {
                    { number: Long -> number * operation[7].toLong() }
                }
            } else if (operation[6] == "+") {
                if (operation[7] == "old") {
                    { number: Long -> number + number }
                } else {
                    { number: Long -> number + operation[7].toLong() }
                }
            } else {
                throw Exception("Unknown operatior $operation")
            }

            val test = input[i + 3].split(":")[1].split(" ")
            val testFunc = if (test[1] == "divisible") {
                { number: Long -> (number % test[3].toLong()) == 0L }
            } else {
                throw Exception("Unknown test $test")
            }

            val onTrue = input[i + 4].split(" ").last().toInt()
            val onFalse = input[i + 5].split(" ").last().toInt()

            monkeys.put(monkeyId, Monkey(startingItems, operationFunc, testFunc, onTrue, onFalse))

            i += 6
        } else {
            i += 1
        }
    }


    val inspectionCount: MutableMap<Int, Int> = mutableMapOf()

    for (round in 1..20) {
        for (monkeyId in monkeyIds) {
            val monkey = monkeys.get(monkeyId)!!
            val items = monkey.items

            inspectionCount.put(
                monkeyId,
                inspectionCount.getOrDefault(monkeyId, 0) + items.size
            )

            for (item in items) {
                val newValue = monkey.op(item) / 3
                if (monkey.test(newValue)) {
                    // onTrue
                    monkeys.get(monkey.onTrue)!!.items.add(newValue)
                } else {
                    // onFalse
                    monkeys.get(monkey.onFalse)!!.items.add(newValue)
                }
            }

            monkey.items.clear()
        }
    }

    val sol = inspectionCount.values.sorted().reversed()
    println(sol[0] * sol[1])
}

first("test.in")
first("first.in")


fun second(filename: String) {
    val input: List<String> = File(filename).readLines()
    var globalModulo = 1L

    var i = 0
    while (i < input.size) {
        val parts = input[i].split(" ")
        if (parts[0] == "Monkey") {
            val test = input[i + 3].split(":")[1].split(" ")
            val modulo = test[3].toLong()

            globalModulo *= modulo

            i += 6
        } else {
            i += 1
        }
    }

    val monkeys: MutableMap<Int, Monkey> = mutableMapOf()
    val monkeyIds: MutableList<Int> = mutableListOf()

    i = 0
    while (i < input.size) {
        val parts = input[i].split(" ")
        if (parts[0] == "Monkey") {
            val monkeyId = parts[1].removeSuffix(":").toInt()
            monkeyIds.add(monkeyId)

            val test = input[i + 3].split(":")[1].split(" ")
            val modulo = test[3].toLong()

            val testFunc = if (test[1] == "divisible") {
                { number: Long -> ((number % modulo) == 0L) }
            } else {
                throw Exception("Unknown test $test")
            }

            val startingItems = input[i + 1].split(": ")[1].split(", ").map { it.toLong() }.toMutableList()

            val operation = input[i + 2].split(" ")
            val operationFunc = if (operation[6] == "*") {
                if (operation[7] == "old") {
                    { number: Long -> number * number }
                } else {
                    { number: Long -> number * operation[7].toLong() }
                }
            } else if (operation[6] == "+") {
                if (operation[7] == "old") {
                    { number: Long -> number * number }
                } else {
                    { number: Long -> number + operation[7].toLong()}
                }
            } else {
                throw Exception("Unknown operatior $operation")
            }

            val onTrue = input[i + 4].split(" ").last().toInt()
            val onFalse = input[i + 5].split(" ").last().toInt()

            monkeys.put(monkeyId, Monkey(startingItems, operationFunc, testFunc, onTrue, onFalse))

            i += 6
        } else {
            i += 1
        }
    }


    val inspectionCount: MutableMap<Int, Long> = mutableMapOf()

    for (round in 1..10000) {
        for (monkeyId in monkeyIds) {
            val monkey = monkeys.get(monkeyId)!!

            inspectionCount.put(
                monkeyId,
                inspectionCount.getOrDefault(monkeyId, 0L) + monkey.items.size.toLong()
            )

            for (item in monkey.items) {
                val newValue = monkey.op(item) % globalModulo
                if (monkey.test(newValue)) {
                    // onTrue
                    monkeys.get(monkey.onTrue)!!.items.add(newValue)
                } else {
                    // onFalse
                    monkeys.get(monkey.onFalse)!!.items.add(newValue)
                }
            }

            monkey.items.clear()
        }

    }

    val sol = inspectionCount.values.sorted().reversed()
    println(sol[0] * sol[1])
}

second("test.in") // 2713310158
second("first.in") // 27267163742