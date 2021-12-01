import java.io.File

fun checkCurrentPassport(passport: Map<String, String>): Boolean {
    val fields = mapOf(
        "byr" to true,
        "iyr" to true,
        "eyr" to true,
        "hgt" to true,
        "hcl" to true,
        "ecl" to true,
        "pid" to true,
        "cid" to false
    )

    for ((key, required) in fields) {
        if (required == true && passport.get(key) == null) {
            return false
        }
    }
    return true
}

fun main(filename: String) {
    val input = File(filename).readLines()
    var solution = 0


    var passport = mutableMapOf<String, String>()

    for (line in input) {
        if (line.length == 0) {
            if (checkCurrentPassport(passport)) {
                solution += 1
            }
            passport.clear()
            continue
        }

        for (data in line.split(" ")) {
            val (key, value) = data.split(":")
            passport[key] = value
        }

    }

    if (checkCurrentPassport(passport)) {
        solution += 1
    }

    println(solution)
}

main("testA.in")
