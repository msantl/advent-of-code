package `2022_06`

import java.io.File

fun findStartOfPacket(line: String, uniqueCharLen: Int = 4): Int {
    val buffer: MutableMap<Char, Int> = mutableMapOf()
    for (i in 0..uniqueCharLen - 2) {
        val key = line[i]
        buffer[key] = buffer.getOrDefault(key, 0) + 1
    }

    for (i in uniqueCharLen - 1..line.length - 1) {
        val key = line[i]
        buffer[key] = buffer.getOrDefault(key, 0) + 1

        var foundMarker = true
        for (value in buffer.values) {
            if (value >= 2) {
                foundMarker = false
                break
            }
        }

        if (foundMarker) {
            return i + 1
        }

        val oldKey = line[i - uniqueCharLen + 1]
        buffer[oldKey] = buffer.getOrDefault(oldKey, 0) - 1
    }
    return -1
}

fun first(filename: String) {
    val input: List<String> = File(filename).readLines()

    for (line in input) {
        println(findStartOfPacket(line, 4))
    }
}

first("test.in")
first("first.in")


fun second(filename: String) {
    val input : List<String> = File(filename).readLines()

    for (line in input) {
        println(findStartOfPacket(line, 14))
    }
}

second("test.in")
second("first.in")
