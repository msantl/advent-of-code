import java.io.File

fun getFuel(mass: Int): Int {
    var current: Int = mass;
    var result: Int = 0;

    while (true) {
        current = (current / 3) - 2;
        if (current <= 0) break;
        result += current;
    }

    return result;
}

fun main() {
    var sum: Int = 0;
    File("second.in").forEachLine { sum += getFuel(it.toInt()) }
    print(sum)
}

main();