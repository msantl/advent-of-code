import java.io.File
import java.util.regex.Pattern

data class Rule(
    val char: Char? = null, val branch: List<Int>? = null, val complex: List<List<Int>>? = null
)

var rules = mutableMapOf<Int, Rule>()

fun countMatchingCharacters(str: String, rule: Rule): Set<Int> {
    if (rule.char != null) {
        if (str.get(0) == rule.char) {
            return setOf(1)
        } else {
            return setOf(Int.MAX_VALUE)
        }
    } else if (rule.branch != null) {
        val first = rule.branch.first()
        val firstMatches = countMatchingCharacters(str, rules.get(first)!!)

        if (rule.branch.size == 1) {
            return firstMatches
        }

        val rest = rule.branch.takeLast(rule.branch.size - 1)
        val result = mutableSetOf<Int>()

        for (matches in firstMatches) {
            if (matches < str.length) {
                val otherMatches = countMatchingCharacters(str.substring(matches), Rule(branch = rest))
                otherMatches.filter { it != Int.MAX_VALUE }.map { it + matches }.forEach { result.add(it) }
            }
        }
        return result
    } else if (rule.complex != null) {
        var result = mutableSetOf<Int>()
        for (branch in rule.complex) {
            result.addAll(countMatchingCharacters(str, Rule(branch = branch)))
        }
        return result
    } else {
        throw Exception("Invlid rule $rule")
    }
}

fun main(filename: String) {
    var solution = 0
    var ingestRules = true

    val charPattern = Pattern.compile(" \"(\\p{Alnum}+)\"")

    File(filename).readLines().forEach {
        if (it.length == 0) {
            ingestRules = false
        } else if (ingestRules) {
            val (ruleIdString, rulePayload) = it.split(":")
            val ruleId = ruleIdString.toInt()

            val matcher = charPattern.matcher(rulePayload)

            val rule = if (matcher.matches()) {
                val charToMatch = matcher.group(1)[0]
                Rule(char = charToMatch)
            } else {
                val ruleRefs = rulePayload.split("|").map { rp -> rp.split(" ") }
                    .map { rpl -> rpl.map { it.toIntOrNull() }.filterNotNull() }
                Rule(complex = ruleRefs)
            }

            rules.put(ruleId, rule)

        } else {
            if (countMatchingCharacters(it, rules.get(0)!!).contains(it.length)) {
                solution += 1
            }
        }
    }

    println(solution)
}

main("second.in")
// 243
