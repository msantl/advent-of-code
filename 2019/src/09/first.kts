import java.io.File
import java.math.BigInteger
import kotlin.math.max

fun getValueModes(command: BigInteger): List<Int> {
    var cmd: String = command.toString()
    while (cmd.length < 5) cmd = "0" + cmd

    return listOf(
        cmd.substring(3, 5).toInt(),
        cmd.substring(2, 3).toInt(),
        cmd.substring(1, 2).toInt(),
        cmd.substring(0, 1).toInt()
    )
}

fun main() {
    val commands: List<BigInteger> = File("first.in")
        .readText()
        .trim()
        .split(",")
        .map { it.toBigInteger() }
        .toList()

    var memory: MutableMap<BigInteger, BigInteger> = mutableMapOf()

    var ip: BigInteger = BigInteger.ZERO
    for (cmd in commands) {
        memory.put(ip++, cmd)
    }

    var relativeBase: BigInteger = BigInteger.ZERO
    var halt: Boolean = false

    ip = BigInteger.ZERO
    while (!halt) {
        val command: BigInteger = memory.getOrDefault(ip++, BigInteger.ZERO)
        val modes: List<Int> = getValueModes(command)

        when (modes.get(0)) {
            1 -> {
                var val1: BigInteger = memory.getOrDefault(ip++, BigInteger.ZERO)
                if (modes.get(1) == 0) val1 = memory.getOrDefault(val1, BigInteger.ZERO)
                else if (modes.get(1) == 2) val1 = memory.getOrDefault(val1 + relativeBase, BigInteger.ZERO)

                var val2: BigInteger = memory.getOrDefault(ip++, BigInteger.ZERO)
                if (modes.get(2) == 0) val2 = memory.getOrDefault(val2, BigInteger.ZERO)
                else if (modes.get(2) == 2) val2 = memory.getOrDefault(val2 + relativeBase, BigInteger.ZERO)

                var addr: BigInteger = memory.getOrDefault(ip++, BigInteger.ZERO)
//                if (modes.get(3) == 0) addr = memory.getOrDefault(addr, BigInteger.ZERO)
                if (modes.get(3) == 2) addr = addr + relativeBase

                memory.put(addr, val1 + val2)
            }
            2 -> {
                var val1: BigInteger = memory.getOrDefault(ip++, BigInteger.ZERO)
                if (modes.get(1) == 0) val1 = memory.getOrDefault(val1, BigInteger.ZERO)
                else if (modes.get(1) == 2) val1 = memory.getOrDefault(val1 + relativeBase, BigInteger.ZERO)

                var val2: BigInteger = memory.getOrDefault(ip++, BigInteger.ZERO)
                if (modes.get(2) == 0) val2 = memory.getOrDefault(val2, BigInteger.ZERO)
                else if (modes.get(2) == 2) val2 = memory.getOrDefault(val2 + relativeBase, BigInteger.ZERO)

                var addr: BigInteger = memory.getOrDefault(ip++, BigInteger.ZERO)
//                if (modes.get(3) == 0) addr = memory.getOrDefault(addr, BigInteger.ZERO)
                if (modes.get(3) == 2) addr = addr + relativeBase

                memory.put(addr, val1 * val2)
            }
            3 -> {
                var addr: BigInteger = memory.getOrDefault(ip++, BigInteger.ZERO)
//                if (modes.get(1) == 0) addr = memory.getOrDefault(addr, BigInteger.ZERO)
                if (modes.get(1) == 2) addr = addr + relativeBase

                memory.put(addr, BigInteger.ONE)
            }
            4 -> {
                var res: BigInteger = memory.getOrDefault(ip++, BigInteger.ZERO)
                if (modes.get(1) == 0) res = memory.getOrDefault(res, BigInteger.ZERO)
                else if (modes.get(1) == 2) res = memory.getOrDefault(res + relativeBase, BigInteger.ZERO)

                println(res)
            }
            5 -> {
                var val1: BigInteger = memory.getOrDefault(ip++, BigInteger.ZERO)
                if (modes.get(1) == 0) val1 = memory.getOrDefault(val1, BigInteger.ZERO)
                else if (modes.get(1) == 2) val1 = memory.getOrDefault(val1 + relativeBase, BigInteger.ZERO)

                var val2: BigInteger = memory.getOrDefault(ip++, BigInteger.ZERO)
                if (modes.get(2) == 0) val2 = memory.getOrDefault(val2, BigInteger.ZERO)
                else if (modes.get(2) == 2) val2 = memory.getOrDefault(val2 + relativeBase, BigInteger.ZERO)

                if (val1 != BigInteger.ZERO) ip = val2
            }
            6 -> {
                var val1: BigInteger = memory.getOrDefault(ip++, BigInteger.ZERO)
                if (modes.get(1) == 0) val1 = memory.getOrDefault(val1, BigInteger.ZERO)
                else if (modes.get(1) == 2) val1 = memory.getOrDefault(val1 + relativeBase, BigInteger.ZERO)

                var val2: BigInteger = memory.getOrDefault(ip++, BigInteger.ZERO)
                if (modes.get(2) == 0) val2 = memory.getOrDefault(val2, BigInteger.ZERO)
                else if (modes.get(2) == 2) val2 = memory.getOrDefault(val2 + relativeBase, BigInteger.ZERO)

                if (val1 == BigInteger.ZERO) ip = val2
            }
            7 -> {
                var val1: BigInteger = memory.getOrDefault(ip++, BigInteger.ZERO)
                if (modes.get(1) == 0) val1 = memory.getOrDefault(val1, BigInteger.ZERO)
                else if (modes.get(1) == 2) val1 = memory.getOrDefault(val1 + relativeBase, BigInteger.ZERO)

                var val2: BigInteger = memory.getOrDefault(ip++, BigInteger.ZERO)
                if (modes.get(2) == 0) val2 = memory.getOrDefault(val2, BigInteger.ZERO)
                else if (modes.get(2) == 2) val2 = memory.getOrDefault(val2 + relativeBase, BigInteger.ZERO)

                var addr: BigInteger = memory.getOrDefault(ip++, BigInteger.ZERO)
//                if (modes.get(3) == 0) addr = memory.getOrDefault(addr, BigInteger.ZERO)
                if (modes.get(3) == 2) addr = addr + relativeBase

                memory.put(addr, if (val1 < val2) BigInteger.ONE else BigInteger.ZERO)
            }
            8 -> {
                var val1: BigInteger = memory.getOrDefault(ip++, BigInteger.ZERO)
                if (modes.get(1) == 0) val1 = memory.getOrDefault(val1, BigInteger.ZERO)
                else if (modes.get(1) == 2) val1 = memory.getOrDefault(val1 + relativeBase, BigInteger.ZERO)

                var val2: BigInteger = memory.getOrDefault(ip++, BigInteger.ZERO)
                if (modes.get(2) == 0) val2 = memory.getOrDefault(val2, BigInteger.ZERO)
                else if (modes.get(2) == 2) val2 = memory.getOrDefault(val2 + relativeBase, BigInteger.ZERO)

                var addr: BigInteger = memory.getOrDefault(ip++, BigInteger.ZERO)
//                if (modes.get(3) == 0) addr = memory.getOrDefault(addr, BigInteger.ZERO)
                if (modes.get(3) == 2) addr = addr + relativeBase

                memory.put(addr, if (val1 == val2) BigInteger.ONE else BigInteger.ZERO)
            }
            9 -> {
                var value: BigInteger = memory.getOrDefault(ip++, BigInteger.ZERO)
                if (modes.get(1) == 0) value = memory.getOrDefault(value, BigInteger.ZERO)
                else if (modes.get(1) == 2) value = memory.getOrDefault(value + relativeBase, BigInteger.ZERO)

                relativeBase += value
            }
            99 -> halt = true
        }
    }
}

main()