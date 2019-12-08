import java.io.File

fun main() {
    val image: String = File("first.in").readText().trim()

    var layer: Int = 0
    var zeroCount: Int = Int.MAX_VALUE
    val layers: Int = image.length / (25 * 6)

    for (i in 1..layers) {
        var currentZeroCount: Int = 0
        for (x in 1..6) {
            for (y in 1..25) {
                if (image[25 * 6 * (i - 1) + 25 * (x - 1) + (y - 1)] == '0') {
                    currentZeroCount += 1
                }
            }
        }

        if (currentZeroCount < zeroCount) {
            zeroCount = currentZeroCount
            layer = i
        }
    }

    println(layer)
    var oneCount: Int = 0
    var twoCount: Int = 0

    for (x in 1..6) {
        for (y in 1..25) {
            if (image[25 * 6 * (layer - 1) + 25 * (x - 1) + (y - 1)] == '1') {
                oneCount += 1
            }
            if (image[25 * 6 * (layer - 1) + 25 * (x - 1) + (y - 1)] == '2') {
                twoCount += 1
            }
        }
    }

    println(oneCount * twoCount)
}

main()