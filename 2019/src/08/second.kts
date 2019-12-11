import java.io.File

fun main() {
    val image: String = File("second.in").readText().trim()
    val layers: Int = image.length / (25 * 6)

    var pixels: Map<Pair<Int, Int>, List<Char>> = emptyMap()

    for (i in 1..layers) {
        for (x in 1..6) {
            for (y in 1..25) {
                val curr: List<Char> = pixels.getOrDefault(x to y, emptyList())
                pixels = pixels.plus((x to y) to curr.plus(image[25 * 6 * (i - 1) + 25 * (x - 1) + (y - 1)]))
            }
        }
    }

    for (x in 1..6) {
        for (y in 1..25) {
            for (pixel in pixels.getValue(x to y)) {
                if (pixel == '2') continue;
                if (pixel == '0') {
                    print(" ")
                    break
                }
                if (pixel == '1') {
                    print("*")
                    break
                }
            }
        }
        println("")
    }
}

main()