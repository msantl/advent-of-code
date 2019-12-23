import java.io.File

fun main() {
    val N: Int = 10007
    var cards = generateSequence(0) { it + 1 }.take(N).toList()

    val actions = File("first.in").readLines()

    for (action in actions) {
        val cmds = action.split(" ")
        if (cmds[0] == "cut") {
            var M = cmds[1].toInt()
            if (M < 0) {
                M += N
            }
            cards = cards.drop(M) + cards.take(M)
        } else if (cmds[1] == "into") {
            // deal into new stack
            cards = cards.reversed()
        } else if (cmds[1] == "with") {
            // deal with increment
            var M = cmds[3].toInt()
            var it = 0
            var pos = 0
            val deck = generateSequence(0) { it + 1 }.take(N).toMutableList()

            while (it < N) {
                deck[pos] = cards[it]
                it += 1
                pos = (pos + M) % N
            }

            cards = deck.toList()
        }
    }

    println(cards)
    print(cards.withIndex().filter { it.value == 2019 }.map { it.index })
}

main()