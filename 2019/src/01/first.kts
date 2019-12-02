import java.io.File

fun getFuel(mass: Int) : Int {
    return (mass / 3) - 2;
}

fun main() {
    var sum: Int = 0;
    File("first.in").forEachLine { sum += getFuel(it.toInt()) }
    print(sum);
}

main();