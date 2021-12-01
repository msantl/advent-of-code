import java.io.File

fun main(filename: String) {
    val player1 = mutableListOf<Int>()
    val player2 = mutableListOf<Int>()

    var player1Turn = true

    File(filename).readLines().forEach {
        if (it.length == 0) {
            // do nothing
        } else if (it == "Player 1:") {
            player1Turn = true
        } else if (it == "Player 2:") {
            player1Turn = false
        } else if (player1Turn) {
            player1.add(it.toInt())
        } else if (!player1Turn) {
            player2.add(it.toInt())
        }
    }

    while (player1.size > 0 && player2.size > 0) {
        val c1 = player1.removeFirst()
        val c2 = player2.removeFirst()

        if (c1 > c2) {
            player1.add(c1)
            player1.add(c2)
        } else {
            player2.add(c2)
            player2.add(c1)
        }
    }

    println(player1.withIndex().map { (i, it) -> it * (player1.size - i) }.sum())
    println(player2.withIndex().map { (i, it) -> it * (player2.size - i) }.sum())
}

main("first.in")
