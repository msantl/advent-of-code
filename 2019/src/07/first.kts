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

fun run(inputs: List<Int>): Int {
    var next: Int = 0
    var result: Int = 0

    val commands: List<Int> = File("first.in")
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

    var ip: Int = 0
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

                result = res
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

    return result
}

fun rek(used: Set<Int>, prev: Int): Int {
    if (used.size == 5) return prev;
    
    var res: Int = 0;

    for (i in 0..4) {
        if (!used.contains(i)) {
            res = max(rek(used.plus(i), run(listOf(i, prev))), res)
        }
    }

    return res;
}

fun main() {
    println(rek(emptySet(), 0))
}

main()