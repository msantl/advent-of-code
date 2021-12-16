package `12`

import java.io.File

val START = "start"
val END = "end"

fun readInput(filename: String): Map<String, Set<String>> {
    val result = mutableMapOf<String, Set<String>>()
    File(filename).readLines()
        .map { it.split('-') }
        .map { it[0] to it[1] }
        .forEach {
            result[it.first] =
                result.getOrDefault(it.first, emptySet()).plus(it.second)
            result[it.second] =
                result.getOrDefault(it.second, emptySet()).plus(it.first)
        }
    return result
}

fun isVisited(cave: String, visited: List<String>): Boolean {
    return if (cave.all { it.isUpperCase() })
        return false
    else {
        visited.contains(cave)
    }
}

fun isSmallCave(cave: String): Boolean {
    if (cave in listOf(START, END)) return false
    if (cave.all { it.isUpperCase() }) return false
    return true
}

fun dfs(
    cave: String,
    visited: List<String>,
    map: Map<String, Set<String>>,
): List<List<String>> {
    if (cave == END) {
        return listOf(visited)
    }

    val result = mutableListOf<List<String>>()
    for (next in map.getOrDefault(cave, emptySet())) {
        if (isVisited(next, visited)) continue

        result.addAll(dfs(next, visited.plus(next), map))
    }
    return result
}

fun dfs2(
    cave: String,
    visited: List<String>,
    visitedSmallCaveTwiceFlag: Boolean,
    map: Map<String, Set<String>>,
): List<List<String>> {
    if (cave == END) {
        return listOf(visited)
    }

    val result = mutableListOf<List<String>>()
    for (next in map.getOrDefault(cave, emptySet())) {
        if (isVisited(next, visited)) {
            if (isSmallCave(next) && !visitedSmallCaveTwiceFlag) {
                result.addAll(
                    dfs2(
                        next,
                        visited.plus(next),
                        true,
                        map
                    )
                )
            }
        } else {
            result.addAll(
                dfs2(
                    next,
                    visited.plus(next),
                    visitedSmallCaveTwiceFlag,
                    map
                )
            )
        }
    }
    return result
}

fun first(filename: String) {
    val map = readInput(filename)
    val paths = dfs(START, listOf(START), map)
    println(paths.size)
}

first("test.in") // 10
first("small.in") // 19
first("input.in") // 5157

fun second(filename: String) {
    val map = readInput(filename)
    val paths = dfs2(START, listOf(START), false, map)
    println(paths.size)
}

second("test.in") // 36
second("small.in") // 103
second("input.in") // 144309
