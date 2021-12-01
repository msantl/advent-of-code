import java.io.File

fun rearrange(cups: String, x: Int): String {
    var next = ""
    for (i in 1..3) {
        next += cups[(x + i) % cups.length]
    }

    var nextId = -1
    var curr = Character.getNumericValue(cups[x])
    while (nextId == -1) {
        curr -= 1
        if (curr <= 0) {
            curr = 9
        }
        for (i in 0..cups.length - 1) {
            if (next.contains(cups[i])) continue
            val candidate = Character.getNumericValue(cups[i])
            if (candidate == curr) {
                nextId = i
                break
            }
        }
    }

    var result = ""
    for (i in 0..cups.length - 1) {
        if (next.contains(cups[i])) continue

        result += cups[i]
        if (i == nextId) {
            result += next
        }
    }
    return result
}

fun main(filename: String) {
    var cups = File(filename).readLines().first()
    var curr = 0

    for (i in 0..99) {
        val currentCup = cups[curr]
        cups = rearrange(cups, curr)
        for (j in 0 .. cups.length -1) {
            if (cups[j] == currentCup) {
                curr = (j + 1) % cups.length
                break
            }
        }
    }

    println(cups)
}

main("first.in")
