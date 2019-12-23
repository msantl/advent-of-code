import java.io.File
import kotlin.math.max

class Machine(commands: List<Long>, address: Long) {
    val memory: MutableMap<Long, Long> = mutableMapOf()
    var ip = 0L
    var relativeBase = 0L
    var halt = false
    val queue: MutableList<Long> = mutableListOf()
    var emptyReads = 0

    init {
        commands.withIndex().forEach { memory.put(it.index.toLong(), it.value) }
        queue.add(address)
    }

    fun addToQueue(x: Long, y: Long) {
        queue.add(x)
        queue.add(y)
    }

    fun isIdle(): Boolean {
        return emptyReads >= 2 && queue.isEmpty()
    }

    fun getValueModes(command: Long): List<Int> {
        var cmd: String = command.toString()
        while (cmd.length < 5) cmd = "0" + cmd

        return listOf(
            cmd.substring(3, 5).toInt(),
            cmd.substring(2, 3).toInt(),
            cmd.substring(1, 2).toInt(),
            cmd.substring(0, 1).toInt()
        )
    }

    fun runOnce(): Long? {
        if (halt) return null

        val command = memory.getOrDefault(ip++, 0)
        val modes: List<Int> = getValueModes(command)

        when (modes.get(0)) {
            1 -> {
                var val1: Long = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 0) val1 = memory.getOrDefault(val1, 0)
                else if (modes.get(1) == 2) val1 = memory.getOrDefault(val1 + relativeBase, 0)

                var val2: Long = memory.getOrDefault(ip++, 0)
                if (modes.get(2) == 0) val2 = memory.getOrDefault(val2, 0)
                else if (modes.get(2) == 2) val2 = memory.getOrDefault(val2 + relativeBase, 0)

                var addr: Long = memory.getOrDefault(ip++, 0)
                if (modes.get(3) == 2) addr = addr + relativeBase

                memory.put(addr, val1 + val2)
            }
            2 -> {
                var val1: Long = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 0) val1 = memory.getOrDefault(val1, 0)
                else if (modes.get(1) == 2) val1 = memory.getOrDefault(val1 + relativeBase, 0)

                var val2: Long = memory.getOrDefault(ip++, 0)
                if (modes.get(2) == 0) val2 = memory.getOrDefault(val2, 0)
                else if (modes.get(2) == 2) val2 = memory.getOrDefault(val2 + relativeBase, 0)

                var addr: Long = memory.getOrDefault(ip++, 0)
                if (modes.get(3) == 2) addr = addr + relativeBase

                memory.put(addr, val1 * val2)
            }
            3 -> {
                var addr: Long = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 2) addr = addr + relativeBase

                // Input
                if (queue.isEmpty()) {
                    emptyReads += 1
                    memory.put(addr, -1)
                } else {
                    emptyReads = 0
                    memory.put(addr, queue.removeAt(0))
                }
            }
            4 -> {
                var res: Long = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 0) res = memory.getOrDefault(res, 0)
                else if (modes.get(1) == 2) res = memory.getOrDefault(res + relativeBase, 0)

                // Output
                return res
            }
            5 -> {
                var val1: Long = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 0) val1 = memory.getOrDefault(val1, 0)
                else if (modes.get(1) == 2) val1 = memory.getOrDefault(val1 + relativeBase, 0)

                var val2: Long = memory.getOrDefault(ip++, 0)
                if (modes.get(2) == 0) val2 = memory.getOrDefault(val2, 0)
                else if (modes.get(2) == 2) val2 = memory.getOrDefault(val2 + relativeBase, 0)

                if (val1 != 0L) ip = val2
            }
            6 -> {
                var val1: Long = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 0) val1 = memory.getOrDefault(val1, 0)
                else if (modes.get(1) == 2) val1 = memory.getOrDefault(val1 + relativeBase, 0)

                var val2: Long = memory.getOrDefault(ip++, 0)
                if (modes.get(2) == 0) val2 = memory.getOrDefault(val2, 0)
                else if (modes.get(2) == 2) val2 = memory.getOrDefault(val2 + relativeBase, 0)

                if (val1 == 0L) ip = val2
            }
            7 -> {
                var val1: Long = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 0) val1 = memory.getOrDefault(val1, 0)
                else if (modes.get(1) == 2) val1 = memory.getOrDefault(val1 + relativeBase, 0)

                var val2: Long = memory.getOrDefault(ip++, 0)
                if (modes.get(2) == 0) val2 = memory.getOrDefault(val2, 0)
                else if (modes.get(2) == 2) val2 = memory.getOrDefault(val2 + relativeBase, 0)

                var addr: Long = memory.getOrDefault(ip++, 0)
                if (modes.get(3) == 2) addr = addr + relativeBase

                memory.put(addr, if (val1 < val2) 1 else 0)
            }
            8 -> {
                var val1: Long = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 0) val1 = memory.getOrDefault(val1, 0)
                else if (modes.get(1) == 2) val1 = memory.getOrDefault(val1 + relativeBase, 0)

                var val2: Long = memory.getOrDefault(ip++, 0)
                if (modes.get(2) == 0) val2 = memory.getOrDefault(val2, 0)
                else if (modes.get(2) == 2) val2 = memory.getOrDefault(val2 + relativeBase, 0)

                var addr: Long = memory.getOrDefault(ip++, 0)
                if (modes.get(3) == 2) addr = addr + relativeBase

                memory.put(addr, if (val1 == val2) 1 else 0)
            }
            9 -> {
                var value: Long = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 0) value = memory.getOrDefault(value, 0)
                else if (modes.get(1) == 2) value = memory.getOrDefault(value + relativeBase, 0)

                relativeBase += value
            }
            99 -> halt = true
        }

        return null
    }
}

fun main() {
    val commands: List<Long> = File("second.in")
        .readText()
        .trim()
        .split(",")
        .map { it.toLong() }
        .toList()

    val machines: MutableList<Machine> = mutableListOf()

    for (i in 0 until 50) {
        machines.add(Machine(commands, i.toLong()))
    }

    val outputBuffer: MutableMap<Int, List<Long>> = mutableMapOf()

    var lastNatY: Long = -1
    var natX: Long = -1
    var natY: Long = -1

    while (true) {
        for (i in 0 until 50) {
            val out = machines[i].runOnce()

            if (out != null) {
                var curr = outputBuffer.getOrDefault(i, emptyList())
                curr = curr.plus(out)
                outputBuffer.put(i, curr)

                if (curr.size == 3) {
                    val (to, x, y) = curr.take(3)
                    outputBuffer.put(i, curr.drop(3))

                    if (to.toInt() == 255) {
                        natX = x
                        natY = y
                    }

                    if (to.toInt() >= 0 && to.toInt() < 50) {
                        machines[to.toInt()].addToQueue(x, y)

                    }
                }
            }
        }

        val isIdle = machines.map { it.isIdle() }.all { it == true }
        if (isIdle) {
            machines[0].addToQueue(natX, natY)

            if (natY == lastNatY) {
                println(natY)
                break
            }

            lastNatY = natY
        }
    }
}

main()