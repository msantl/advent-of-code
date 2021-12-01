import java.io.File

fun main(filename: String) {
    val input = File(filename).readLines().withIndex().map { (f, s) -> f to s }.toMap()
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
    println(acc)
}

main("test.in")
