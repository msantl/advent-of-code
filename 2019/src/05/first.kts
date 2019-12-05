import java.io.File

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

fun add(instructionPointer: Int, memory: MutableMap<Int, Int>, modes: List<Int>): Int {
    var ip: Int = instructionPointer
    var val1: Int = memory.getValue(ip++)
    var val2: Int = memory.getValue(ip++)
    val addr: Int = memory.getValue(ip++)

    if (modes.get(1) == 0) val1 = memory.getValue(val1)
    if (modes.get(2) == 0) val2 = memory.getValue(val2)

    val result = val1 + val2
    memory.put(addr, result)

    return ip
}

fun mul(instructionPointer: Int, memory: MutableMap<Int, Int>, modes: List<Int>): Int {
    var ip: Int = instructionPointer
    var val1: Int = memory.getValue(ip++)
    var val2: Int = memory.getValue(ip++)
    val addr: Int = memory.getValue(ip++)

    if (modes.get(1) == 0) val1 = memory.getValue(val1)
    if (modes.get(2) == 0) val2 = memory.getValue(val2)

    val result = val1 * val2
    memory.put(addr, result)

    return ip
}

fun main() {
    var commands: List<Int> = File("first.in")
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
            1 -> ip = add(ip, memory, modes)
            2 -> ip = mul(ip, memory, modes)
            3 -> {
                memory.put(memory.getValue(ip++), 1)
                println("Asked for input, provided 1")
            }
            4 -> {
                var res: Int = memory.getValue(ip++)
                if (modes.get(1) == 0) res = memory.getValue(res)

                if (res != 0) {
                    print("output: ")
                    println(res)
                }
            }
            99 -> halt = true
        }
    }
}

main()