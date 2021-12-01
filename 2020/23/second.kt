import java.io.File

data class Node(
    val V: Int, var next: Node?
)

fun main(filename: String) {
    var cups = File(filename).readLines().first().map { Character.getNumericValue(it) }.plus(10..1000000)

    val lookup = mutableMapOf<Int, Node>()

    val root = Node(cups[0], null)
    lookup[cups[0]] = root

    var prev = root
    for (i in cups.size - 1 downTo 1) {
        prev = Node(cups[i], prev)
        lookup[cups[i]] = prev
    }
    root.next = prev

    var pt = root
    for (IT in 1..10000000) {
        val paste = pt.next!!
        val values = mutableListOf<Int>()

        for (i in 1..3) {
            val valueTaken = pt.next?.V!!
            values.add(valueTaken)
            pt.next = pt.next?.next ?: throw Exception("Linked list error!")
        }

        var destinationValue = pt.V
        while (true) {
            destinationValue = destinationValue - 1
            if (destinationValue <= 0) destinationValue = 1000000
            if (!values.contains(destinationValue)) break
        }

        var curr = lookup[destinationValue] ?: throw Exception("Failed to find $destinationValue in lookup map")

        val cut = curr.next!!
        curr.next = paste

        for (i in 1..3) {
            curr = curr.next ?: throw Exception("Linked list error!")
        }
        curr.next = cut

        pt = pt.next ?: throw Exception("Linked list error!")
    }

    while (true) {
        if (pt.V == 1) {
            val a = pt.next?.V!!
            val b = pt.next?.next?.V!!
            println(1L * a * b)
            break
        }
        pt = pt.next ?: throw Exception("Linked list error!")
    }
}

main("second.in")
