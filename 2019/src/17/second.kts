import java.io.File
import java.util.stream.Collectors
import kotlin.math.max

fun getValueModes(command: Int): List<Int> {
    var cmd: String = command.toString()
    while (cmd.length < 5) cmd = "0" + cmd

    return listOf(
        cmd.substring(3, 5).toInt(),
        cmd.substring(2, 3).toInt(),
        cmd.substring(1, 2).toInt(),
        cmd.substring(0, 1).toInt()
    )
}

var memory: MutableMap<Int, Int> = mutableMapOf()
var ip: Int = 0

var relativeBase: Int = 0
var halt: Boolean = false

fun runOnce(input: List<Int>, returnOnOutput: Boolean): MutableList<Int> {
    var inputPointer: Int = 0
    val results: MutableList<Int> = mutableListOf()

    while (!halt) {
        val command: Int = memory.getOrDefault(ip++, 0)
        val modes: List<Int> = getValueModes(command)

        when (modes.get(0)) {
            1 -> {
                var val1: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 0) val1 = memory.getOrDefault(val1, 0)
                else if (modes.get(1) == 2) val1 = memory.getOrDefault(val1 + relativeBase, 0)

                var val2: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(2) == 0) val2 = memory.getOrDefault(val2, 0)
                else if (modes.get(2) == 2) val2 = memory.getOrDefault(val2 + relativeBase, 0)

                var addr: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(3) == 2) addr = addr + relativeBase

                memory.put(addr, val1 + val2)
            }
            2 -> {
                var val1: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 0) val1 = memory.getOrDefault(val1, 0)
                else if (modes.get(1) == 2) val1 = memory.getOrDefault(val1 + relativeBase, 0)

                var val2: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(2) == 0) val2 = memory.getOrDefault(val2, 0)
                else if (modes.get(2) == 2) val2 = memory.getOrDefault(val2 + relativeBase, 0)

                var addr: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(3) == 2) addr = addr + relativeBase

                memory.put(addr, val1 * val2)
            }
            3 -> {
                var addr: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 2) addr = addr + relativeBase

                // Input
                memory.put(addr, input.get(inputPointer))
                inputPointer++
            }
            4 -> {
                var res: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 0) res = memory.getOrDefault(res, 0)
                else if (modes.get(1) == 2) res = memory.getOrDefault(res + relativeBase, 0)

                // Output
                if (returnOnOutput) {
                    return mutableListOf(res)
                }
                results.add(res)
            }
            5 -> {
                var val1: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 0) val1 = memory.getOrDefault(val1, 0)
                else if (modes.get(1) == 2) val1 = memory.getOrDefault(val1 + relativeBase, 0)

                var val2: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(2) == 0) val2 = memory.getOrDefault(val2, 0)
                else if (modes.get(2) == 2) val2 = memory.getOrDefault(val2 + relativeBase, 0)

                if (val1 != 0) ip = val2
            }
            6 -> {
                var val1: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 0) val1 = memory.getOrDefault(val1, 0)
                else if (modes.get(1) == 2) val1 = memory.getOrDefault(val1 + relativeBase, 0)

                var val2: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(2) == 0) val2 = memory.getOrDefault(val2, 0)
                else if (modes.get(2) == 2) val2 = memory.getOrDefault(val2 + relativeBase, 0)

                if (val1 == 0) ip = val2
            }
            7 -> {
                var val1: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 0) val1 = memory.getOrDefault(val1, 0)
                else if (modes.get(1) == 2) val1 = memory.getOrDefault(val1 + relativeBase, 0)

                var val2: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(2) == 0) val2 = memory.getOrDefault(val2, 0)
                else if (modes.get(2) == 2) val2 = memory.getOrDefault(val2 + relativeBase, 0)

                var addr: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(3) == 2) addr = addr + relativeBase

                memory.put(addr, if (val1 < val2) 1 else 0)
            }
            8 -> {
                var val1: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 0) val1 = memory.getOrDefault(val1, 0)
                else if (modes.get(1) == 2) val1 = memory.getOrDefault(val1 + relativeBase, 0)

                var val2: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(2) == 0) val2 = memory.getOrDefault(val2, 0)
                else if (modes.get(2) == 2) val2 = memory.getOrDefault(val2 + relativeBase, 0)

                var addr: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(3) == 2) addr = addr + relativeBase

                memory.put(addr, if (val1 == val2) 1 else 0)
            }
            9 -> {
                var value: Int = memory.getOrDefault(ip++, 0)
                if (modes.get(1) == 0) value = memory.getOrDefault(value, 0)
                else if (modes.get(1) == 2) value = memory.getOrDefault(value + relativeBase, 0)

                relativeBase += value
            }
            99 -> halt = true
        }
    }
    return results
}

val map: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()

