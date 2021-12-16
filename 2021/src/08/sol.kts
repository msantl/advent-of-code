package `08`

import java.io.File

val digitsToSegments = mapOf(
    0 to "abcefg",
    1 to "cf",
    2 to "acdeg",
    3 to "acdfg",
    4 to "bcdf",
    5 to "abdfg",
    6 to "abdefg",
    7 to "acf",
    8 to "abcdefg",
    9 to "abcdfg"
)

val reverseLookup =
    digitsToSegments.entries.associate { (key, value) -> value to key }
val lengthLookup = digitsToSegments.entries.groupBy { it.value.length }

fun first(filename: String) {
    val input = File(filename).readLines().map { it.split("|").last().trim() }
        .map { it.split(' ') }

    val uniqueLengthDigits = lengthLookup.filterValues { it.size == 1 }
        .flatMap { it.value }

    var count = 0
    for (line in input) {
        for (number in line) {
            count += uniqueLengthDigits.filter { it.value.length == number.length }.size
        }
    }
    println(count)
}

first("test.in") // 26
first("input.in") // 525

fun generateAllMappings(
    mapFrom: List<Char>,
    mapTo: List<Char>
): Set<Map<Char, Char>> {
    if (mapFrom.isEmpty() && mapTo.isEmpty()) {
        return setOf(emptyMap())
    }
    val result = mutableSetOf<Map<Char, Char>>()
    for (from in mapFrom) {
        for (to in mapTo) {
            val lists =
                generateAllMappings(mapFrom.minus(from), mapTo.minus(to))
                    .map { it.plus(from to to) }
            result.addAll(lists)
        }
    }
    return result
}

fun findMapping(
    numbers: List<String>,
    mappings: Set<Map<Char, Char>>
): Map<Char, Char> {
    for (mapping in mappings) {
        val isValidMapping =
            numbers.map { it.map { ch -> mapping.getValue(ch) } }
                .map { it.sorted() }
                .map { String(it.toCharArray()) }
                .all { reverseLookup.containsKey(it) }

        if (isValidMapping) {
            return mapping
        }
    }
    return emptyMap()
}

val defaultValues = listOf('a', 'b', 'c', 'd', 'e', 'f', 'g')
val allMappings = generateAllMappings(defaultValues, defaultValues)

fun second(filename: String) {
    var solution = 0

    val input = File(filename).readLines()
    for (line in input) {
        val lineParts = line.split('|')
        val numbersToCheck = lineParts.first().trim().split(' ')
        val numbersToSum = lineParts.last().trim().split(' ')

        // Find mapping
        val mapping = findMapping(numbersToCheck, allMappings)

        val translated =
            numbersToSum.map { it.map { ch -> mapping.getValue(ch) } }
                .map { it.sorted() }
                .map { String(it.toCharArray()) }
                .map { reverseLookup.getValue(it) }
                .reduce { acc, i -> 10 * acc + i }

        solution += translated
    }
    println(solution)
}


second("small.in") // 5353
second("test.in") // 61229
second("input.in") // 1083859