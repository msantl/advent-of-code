import java.io.File
import kotlin.math.max

data class State(val a: Int, val b: MutableMap<Int, Int>, val c: Int, val d: Boolean)

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

fun run(
    inputs: List<Int>,
    pointer: Int,
    memory: MutableMap<Int, Int>
) : State {
    var next: Int = 0
    var ip: Int = pointer

    var halt: Boolean = false
    while (!halt) {
        if (!memory.containsKey(ip)) {
            println("Pointer out of memory")
            break
        }

        val command: Int = memory.getValue(ip++)
        val modes: List<Int> = getValueModes(command)
        when (modes.get(0)) {
            1 -> {
                var val1: Int = memory.getValue(ip++)
                if (modes.get(1) == 0) val1 = memory.getValue(val1)

                var val2: Int = memory.getValue(ip++)
                if (modes.get(2) == 0) val2 = memory.getValue(val2)

                val addr: Int = memory.getValue(ip++)

                memory.put(addr, val1 + val2)
            }
            2 -> {
                var val1: Int = memory.getValue(ip++)
                if (modes.get(1) == 0) val1 = memory.getValue(val1)

                var val2: Int = memory.getValue(ip++)
                if (modes.get(2) == 0) val2 = memory.getValue(val2)

                val addr: Int = memory.getValue(ip++)

                memory.put(addr, val1 * val2)
            }
            3 -> {
                memory.put(memory.getValue(ip++), inputs.get(next))
                next++
            }
            4 -> {
                var res: Int = memory.getValue(ip++)
                if (modes.get(1) == 0) res = memory.getValue(res)

                return State(ip, memory, res, false)
            }
            5 -> {
                var val1: Int = memory.getValue(ip++)
                if (modes.get(1) == 0) val1 = memory.getValue(val1)

                var val2: Int = memory.getValue(ip++)
                if (modes.get(2) == 0) val2 = memory.getValue(val2)

                if (val1 != 0) ip = val2
            }
            6 -> {
                var val1: Int = memory.getValue(ip++)
                if (modes.get(1) == 0) val1 = memory.getValue(val1)

                var val2: Int = memory.getValue(ip++)
                if (modes.get(2) == 0) val2 = memory.getValue(val2)

                if (val1 == 0) ip = val2
            }
            7 -> {
                var val1: Int = memory.getValue(ip++)
                if (modes.get(1) == 0) val1 = memory.getValue(val1)

                var val2: Int = memory.getValue(ip++)
                if (modes.get(2) == 0) val2 = memory.getValue(val2)

                val addr: Int = memory.getValue(ip++)

                memory.put(addr, if (val1 < val2) 1 else 0)
            }
            8 -> {
                var val1: Int = memory.getValue(ip++)
                if (modes.get(1) == 0) val1 = memory.getValue(val1)

                var val2: Int = memory.getValue(ip++)
                if (modes.get(2) == 0) val2 = memory.getValue(val2)

                val addr: Int = memory.getValue(ip++)

                memory.put(addr, if (val1 == val2) 1 else 0)
            }
            99 -> halt = true
        }
    }

    return State(ip, memory, 0, true)
}

fun generatePermutations(available: Set<Int>, current: List<Int>): List<List<Int>> {
   if (available.size == 0) return listOf(current)

    var result: List<List<Int>> = emptyList()
    for (i in available) {
        result = result.plus(generatePermutations(available.minus(i), current.plus(i)))
    }
    return result
}

fun runSequence(settings: List<Int>): Int {
    val commands: List<Int> = File("second.in")
        .readText()
        .trim()
        .split(",")
        .map { it.toInt() }
        .toList()

    var memory: MutableMap<Int, Int> = generateSequence(0) { it + 1 }
        .take(commands.size)
        .toList()
        .zip(commands)
        .toMap()
        .toMutableMap()

    val ips: MutableMap<Int, Int> = generateSequence(0) { it + 1 }
        .take(5)
        .toList()
        .map { it -> it to 0 }
        .toMap()
        .toMutableMap()

    val memories: MutableMap<Int, MutableMap<Int, Int>> = generateSequence(0) { it + 1 }
        .take(5)
        .toList()
        .map { it -> it to memory }
        .toMap()
        .toMutableMap()

    var curr: Int = 0
    var prev: Int = 0
    var finalResult: Int = 0

    var input: List<Int>
    val initialized: MutableMap<Int, Boolean> = generateSequence(0) { it + 1 }
        .take(5)
        .toList()
        .map { it -> it to false }
        .toMap()
        .toMutableMap()

    while (true) {
        if (!initialized.getValue(curr)) {
            input = listOf(settings.get(curr), prev)
        } else {
            input = listOf(prev)
        }

        val (curr_ip, curr_memory, result, halted) = run(input, ips.getValue(curr), memories.getValue(curr))

        initialized.put(curr, true)

        ips.put(curr, curr_ip)
        memories.put(curr, curr_memory)
        prev = result

        if (curr == 4) {
            if (halted) break
            finalResult = result
        }

        curr = (curr + 1) % 5
    }

    return finalResult
}

fun main() {
    var sol: Int = 0
    val permutations = generatePermutations(setOf(5, 6, 7, 8, 9), emptyList())
    for (setting in permutations) {
        sol = max(runSequence(setting), sol)
    }
    println(sol)
}

main()