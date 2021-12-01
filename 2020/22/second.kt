import java.io.File

fun play(
    p1: List<Int>, p2: List<Int>,
): Pair<Int, List<Int>> {
    val memo = mutableSetOf<Pair<List<Int>, List<Int>>>()
    var player1 = p1
    var player2 = p2

    while (player1.size > 0 && player2.size > 0) {
        if (memo.contains(Pair(player1, player2))) {
            return Pair(1, player1)
        }
        memo.add(Pair(player1, player2))

        val c1 = player1.first()
        val c2 = player2.first()

        player1 = player1.minus(c1)
        player2 = player2.minus(c2)

        if (c1 <= player1.size && c2 <= player2.size) {
            val subGame = play(player1.subList(0, c1), player2.subList(0, c2))
            if (subGame.first == 1) {
                player1 = player1.plus(listOf(c1, c2))
            } else {
                player2 = player2.plus(listOf(c2, c1))
            }
        } else {
            if (c1 > c2) {
                player1 = player1.plus(listOf(c1, c2))
            } else {
                player2 = player2.plus(listOf(c2, c1))
            }
        }
    }

    if (player1.size > 0) {
        return Pair(1, player1)
    } else {
        return Pair(2, player2)
    }
}

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

    val (winner, deck) = play(player1.toList(), player2.toList())
    println("Winner is $winner")
    println(deck)
    println(deck.withIndex().map { (i, it) -> it * (deck.size - i) }.sum())
}

main("second.in")
