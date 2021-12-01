import java.io.File
import java.util.regex.Pattern
import java.util.regex.Matcher

class Rule(val conditions: List<Pair<Int, Int>>) {
    fun evaluateRule(value: Int) = conditions.any { cond -> cond.first <= value && value <= cond.second }
}

fun main(filename: String) {
    val input = File(filename).readLines()
    val rulePattern = Pattern.compile("(\\p{Alpha}+): (\\d+)-(\\d+) or (\\d+)-(\\d+)")
    val myTicket = Pattern.compile("your ticket:")
    val nearbyTicket = Pattern.compile("nearby tickets:")

    val rules = mutableListOf<Rule>()

    var mode = "rules"
    var solution = 0

    input.forEach {
        when (mode) {
            "rules" -> {
                val matcher = rulePattern.matcher(it)

                if (matcher.matches()) {
                    val x1 = matcher.group(2).toInt()
                    val x2 = matcher.group(3).toInt()
                    val y1 = matcher.group(4).toInt()
                    val y2 = matcher.group(5).toInt()

                    rules.add(Rule(listOf(Pair(x1, x2), Pair(y1, y2))))
                } else {
                    val myTicketMatcher = myTicket.matcher(it)
                    if (myTicketMatcher.matches()) {
                        mode = "myticket"
                    }
                }
            }
            "myticket" -> {
                // do nothing for now
                val nearbyTicketMatcher = nearbyTicket.matcher(it)
                if (nearbyTicketMatcher.matches()) {
                    mode = "tickets"
                }
            }
            "tickets" -> {
                val ticket = it.split(",").map { it.toInt() }

                ticket.forEach { value ->
                    if (!rules.any { it.evaluateRule(value) }) {
                        solution += value
                    }
                }
            }
            else -> {
                throw Exception("Invalid ingestion mode")
            }
        }
    }

    println(solution)
}

main("first.in")
