import java.io.File

fun dfs(curr: String, nodes: MutableMap<String, MutableList<Pair<String, Int>>>, visited: Set<String>): Int {
    var res = 1
    nodes.getOrDefault(curr, mutableListOf()).forEach { (c, v) ->
        if (!visited.contains(c)) {
            res += v * dfs(c, nodes, visited.plus(c))
        }
    }
    return res
}

fun main(filename: String) {
    val input = File(filename).readLines()
    val nodes = mutableMapOf<String, MutableList<Pair<String, Int>>>()

    input.forEach {
        val color = it.split(" ").take(2).joinToString(separator = " ") { it }
        it.split("contain").last().split(",").filter { it != " no other bags." }.forEach {
            val items = it.split(" ").subList(1, 2).joinToString(separator = " ") { it }.toInt()
            val c = it.split(" ").subList(2, 4).joinToString(separator = " ") { it }
            if (nodes.containsKey(color)) {
                nodes[color]?.add(Pair(c, items))
            } else {
                nodes[color] = mutableListOf(Pair(c, items))
            }
        }
    }
    val solution = dfs("shiny gold", nodes, emptySet())
    println(solution - 1)
}

main("second.in")
