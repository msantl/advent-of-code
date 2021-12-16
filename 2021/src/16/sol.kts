package `16`

import java.io.File

fun readInput(filename: String) =
    File(filename).readLines().first()

sealed class Packet {
    abstract val version: Int
    abstract val type: Int
}

data class LiteralValue(
    override val version: Int,
    override val type: Int,
    val value: Long
) : Packet()

data class Operator(
    override val version: Int,
    override val type: Int,
    val lengthTypeId: Int,
    val lengthTypeValue: Int,
    val subPackets: List<Packet>
) : Packet()

fun getValue(input: String): Pair<Long, Int> {
    var value = ""
    var i = 0
    var lastChunk = false

    while (!lastChunk && i + 5 <= input.length) {
        if (input[i] == '0') lastChunk = true
        value += input.substring(i + 1, i + 5)
        i += 5
    }

    return Pair(value.toLong(2), i)
}

fun parse(input: String): Pair<Packet, Int> {
    var lastIndex = 0

    val version = input.substring(lastIndex, lastIndex + 3).toInt(2)
    lastIndex += 3

    val type = input.substring(lastIndex, lastIndex + 3).toInt(2)
    lastIndex += 3

    return if (type == 4) {
        // LiteralValue
        val (value, nextLastIndex) = getValue(input.substring(lastIndex))
        lastIndex += nextLastIndex

        Pair(LiteralValue(version, type, value), lastIndex)
    } else {
        // Operator
        val lengthTypeId = input.substring(lastIndex, lastIndex + 1).toInt(2)
        lastIndex += 1

        val lengthType = if (lengthTypeId == 0) {
            // 15 bits
            val value = input.substring(lastIndex, lastIndex + 15)
            lastIndex += 15
            value.toInt(2)
        } else {
            // 11 bits
            val value = input.substring(lastIndex, lastIndex + 11)
            lastIndex += 11
            value.toInt(2)
        }

        val subPackets = mutableListOf<Packet>()
        // Handle subpackets
        if (lengthTypeId == 0) {
            // read next lengthType bits and convert to packets

            var bitsRead = 0
            while (bitsRead < lengthType) {
                val (nextPacket, nextLastIndex) = parse(
                    input.substring(
                        lastIndex
                    )
                )
                lastIndex += nextLastIndex
                subPackets.add(nextPacket)

                bitsRead += nextLastIndex
            }

        } else {
            // read next lengthType packets
            for (i in 0 until lengthType) {
                val (nextPacket, nextLastIndex) = parse(
                    input.substring(
                        lastIndex
                    )
                )
                lastIndex += nextLastIndex
                subPackets.add(nextPacket)
            }
        }
        Pair(
            Operator(version, type, lengthTypeId, lengthType, subPackets),
            lastIndex
        )
    }
}

fun convertToBinary(input: String): String {
    val conversion = mapOf(
        '0' to "0000", '1' to "0001", '2' to "0010", '3' to "0011",
        '4' to "0100", '5' to "0101", '6' to "0110", '7' to "0111",
        '8' to "1000", '9' to "1001", 'A' to "1010", 'B' to "1011",
        'C' to "1100", 'D' to "1101", 'E' to "1110", 'F' to "1111"
    )

    return String(
        input
            .map { conversion.getValue(it) }
            .flatMap { it.toList() }
            .toCharArray()
    )
}

fun sumPacketVersions(root: Packet): Int {
    return when (root) {
        is LiteralValue -> root.version
        is Operator -> root.version + root.subPackets.map { sumPacketVersions(it) }
            .sum()
        else -> 0
    }
}

fun first(filename: String) {
    val input = readInput(filename)
    val binaryInput = convertToBinary(input)
    val (packet, _) = parse(binaryInput)
    println(sumPacketVersions(packet))
}

first("small.in") // 9
first("test.in") // 31
first("input.in") // 843

fun evaluate(root: Packet): Long {
    return when (root) {
        is LiteralValue -> root.value
        is Operator -> {
            when (root.type) {
                /* sum */ 0 -> {
                root.subPackets.map { evaluate(it) }.sum()
            }
                /* mul */ 1 -> {
                root.subPackets.map { evaluate(it) }
                    .fold(1) { mul, fac -> mul * fac }
            }
                /* min */ 2 -> {
                root.subPackets.map { evaluate(it) }.minOf { it }
            }
                /* max */ 3 -> {
                root.subPackets.map { evaluate(it) }.maxOf { it }
            }
                /* > */ 5 -> {
                val values = root.subPackets.map { evaluate(it) }
                if (values[0] > values[1]) {
                    1L
                } else {
                    0L
                }
            }
                /* < */ 6 -> {
                val values = root.subPackets.map { evaluate(it) }
                if (values[0] < values[1]) {
                    1L
                } else {
                    0L
                }
            }
                /* = */ 7 -> {
                val values = root.subPackets.map { evaluate(it) }
                if (values[0] == values[1]) {
                    1L
                } else {
                    0L
                }
            }
                else -> 0
            }
        }
        else -> 0
    }
}

fun second(filename: String) {
    val input = readInput(filename)
    val binaryInput = convertToBinary(input)
    val (packet, _) = parse(binaryInput)
    println(evaluate(packet))
}

second("test_2.in") // 1
second("input.in") // 5390807940351