fun main() {
    val commands: List<Int> = File("second.in")
        .readText()
        .trim()
        .split(",")
        .map { it.toInt() }
        .toList()

    relativeBase = 0
    halt = false
    ip = 0
    memory = mutableMapOf()
    for (cmd in commands.withIndex()) {
        memory.put(cmd.index, cmd.value)
    }

    var pos_x = 0
    var pos_y = 0

    while (true) {
        val res = runOnce(emptyList(), true)
        if (res.isEmpty()) break
        if (res.first() == 10) {
            pos_y += 1
            pos_x = 0
        } else {
            map.put(pos_y to pos_x, res.first())
            pos_x += 1
        }
    }

    var pos = map.entries.stream()
        .filter { it -> it.value != 35 && it.value != 46 }
        .map { it.key }
        .collect(Collectors.toList())
        .first()!!

    val dir = listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)
    var previousDirId: Int = -1

    when (map.getValue(pos).toChar()) {
        ('^') -> previousDirId = 1
        ('v') -> previousDirId = 0
        ('<') -> previousDirId = 3
        ('>') -> previousDirId = 2
    }

    val solution: MutableList<String> = mutableListOf()

    while (true) {
        var dirId: Int = -1

        for (i in 0 until 4) {
            if (dir[i].first == dir[previousDirId].first || dir[i].second == dir[previousDirId].second) continue
            if (map.getOrDefault((pos.first + dir[i].first) to (pos.second + dir[i].second), 0) == 35) {
                dirId = i
            }
        }

        if (dirId == -1) break

        var turn: Char = 'R'
        if (dir[previousDirId] == -1 to 0 && dir[dirId] == 0 to -1) turn = 'L'
        if (dir[previousDirId] == 1 to 0 && dir[dirId] == 0 to 1) turn = 'L'
        if (dir[previousDirId] == 0 to 1 && dir[dirId] == -1 to 0) turn = 'L'
        if (dir[previousDirId] == 0 to -1 && dir[dirId] == 1 to 0) turn = 'L'

        var len: Int = 0
        while (map.getOrDefault((pos.first + dir[dirId].first) to (pos.second + dir[dirId].second), 0) == 35) {
            pos = pos.first + dir[dirId].first to pos.second + dir[dirId].second
            len += 1
        }

        previousDirId = dirId
        solution.add("${turn},${len}")
    }

    relativeBase = 0
    halt = false
    ip = 0
    memory = mutableMapOf()
    for (cmd in commands.withIndex()) {
        memory.put(cmd.index, cmd.value)
    }
    memory.put(0, 2)

    val lists: MutableList<MutableList<String>> = mutableListOf()
    val bio: MutableMap<Int, Char> = mutableMapOf()
    val arr: MutableMap<Int, Char> = mutableMapOf()
    var groupMark: Char = 'A'

    for (first in 0 until solution.size) {
        if (bio.containsKey(first)) continue

        var subSolution: MutableList<Int> = mutableListOf()
        var subSolutionLength: Int = 0
        for (length in 5 downTo 1) {

            if (first + length > solution.size) continue

            var alreadyVisited = false
            var subSolutionLengthCheck = 0
            for (j in 0 until length) {
                if (bio.containsKey(first + j)) {
                    alreadyVisited = true
                }
                subSolutionLengthCheck += solution[first + j].length + 1
            }

            if ((first == 0 && subSolutionLengthCheck > 20) || (first > 0 && subSolutionLengthCheck > 21)) {
                continue
            }

            if (alreadyVisited) {
                continue
            }

            val repeating: MutableList<Int> = mutableListOf()
            var offset: Int = first + length
            while (offset <= solution.size - length) {
                var found = true
                for (j in 0 until length) {
                    if (bio.containsKey(offset + j) || solution[first + j] != solution[offset + j]) {
                        found = false;
                        break
                    }
                }
                if (found) {
                    repeating.add(offset)
                    offset += length
                } else {
                    offset += 1
                }
            }

            if (repeating.size > 0) {
                subSolution = repeating
                subSolutionLength = length
                break
            }
        }

        val list: MutableList<String> = mutableListOf()
        for (j in 0 until subSolutionLength) {
            list.add(solution[first + j])
            bio.put(first + j, groupMark)

            for (starts in subSolution) {
                bio.put(starts + j, groupMark)
            }
        }

        arr.put(first, groupMark)
        for (starts in subSolution) {
            arr.put(starts, groupMark)
        }

        lists.add(list)
        groupMark += 1
    }

    val arrangement: MutableList<Char> = mutableListOf()
    for (i in 0 until solution.size) {
        if (arr.containsKey(i)) {
            arrangement.add(arr.getValue(i))
        }
    }

    val finalSolution = mutableListOf(arrangement.joinToString(","))
    for (list in lists) {
        finalSolution.add(list.joinToString(","))
    }

    val instructions: MutableList<Int> = mutableListOf()

    for (i in 0 until finalSolution.size) {
        for (j in 0 until finalSolution[i].length) {
            instructions.add(finalSolution[i][j].toInt())
        }
        instructions.add(10)
    }

    instructions.add('n'.toInt())
    instructions.add(10)

    val final = runOnce(instructions, false)
    println(final.last())
}

main()

