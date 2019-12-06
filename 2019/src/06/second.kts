import java.io.File

val dp: HashMap<String, Int> = HashMap()
val edges: HashMap<String, List<String>> = HashMap()

fun dfs(curr: String, to: String, prev: String?): Int? {
    if (curr == to) return 0
    for (adj in edges.getOrDefault(curr, emptyList())) {
        if (adj == prev) continue

        val res: Int? = dfs(adj, to, curr)
        if (res != null) {
            return res + 1
        }
    }

    return null
}

fun main() {

    File("second.in").forEachLine {
        val (f, s) = it.trim().split(")")

        var a = edges.getOrDefault(f, emptyList())
        edges.put(f, a.plus(s))

        var b = edges.getOrDefault(s, emptyList())
        edges.put(s, b.plus(f))
    }

    val res: Int? = dfs("YOU", "SAN", null)

    if (res != null) {
        println(res - 2)
    }
}

main()