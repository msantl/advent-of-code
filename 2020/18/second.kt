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

fun transform(expr: String): String {
    var result = ""
    var buffer = ""
    var operationToApply: Char? = null
    var stack = mutableListOf<String>()

    val opStack = mutableListOf<Char?>()
    val pStack = mutableListOf<MutableList<String>>()

    for (c in expr) {
        when (c) {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                buffer += c
            }
            '+', '*' -> {
                if (operationToApply == '+') {
                    val prevOperand = stack.removeLast()
                    stack.add("($prevOperand + $buffer)")
                } else if (operationToApply == '*') {
                    val prevOperand = stack.removeLast()
                    stack.add("$prevOperand * ")
                    stack.add(buffer)
                } else {
                    stack.add(buffer)
                }
                operationToApply = c
                buffer = ""
            }
            '(' -> {
                opStack.add(operationToApply)
                pStack.add(stack)
                operationToApply = null
                stack = mutableListOf<String>()
            }
            ')' -> {
                if (operationToApply == '+') {
                    val prevOperand = stack.removeLast()
                    stack.add("($prevOperand + $buffer)")
                } else if (operationToApply == '*') {
                    val prevOperand = stack.removeLast()
                    stack.add("$prevOperand * ")
                    stack.add(buffer)
                } else {
                    stack.add(buffer)
                }

                buffer = ""
                operationToApply = opStack.removeLast()

                while (!stack.isEmpty()) {
                    buffer = stack.removeLast() + buffer
                }
                buffer = "($buffer)"
                stack = pStack.removeLast()
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
        val prevOperand = stack.removeLast()
        stack.add("($prevOperand + $buffer)")
    } else if (operationToApply == '*') {
        val prevOperand = stack.removeLast()
        stack.add("$prevOperand * ")
        stack.add(buffer)
    } else {
        stack.add(buffer)
    }

    while (!stack.isEmpty()) {
        result = stack.removeLast() + result
    }

    return result
}

fun main(filename: String) {
    val solution = File(filename).readLines().map { transform(it) }
    val sum = solution.map { evaluate(it) }
    println(sum.sum())
}

main("second.in")
