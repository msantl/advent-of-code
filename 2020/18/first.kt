import java.io.File

fun evaluate(expr: String): Long {
    var result = 0L
    var buffer = 0L

    var operationToApply = '+'
    val stack = mutableListOf<Pair<Long, Char>>()

    for (c in expr) {
        when (c) {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                buffer = buffer * 10 + Character.getNumericValue(c)
            }
            '+', '*' -> {
                if (operationToApply == '+') {
                    result += buffer
                } else {
                    result *= buffer
                }
                operationToApply = c
                buffer = 0L
            }
            '(' -> {
                assert(buffer == 0L)
                stack.add(Pair(result, operationToApply))
                operationToApply = '+'
                result = 0L
                buffer = 0L
            }
            ')' -> {
                if (operationToApply == '+') {
                    result += buffer
                } else {
                    result *= buffer
                }
                val (prevResult, prevOperationToApply) = stack.removeLast()
                operationToApply = prevOperationToApply
                buffer = prevResult
            }
            ' ' -> {
                // do nothing
            }
            else -> {
                throw Exception("Unexpected character $c in the expresion $expr")
            }
        }
    }

    if (operationToApply == '+') {
        result += buffer
    } else {
        result *= buffer
    }

    return result
}

fun main(filename: String) {
    val solution = File(filename).readLines().map { evaluate(it) }
    println(solution.sum())
}

main("first.in")
