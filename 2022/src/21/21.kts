package `2022_21`

import java.io.File

sealed class Operation
class SumOperation(val lhs: String, val rhs: String) : Operation()
class SubOperation(val lhs: String, val rhs: String) : Operation()
class MulOperation(val lhs: String, val rhs: String) : Operation()
class DivOperation(val lhs: String, val rhs: String) : Operation()
class EqOperation(val lhs: String, val rhs: String) : Operation()
class NumOperation(val num: Long) : Operation()

sealed class Op
class SumOp(val lhs: Long?, val rhs: Long?) : Op()
class SubOp(val lhs: Long?, val rhs: Long?) : Op()
class MulOp(val lhs: Long?, val rhs: Long?) : Op()
class DivOp(val lhs: Long?, val rhs: Long?) : Op()


fun buildGraph(filename: String): MutableMap<String, Operation> {
    val graph: MutableMap<String, Operation> = mutableMapOf<String, Operation>()

    val input: List<String> = File(filename).readLines()
    for (line in input) {
        val parts = line.split(": ")
        val monkeyFrom = parts[0]

        val operation =
            try {
                NumOperation(parts[1].toLong())
            } catch (ex: NumberFormatException) {
                val operationSplit = parts[1].split(" ")
                when (operationSplit[1]) {
                    "+" -> SumOperation(operationSplit[0], operationSplit[2])
                    "-" -> SubOperation(operationSplit[0], operationSplit[2])
                    "*" -> MulOperation(operationSplit[0], operationSplit[2])
                    "/" -> DivOperation(operationSplit[0], operationSplit[2])
                    else -> throw Exception("Unexpected operation $line")
                }
            }

        graph.put(monkeyFrom, operation)
    }
    return graph
}


fun solve(graph: Map<String, Operation>, curr: String): Long {
    val op = graph.getValue(curr)

    val sol =
        if (op is NumOperation) {
            val numOp = op as NumOperation
            return numOp.num
        } else if (op is SumOperation) {
            val sumOp = op as SumOperation
            solve(graph, sumOp.lhs) + solve(graph, sumOp.rhs)
        } else if (op is SubOperation) {
            val subOp = op as SubOperation
            solve(graph, subOp.lhs) - solve(graph, subOp.rhs)
        } else if (op is MulOperation) {
            val mulOp = op as MulOperation
            solve(graph, mulOp.lhs) * solve(graph, mulOp.rhs)
        } else if (op is DivOperation) {
            val divOp = op as DivOperation
            solve(graph, divOp.lhs) / solve(graph, divOp.rhs)
        } else {
            throw Exception("Unknown operation $op")
        }

    return sol
}


fun first(filename: String) {
    val graph = buildGraph(filename)

    println(solve(graph, "root"))
}

first("test.in") // 152
first("first.in")

val opList: MutableList<Op> = mutableListOf<Op>()

fun trySolve(graph: Map<String, Operation>, curr: String): Long? {
    if (curr == "humn") {
        return null
    }
    val op = graph.getValue(curr)

    val sol =
        if (op is NumOperation) {
            val numOp = op as NumOperation
            numOp.num
        } else if (op is EqOperation) {
            val eqOp = op as EqOperation
            val lhs = trySolve(graph, eqOp.lhs)
            val rhs = trySolve(graph, eqOp.rhs)

            if (lhs != null) lhs
            else if (rhs != null) rhs
            else null

        } else if (op is SumOperation) {
            val sumOp = op as SumOperation
            val lhs = trySolve(graph, sumOp.lhs)
            val rhs = trySolve(graph, sumOp.rhs)

            if (lhs == null || rhs == null) {
                opList.add(0, SumOp(lhs, rhs))
                null
            } else lhs + rhs

        } else if (op is SubOperation) {
            val subOp = op as SubOperation
            val lhs = trySolve(graph, subOp.lhs)
            val rhs = trySolve(graph, subOp.rhs)

            if (lhs == null || rhs == null) {
                opList.add(0, SubOp(lhs, rhs))
                null
            } else lhs - rhs

        } else if (op is MulOperation) {
            val mulOp = op as MulOperation
            val lhs = trySolve(graph, mulOp.lhs)
            val rhs = trySolve(graph, mulOp.rhs)

            if (lhs == null || rhs == null) {
                opList.add(0, MulOp(lhs, rhs))
                null
            } else lhs * rhs

        } else if (op is DivOperation) {
            val divOp = op as DivOperation
            val lhs = trySolve(graph, divOp.lhs)
            val rhs = trySolve(graph, divOp.rhs)

            if (lhs == null || rhs == null) {
                opList.add(0, DivOp(lhs, rhs))
                null
            } else lhs / rhs
        } else {
            null
        }

    return sol
}

fun second(filename: String) {
    val input: List<String> = File(filename).readLines()
    val graph = buildGraph(filename)

    // replace for root
    val rootOp = graph.getValue("root")
    if (rootOp is DivOperation) {
        val op = rootOp as DivOperation
        graph.put("root", EqOperation(op.lhs, op.rhs))
    } else if (rootOp is MulOperation) {
        val op = rootOp as MulOperation
        graph.put("root", EqOperation(op.lhs, op.rhs))
    } else if (rootOp is SumOperation) {
        val op = rootOp as SumOperation
        graph.put("root", EqOperation(op.lhs, op.rhs))
    } else if (rootOp is SubOperation) {
        val op = rootOp as SubOperation
        graph.put("root", EqOperation(op.lhs, op.rhs))
    } else throw Exception("Invalid root op $rootOp")


    opList.clear()
    var result: Long = trySolve(graph, "root")!!

    for (op in opList) {
        if (op is SumOp) {
            val sumOp = op as SumOp
            // result = lhs + rhs
            if (sumOp.lhs == null && sumOp.rhs != null) {
                result = result - sumOp.rhs
            } else if (sumOp.lhs != null && sumOp.rhs == null) {
                result = result - sumOp.lhs
            } else {
                throw Exception("Invalid Op $sumOp")
            }
        } else if (op is SubOp) {
            val subOp = op as SubOp
            // result = lhs - rhs
            if (subOp.lhs == null && subOp.rhs != null) {
                result = result + subOp.rhs
            } else if (subOp.lhs != null && subOp.rhs == null) {
                result = subOp.lhs - result
            } else {
                throw Exception("Invalid Op $subOp")
            }

        } else if (op is MulOp) {
            val mulOp = op as MulOp
            // result = lhs * rhs
            if (mulOp.lhs == null && mulOp.rhs != null) {
                result = result / mulOp.rhs
            } else if (mulOp.lhs != null && mulOp.rhs == null) {
                result = result / mulOp.lhs
            } else {
                throw Exception("Invalid Op $mulOp")
            }

        } else if (op is DivOp) {
            val divOp = op as DivOp
            // result = lhs / rhs
            if (divOp.lhs == null && divOp.rhs != null) {
                result = result * divOp.rhs
            } else if (divOp.lhs != null && divOp.rhs == null) {
                result = divOp.lhs / result
            } else {
                throw Exception("Invalid Op $divOp")
            }
        }
    }

    println(result)
}

second("test.in") // 301
second("first.in")
