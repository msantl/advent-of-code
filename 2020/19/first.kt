import java.io.File
import java.util.regex.Pattern

data class Rule(
    val char: Char?, val refs: List<List<Int>>?
)

fun flatList(x: Int, list: List<List<String>>) : List<String> {
    if (x >= list.size) return emptyList()
    if (x == list.size - 1) return list[x]

    var result = mutableListOf<String>()
    val others = flatList(x + 1, list)
    for (w in list[x]) {
        for (other in others) {
            result.add("$w$other")
        }
    }
    return result.toList()
}

fun buildRuleBase(ruleId: Int, rules: Map<Int, Rule>): List<String> {
    val result = mutableListOf<String>()
    val rule = rules.get(ruleId) ?: throw Exception("Missing rule with id $ruleId")

    if (rule.char != null) {
        result.add(rule.char.toString())
    } else if (rule.refs != null) {
        for (refs in rule.refs) {
            val branch = mutableListOf<List<String>>()
            for (ref in refs) {
                branch.add(buildRuleBase(ref, rules))
            }
            result.addAll(flatList(0, branch.toList()))
        }
    } else {
        throw Exception("Invalid rule!")
    }

    return result.toList()
}


fun main(filename: String) {
    var solution = 0
    var ingestRules = true

    var rules = emptyMap<Int, Rule>()
    var words = emptyList<String>()

    val charPattern = Pattern.compile(" \"(\\p{Alnum}+)\"")

    File(filename).readLines().forEach {
        if (it.length == 0) {
            ingestRules = false

            words = buildRuleBase(0, rules)

        } else if (ingestRules) {
            val (ruleIdString, rulePayload) = it.split(":")
            val ruleId = ruleIdString.toInt()

            val matcher = charPattern.matcher(rulePayload)

            val rule = if (matcher.matches()) {
                val charToMatch = matcher.group(1)[0]
                Rule(char = charToMatch, refs = null)
            } else {
                val ruleRefs = rulePayload.split("|").map { rp -> rp.split(" ") }
                    .map { rpl -> rpl.map { it.toIntOrNull() }.filterNotNull() }
                Rule(char = null, refs = ruleRefs)
            }

            rules = rules.plus(Pair(ruleId, rule))

        } else {
            if (words.contains(it)) {
                solution += 1
            }
        }
    }

    println(solution)
}

main("first.in")
