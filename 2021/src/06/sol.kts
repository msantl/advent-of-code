package `06`

import java.io.File

data class Fish(val timer: Int) {
    fun tick(): List<Fish> {
        return if (timer == 0) {
            listOf(Fish(6), Fish(8))
        } else {
            listOf(Fish(timer - 1))
        }
    }
}

fun first(filename: String) {
    var fish =
        File(filename).readLines().first().split(',').map { Fish(it.toInt()) }
    for (age in 1..80) {
        fish = fish.map { it.tick() }.flatMap { it.toList() }
    }
    println(fish.size)
}

first("test.in") // 5934
first("input.in") // 388419

fun solve(timer: Int, days: Int, cache: MutableMap<Pair<Int, Int>, Long>): Long {
    if (days == 0) {
        return 1L
    }
    if (cache.containsKey(Pair(timer, days))) {
        return cache.getValue(Pair(timer, days))
    }

    val result = if (timer == 0) {
        solve(6, days - 1, cache) + solve(8, days - 1, cache)
    } else {
        solve(timer - 1, days - 1, cache)
    }
    cache[Pair(timer, days)] = result
    return result
}

fun second(filename: String) {
    val allFish =
        File(filename).readLines().first().split(',').map { Fish(it.toInt()) }
    val uniqueFish = allFish.groupBy { it.timer }
    val fish = uniqueFish.keys.map { Fish(it) }

    var sol = 0L
    val cache = mutableMapOf<Pair<Int, Int>, Long>()
    for (f in fish) {
        val curr = solve(f.timer, 256, cache) * uniqueFish.getValue(f.timer).size
        sol += curr
    }

    println(sol)
}

second("test.in") // 26984457539
second("input.in") //