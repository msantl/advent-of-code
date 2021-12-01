import java.io.File
import java.util.regex.Pattern
import java.util.regex.Matcher

class Rule(val name: String, val conditions: List<Pair<Int, Int>>) {
    fun evaluateRule(value: Int) = conditions.any { cond -> cond.first <= value && value <= cond.second }
}

fun main(filename: String) {
    val input = File(filename).readLines()
    val rulePattern = Pattern.compile("([\\p{Alpha}\\p{Space}]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)")
    val myTicket = Pattern.compile("your ticket:")
    val nearbyTicket = Pattern.compile("nearby tickets:")

    val rules = mutableListOf<Rule>()
    val validTickets = mutableListOf<List<Int>>()
    var my = emptyList<Int>()

    var mode = "rules"

    input.forEach {
        when (mode) {
            "rules" -> {
                val matcher = rulePattern.matcher(it)

                if (matcher.matches()) {
                    val name = matcher.group(1)!!
                    val x1 = matcher.group(2).toInt()
                    val x2 = matcher.group(3).toInt()
                    val y1 = matcher.group(4).toInt()
                    val y2 = matcher.group(5).toInt()

                    rules.add(Rule(name, listOf(Pair(x1, x2), Pair(y1, y2))))
                } else {
                    val myTicketMatcher = myTicket.matcher(it)
                    if (myTicketMatcher.matches()) {
                        mode = "myticket"
                    }
                }
            }
            "myticket" -> {
                val nearbyTicketMatcher = nearbyTicket.matcher(it)
                if (nearbyTicketMatcher.matches()) {
                    mode = "tickets"
                } else if (it.length > 0) {
                    my = it.split(",").map { it.toInt() }
                }
            }
            "tickets" -> {
                val ticket = it.split(",").map { it.toInt() }

                if (ticket.all { value -> rules.any { it.evaluateRule(value) } }) {
                    validTickets.add(ticket)
                }
            }
            else -> {
                throw Exception("Invalid ingestion mode")
            }
        }
    }

    val ruleToId = mutableMapOf<String, List<Int>>()

    for (validTicket in validTickets) {
        for ((i, value) in validTicket.withIndex()) {
            for (rule in rules) {
                if (rule.evaluateRule(value)) {
                    val prevList = ruleToId.getOrDefault(rule.name, emptyList())
                    ruleToId[rule.name] = prevList.plus(i)
                }
            }
        }
    }

    val mappings = mutableMapOf<String, List<Int>>()

    for ((k, v) in ruleToId) {
        val groups = v.groupBy { it }.map { (g, l) -> g to l.size }
        for ((group, size) in groups) {
            if (size == validTickets.size) {
                val prevList = mappings.getOrDefault(k, emptyList())
                mappings.put(k, prevList.plus(group))
            }
        }
    }

        val finalMappings = mutableMapOf<Int, String>()

        while (true) {
            var updated = false
            for ((k, v) in mappings) {
                val filteredList = v.filter { it -> !finalMappings.containsKey(it) }
                if (filteredList.size == 1) {
                    finalMappings[filteredList[0]] = k
                    updated = true
                }
            }
            if (!updated) {
                break
            }
        }

        val solution = finalMappings.filter { (_, v) -> v.contains("departure") }.map { (k, _) -> my[k] }
            .fold(1L) { acc, i -> acc * i }
        println(solution)
}

main("second.in")
