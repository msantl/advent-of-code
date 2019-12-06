import java.io.File

val dp: HashMap<String, Int> = HashMap()

fun calc(node: String, edges: HashMap<String, List<String>>): Int {
    if (dp.containsKey(node)) {
        return dp.getValue(node)
    }
    var result: Int = 0

    for (adj in edges.getOrDefault(node, emptyList())) {
        result += calc(adj, edges) + 1
    }

    dp.put(node, result)
    return result
}

fun main() {
    val edges: HashMap<String, List<String>> = HashMap()

    File("first.in").forEachLine {
        val (f, s) = it.trim().split(")")

        var curr = edges.getOrDefault(f, emptyList())
        edges.put(f, curr.plus(s))
    }

    var result: Int = 0
    for (node in edges.keys) {
        result += calc(node, edges)
    }

    println(result)
}

main()