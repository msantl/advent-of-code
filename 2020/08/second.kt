import java.io.File

fun execute(input: Map<Int, String>): Int {
    val executed = mutableSetOf<Int>()

    var ip = 0
    var acc = 0

    while (ip < input.keys.size) {
        if (executed.contains(ip)) {
            throw Exception("Infinite loop detected")
        }
        executed.add(ip)

        val (command, argRaw) = input.get(ip)!!.split(" ")

        when (command) {
            "acc" -> {
                acc += argRaw.toInt()
                ip += 1
            }
            "jmp" -> {
                ip += argRaw.toInt()
            }
            "nop" -> {
                ip += 1
            }
            else -> {
                throw Exception("Unknown command!")
            }
        }
    }
    return acc
}

fun main(filename: String) {
    val mutableInput = mutableMapOf<Int, String>()
    File(filename).readLines().withIndex().forEach { (f, s) ->
        mutableInput.put(f, s)
    }

    var sol = 0
    for ((k, v) in mutableInput) {
        if (v.take(3) == "jmp") {
            val prev = mutableInput[k]!!
            try {
                mutableInput[k] = "nop" + prev.takeLast(prev.length - 3)
                sol = execute(mutableInput.toMap())
                break
            } catch (e: Exception) {
                mutableInput[k] = prev
            }
        } else if (v.take(3) == "nop") {
            val prev = mutableInput[k]!!
            try {
                mutableInput[k] = "jmp" + prev.takeLast(prev.length - 3)
                sol = execute(mutableInput.toMap())
                break
            } catch (e: Exception) {
                mutableInput[k] = prev
            }
        }
    }
    println(sol)
}

main("second.in")
