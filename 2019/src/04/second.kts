import com.sun.org.apache.xpath.internal.operations.Bool
import java.io.File

fun isValidPassword(number: String): Boolean {
    var satisfied: Boolean = false
    for (i in 1..number.length - 1) {
        if (number[i - 1] == number[i] && (i + 1 >= number.length || number[i] != number[i + 1]) && (i - 2 < 0 || number[i - 2] != number[i])) {
            satisfied = true
        }
    }
    if (!satisfied) return false;

    for (i in 1..number.length - 1) {
        if (number[i - 1] > number[i]) return false;
    }

    return true;
}

fun main() {
    val start: Int = 138307
    val end: Int = 654504
    var result: Int = 0

    for (number in start..end) {
        if (isValidPassword(number.toString())) {
            result++;
        }
    }

    print(result)
}

main()