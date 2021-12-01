import java.io.File

fun dfs(curr: String, nodes: MutableMap<String, MutableList<String>>, visited: Set<String>): Set<String> {
    var res = setOf(curr)
    nodes.getOrDefault(curr, mutableListOf()).forEach {
            if (!visited.contains(it)) {
                var s = dfs(it, nodes, visited.plus(it))
                res = res.plus(s)
            }
        }
    return res
}

fun main(filename: String) {
    val input = File(filename).readLines()
    val nodes = mutableMapOf<String, MutableList<String>>()

    input.forEach {
        val color = it.split(" ").take(2).joinToString(separator = " ") { it }
        it.split("contain").last().split(",").filter { it != " no other bags." }.forEach {
            val c = it.split(" ").subList(2, 4).joinToString(separator = " ") { it }
            if (nodes.containsKey(c)) {
                nodes[c]?.add(color)
            } else {
                nodes[c] = mutableListOf(color)
            }
        }
    }

    val solution = dfs("shiny gold", nodes, emptySet())
    println(solution.size - 1)
}

main("first.in")
