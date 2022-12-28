package `2022_13`

import java.io.File

sealed class Element

class ENumber(val value: Int) : Element() {
    override fun toString(): String {
        return value.toString()
    }
}

class EList(val value: List<Element>) : Element() {
    override fun toString(): String {
        return "[" + value.map { it.toString() }.joinToString(", ") + "]"
    }
}

fun parseInput(input: String, i: Int = 0): Pair<Element, Int> {
    if (input[i] == '[') {
        val elements: MutableList<Element> = mutableListOf<Element>()
        var numBuffer: Int? = null
        var j = i + 1

        while (j < input.length) {
            if ('0' <= input[j] && input[j] <= '9') {
                if (numBuffer == null) numBuffer = 0
                numBuffer = numBuffer * 10 + input[j].minus('0')
                j += 1
            } else if (input[j] == ',') {
                if (numBuffer != null) elements.add(ENumber(numBuffer))
                numBuffer = null
                j += 1
            } else if (input[j] == '[') {
                val subParse = parseInput(input, j)

                elements.add(subParse.first)
                j = subParse.second

            } else if (input[j] == ']') {
                if (numBuffer != null) elements.add(ENumber(numBuffer))
                numBuffer = null
                j += 1
                break
            } else {
                throw Exception("Unexpeted char ${input[j]} in $input")
            }
        }
        return Pair(EList(elements), j)
    }
    throw Exception("Unexpected input $input")
}

fun areInOrder(lhs: Element, rhs: Element): Int {
    if (lhs is ENumber && rhs is ENumber) {
        if (lhs.value < rhs.value) return 1
        if (lhs.value > rhs.value) return -1
        else return 0
    } else if (lhs is EList && rhs is EList) {
        var i = 0
        var j = 0
        while (true) {
            if (i >= lhs.value.size && j >= rhs.value.size) return 0
            if (i >= lhs.value.size) return 1
            if (j >= rhs.value.size) return -1

            val order = areInOrder(lhs.value[i], rhs.value[j])
            if (order != 0) return order
            i += 1
            j += 1
        }
    } else if (lhs is ENumber && rhs is EList) {
        return areInOrder(EList(listOf(lhs)), rhs)
    } else if (lhs is EList && rhs is ENumber) {
        return areInOrder(lhs, EList(listOf(rhs)))
    } else {
        throw Exception("Unexpected condition!")
    }
}

fun first(filename: String) {
    val input: List<Element> = File(filename).readLines().filter { it.length > 0 }.map { parseInput(it).first }

    var sol = 0
    var i = 0
    var index = 1
    while (i < input.size) {
        val left = input[i]
        val right = input[i + 1]

        if (areInOrder(left, right) == 1) {
            sol += index
        }

        i += 2
        index += 1
    }
    println(sol)
}

first("test.in") // 13
first("first.in")


fun second(filename: String) {
    val input: List<Element> = File(filename).readLines().filter { it.length > 0 }.map { parseInput(it).first }

    val pckt1 = EList(listOf(EList(listOf(ENumber(2)))))
    val pckt2 = EList(listOf(EList(listOf(ENumber(6)))))

    val sortedList = input.plus(listOf(pckt1, pckt2)).sortedWith(Comparator<Element> { lhs, rhs -> areInOrder(lhs, rhs) }).reversed()

    val sol = (sortedList.indexOf(pckt1)+ 1) * (sortedList.indexOf(pckt2) + 1)
    println(sol)
}


second("test.in") // 140
second("first.in")
