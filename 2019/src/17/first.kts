import java.io.File
import java.util.stream.Collectors
import kotlin.math.max

fun getValueModes(command: Int): List<Int> {
    var cmd: String = command.toString()
    while (cmd.length < 5) cmd = "0" + cmd

    return listOf(
        cmd.substring(3, 5).toInt(),
        cmd.substring(2, 3).toInt(),
        cmd.substring(1, 2).toInt(),
        cmd.substring(0, 1).toInt()
    )
}

var memory: MutableMap<Int, Int> = mutableMapOf()
var ip: Int = 0

var relativeBase: Int = 0
var halt: Boolean = false

fun runOnce(input: Int): Int {
    while (!halt) {
        val command: Int = memory.getOrDefault(ip++, 0)
        val modes: List<Int> = getValueModes(command)

        when (modes.get(0)) {
            1 -> {
                var val1: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 0) val1 = memory.getOrDefault(val1, 0)
                else if (modes.get(1) == 2) val1 = memory.getOrDefault(val1 + relativeBase, 0)

                var val2: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(2) == 0) val2 = memory.getOrDefault(val2, 0)
                else if (modes.get(2) == 2) val2 = memory.getOrDefault(val2 + relativeBase, 0)

                var addr: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(3) == 2) addr = addr + relativeBase

                memory.put(addr, val1 + val2)
            }
            2 -> {
                var val1: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 0) val1 = memory.getOrDefault(val1, 0)
                else if (modes.get(1) == 2) val1 = memory.getOrDefault(val1 + relativeBase, 0)

                var val2: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(2) == 0) val2 = memory.getOrDefault(val2, 0)
                else if (modes.get(2) == 2) val2 = memory.getOrDefault(val2 + relativeBase, 0)

                var addr: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(3) == 2) addr = addr + relativeBase

                memory.put(addr, val1 * val2)
            }
            3 -> {
                var addr: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 2) addr = addr + relativeBase

                // Input
                memory.put(addr, input)
            }
            4 -> {
                var res: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 0) res = memory.getOrDefault(res, 0)
                else if (modes.get(1) == 2) res = memory.getOrDefault(res + relativeBase, 0)

                // Output
                return res
            }
            5 -> {
                var val1: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 0) val1 = memory.getOrDefault(val1, 0)
                else if (modes.get(1) == 2) val1 = memory.getOrDefault(val1 + relativeBase, 0)

                var val2: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(2) == 0) val2 = memory.getOrDefault(val2, 0)
                else if (modes.get(2) == 2) val2 = memory.getOrDefault(val2 + relativeBase, 0)

                if (val1 != 0) ip = val2
            }
            6 -> {
                var val1: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 0) val1 = memory.getOrDefault(val1, 0)
                else if (modes.get(1) == 2) val1 = memory.getOrDefault(val1 + relativeBase, 0)

                var val2: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(2) == 0) val2 = memory.getOrDefault(val2, 0)
                else if (modes.get(2) == 2) val2 = memory.getOrDefault(val2 + relativeBase, 0)

                if (val1 == 0) ip = val2
            }
            7 -> {
                var val1: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 0) val1 = memory.getOrDefault(val1, 0)
                else if (modes.get(1) == 2) val1 = memory.getOrDefault(val1 + relativeBase, 0)

                var val2: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(2) == 0) val2 = memory.getOrDefault(val2, 0)
                else if (modes.get(2) == 2) val2 = memory.getOrDefault(val2 + relativeBase, 0)

                var addr: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(3) == 2) addr = addr + relativeBase

                memory.put(addr, if (val1 < val2) 1 else 0)
            }
            8 -> {
                var val1: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 0) val1 = memory.getOrDefault(val1, 0)
                else if (modes.get(1) == 2) val1 = memory.getOrDefault(val1 + relativeBase, 0)

                var val2: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(2) == 0) val2 = memory.getOrDefault(val2, 0)
                else if (modes.get(2) == 2) val2 = memory.getOrDefault(val2 + relativeBase, 0)

                var addr: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(3) == 2) addr = addr + relativeBase

                memory.put(addr, if (val1 == val2) 1 else 0)
            }
            9 -> {
                var value: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 0) value = memory.getOrDefault(value, 0)
                else if (modes.get(1) == 2) value = memory.getOrDefault(value + relativeBase, 0)

                relativeBase += value
            }
            99 -> halt = true
        }
    }
    return -1
}

val map: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()

fun main() {
    val commands: List<Int> = File("first.in")
        .readText()
        .trim()
        .split(",")
        .map { it.toInt() }
        .toList()


    for (cmd in commands) {
        memory.put(ip++, cmd)
    }
    ip = 0

    var pos_x = 0
    var pos_y = 0

    while (true) {
        val res = runOnce(0)
        if (res == -1) break
        if (res == 10) {
            pos_y += 1
            pos_x = 0
        } else {
            map.put(pos_y to pos_x, res)
            pos_x += 1
        }
    }


    val sol = map.entries.stream()
        .filter { it.value == 35 }
        .filter { it -> map.getOrDefault(it.key.first - 1 to it.key.second, 0) == 35 }
        .filter { it -> map.getOrDefault(it.key.first + 1 to it.key.second, 0) == 35 }
        .filter { it -> map.getOrDefault(it.key.first to it.key.second - 1, 0) == 35 }
        .filter { it -> map.getOrDefault(it.key.first to it.key.second + 1, 0) == 35 }
        .map { it -> it.key.first * it.key.second }
        .collect(Collectors.toList())
        .sum()

    println(sol)
}

main()