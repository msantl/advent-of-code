import java.io.File

fun validateBYR(value: String?): Boolean {
    if (value == null) return false
    val intValue = value.toIntOrNull()
    if (intValue == null) return false
    return if (intValue >= 1920 && intValue <= 2002) true else false
}

fun validateIYR(value: String?): Boolean {
    if (value == null) return false
    val intValue = value.toIntOrNull()
    if (intValue == null) return false
    return if (intValue >= 2010 && intValue <= 2020) true else false
}

fun validateEYR(value: String?): Boolean {
    if (value == null) return false
    val intValue = value.toIntOrNull()
    if (intValue == null) return false
    return if (intValue >= 2020 && intValue <= 2030) true else false
}

fun validateHGT(value: String?): Boolean {
    if (value == null) return false
    if (value.length < 3) return false
    val allowedUnits = listOf("cm", "in")
    val unit = value.takeLast(2)
    if (!allowedUnits.contains(unit)) return false
    val height = value.take(value.length - 2).toIntOrNull()

    if (height == null) return false

    return when (unit) {
        "cm" -> if (150 <= height && height <= 193) true else false
        "in" -> if (59 <= height && height <= 76) true else false
        else -> false
    }
}

fun validateHCL(value: String?): Boolean {
    if (value == null) return false;
    val allowedChars = listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')
    if (value.length != 7) return false
    if (value[0] != '#') return false
    for (i in 1..6) {
        if (!allowedChars.contains(value[i])) return false
    }
    return true
}

fun validateECL(value: String?): Boolean {
    if (value == null) return false
    val validValues = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    return validValues.contains(value)
}

fun validatePID(value: String?): Boolean {
    if (value == null) return false
    if (value.length != 9) return false
    val intValue = value.toIntOrNull()
    if (intValue == null) return false
    return true
}

fun validateCID(value: String?) = true

fun checkCurrentPassport(passport: Map<String, String>): Boolean {
    val fields = mapOf(
        "byr" to ::validateBYR,
        "iyr" to ::validateIYR,
        "eyr" to ::validateEYR,
        "hgt" to ::validateHGT,
        "hcl" to ::validateHCL,
        "ecl" to ::validateECL,
        "pid" to ::validatePID,
        "cid" to ::validateCID
    )

    for ((key, validator) in fields) {
        if (!validator(passport.get(key))) {
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

main("second.in")
