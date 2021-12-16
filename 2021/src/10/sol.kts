package `10`

import utils.Stack
import java.io.File


val closingCharMap = mapOf(
    '(' to ')',
    '{' to '}',
    '[' to ']',
    '<' to '>'
)

val openChunkChars = listOf('(', '{', '[', '<')

fun findCorruptedChunkClosingCharIfAny(line: String): Char? {
    val stack = Stack<Char>()
    for (char in line) {
        if (char in openChunkChars) {
            stack.push(char)
        } else {
            val lastOpenedChunk = stack.pop() ?: return null

            val expectedCloseChunkChar =
                closingCharMap.getValue(lastOpenedChunk)
            if (char != expectedCloseChunkChar) {
                return char
            }
        }
    }
    return null
}

fun first(filename: String) {
    val input = File(filename).readLines()

    val scores = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137
    )

    var score = 0
    for (line in input) {
        val corruptedChunkChar = findCorruptedChunkClosingCharIfAny(line)
        if (corruptedChunkChar != null) {
            score += scores.getValue(corruptedChunkChar)
        }
    }
    println(score)
}

first("test.in") // 26397
first("input.in") // 387363

fun getIncompleteChunkPatch(line: String): String {
    val stack = Stack<Char>()
    for (char in line) {
        if (char in openChunkChars) {
            stack.push(char)
        } else {
            stack.pop()
        }
    }
    var result = ""
    while (!stack.isEmpty) {
        result += closingCharMap.getValue(stack.pop()!!)
    }
    return result
}

fun getScoreForPatch(patch: String): Long {
    val points = mapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4
    )
    var score = 0L
    for (char in patch) {
        score = 5 * score + points.getValue(char)
    }

    return score
}

fun second(filename: String) {
    val input = File(filename).readLines()
        .filter { findCorruptedChunkClosingCharIfAny(it) == null }

    val scores = mutableListOf<Long>()
    for (line in input) {
        val patch = getIncompleteChunkPatch(line)
        val patchScore = getScoreForPatch(patch)
        scores.add(patchScore)
    }
    println(scores.sorted()[scores.size / 2])
}

second("test.in") // 288957
second("input.in") // 4330777059
