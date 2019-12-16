import java.io.File
import java.util.stream.Collectors

fun main() {
    var inputList = File("second.in").readText().trim().map { it.toInt() - '0'.toInt() }
    val offset: Int = inputList.subList(0, 7).joinToString("").toInt()

    var input: MutableList<Int> = mutableListOf()
    for (m in 0 until 10000) {
        for (i in 0 until inputList.size) {
            input.add(inputList.get(i))
        }
    }

    if (2 * offset >= input.size) {

        for (i in 0..99) {
            for (k in input.size - 2 downTo 0) {
                input[k] += input[k + 1]
                input[k] %= 10
            }
        }

        println(input.joinToString("").substring(offset, offset + 8))
    } else {
        println("Meh.,")
    }
}

main()