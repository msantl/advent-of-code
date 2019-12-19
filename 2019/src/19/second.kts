import java.io.File
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

fun runOnce(commands: List<Int>, input: List<Int>): Int {
    var inputPointer: Int = 0
    var memory: MutableMap<Int, Int> = mutableMapOf()
    var ip: Int = 0
    var relativeBase: Int = 0
    var halt: Boolean = false

    for (cmd in commands.withIndex()) {
        memory.put(cmd.index, cmd.value)
    }

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
                memory.put(addr, input.get(inputPointer))
                inputPointer += 1
            }
            4 -> {
                var res: Int = memory.getOrDefault(ip, 0)
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

fun main() {
    val commands: List<Int> = File("second.in")
        .readText()
        .trim()
        .split(",")
        .map { it.toInt() }
        .toList()

    val N: Int = 3000
    val map: MutableSet<Pair<Int, Int>> = mutableSetOf()


    var firstX: Int = 0

    for (y in 0 until N) {
        var foundBeam: Boolean = false
        for (x in firstX until N) {
            if (runOnce(commands, listOf(x, y)) == 1) {
                map.add(x to y)

                if (!foundBeam) {
                    firstX = x
                }

                foundBeam = true
            } else {
                if (foundBeam) {
                    break
                }
            }
        }
    }

    println("initialized")

    for (point in map) {
        val (x, y) = point
        var fits: Boolean = true
        for (dx in 0 until 100) {
            for (dy in 0 until 100) {
                if (!map.contains((x + dx) to (y + dy))) {
                    fits = false
                    break
                }
            }
            if (!fits) break
        }

        if (fits) {
            println(x to y)
            println(10000 * x + y)
            return
        }
    }
}

main()