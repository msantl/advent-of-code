import java.io.File

fun main() {
    val input = File("second.in").readText()
    val list: List<Int> = input.split(",").map { it.trim() }.map { it.toInt() }

    for (noun in 0..99) {
        for (verb in 0..99) {
            if (runProgram(list.toMutableList(), noun, verb) == 19690720) {
                print(100 * noun + verb)
            }
        }
    }
}

fun runProgram(list: MutableList<Int>, noun: Int, verb: Int): Int {
    list[1] = noun
    list[2] = verb

    var ip: Int = 0
    while (true) {
        var cmd: Int = list.get(ip++)

        if (cmd == 1 || cmd == 2) {
            var id1: Int = list.get(ip++)
            var id2: Int = list.get(ip++)
            var id3: Int = list.get(ip++)

            if (cmd == 1) {
                list[id3] = list.get(id1) + list.get(id2)
            } else if (cmd == 2) {
                list[id3] = list.get(id1) * list.get(id2)
            } else {
                print("Something went wrong!")
                break
            }
        } else if (cmd == 99) {
            break
        }
    }

    return list[0]
}

main()
