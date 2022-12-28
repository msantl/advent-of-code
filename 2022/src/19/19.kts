package `2022_19`

import java.io.File

data class Blueprint(
    val id: Int,
    val prices: List<Pair<String, List<Pair<String, Int>>>>
)

fun bfs(
    blueprint: Blueprint,
    robots: Map<String, Int>,
    resources: Map<String, Int>,
    time: Int
): Int {
    val maxCost: MutableMap<String, Int> = mutableMapOf<String, Int>()
    for (type in blueprint.prices.map { it.first }) {
        var cost: Int = Int.MIN_VALUE
        for (needs in blueprint.prices.map { it.second }) {
            for ((typeNeed, typeCost) in needs) {
                if (typeNeed == type) cost = Math.max(cost, typeCost)
            }
        }
        if (cost != Int.MIN_VALUE) {
            maxCost.put(type, cost)
        }
    }

    var sol: Int = 0

    val queue: MutableList<Triple<Map<String, Int>, Map<String, Int>, Int>> =
        mutableListOf<Triple<Map<String, Int>, Map<String, Int>, Int>>()

    val bio: MutableMap<String, Boolean> = mutableMapOf<String, Boolean>()

    queue.add(Triple(robots, resources, time))

    var key = robots.toString() + resources.toString() + time.toString()
    bio[key] = true

    while (!queue.isEmpty()) {
        val (currRobots, currResources, currTime) = queue.removeAt(0)

        val currGeode = currResources.getOrDefault("geode", 0)
        val currGeodeRobots = currRobots.getOrDefault("geode", 0)

        if (currTime == 0) {
            sol = Math.max(sol, currGeode)
            continue
        }

        if (currGeode + currGeodeRobots * currTime + currTime * (currTime - 1) / 2 <= sol) {
            // even if we create a new one every minute, we can't hit current best
            continue
        }

        // Don't build robots in the last step
        if (currTime > 1) {
            for (canBuild in blueprint.prices) {
                // try building one of the robots

                val robotType = canBuild.first
                // don't create a robot if we have enough of that resource
                if (robotType != "geode" &&
                    (
                      currResources.getOrDefault(robotType, 0) >= currTime * maxCost.getOrDefault(robotType, 0) ||
                      currRobots.getOrDefault(robotType, 0) >= maxCost.getOrDefault(robotType, 0)
                    )
                ) {
                    continue
                }

                val newResources: MutableMap<String, Int> = mutableMapOf<String, Int>()
                val newRobots: MutableMap<String, Int> = currRobots.toMutableMap()
                val newTime = currTime - 1

                for ((type, count) in currResources) {
                    if (type == "geode") {
                        newResources.put(type, count)
                    } else {
                        val minNeeded = Math.min(
                            count,
                            currTime * maxCost.getOrDefault(type, 0) - currRobots.getOrDefault(type, 0) * newTime
                        )

                        newResources.put(type, minNeeded)
                    }
                }

                var enoughResources: Boolean = true

                for ((costType, costPrice) in canBuild.second) {
                    if (newResources.getOrDefault(costType, 0) < costPrice) {
                        enoughResources = false
                        break
                    }
                    // Reduce the resources for the cost of the new robot
                    newResources.put(costType, newResources.getOrDefault(costType, 0) - costPrice)
                }

                if (enoughResources) {
                    newRobots.put(robotType, newRobots.getOrDefault(robotType, 0) + 1)

                    // Add what others have mined
                    for ((robot, count) in currRobots) {
                        newResources.put(robot, newResources.getOrDefault(robot, 0) + count)
                    }

                    key = newRobots.toString() + newResources.toString() + newTime.toString()
                    if (key !in bio) {
                        queue.add(0, Triple(newRobots.toMap(), newResources.toMap(), newTime))
                        bio.put(key, true)
                    }
                }
            }
        }

        // spend the time mining
        val newResources: MutableMap<String, Int> = mutableMapOf<String, Int>()
        val newRobots: MutableMap<String, Int> = currRobots.toMutableMap()
        val newTime = currTime - 1

        for ((type, count) in currResources) {
            if (type == "geode") {
                newResources.put(type, count)
            } else {
                val minNeeded = Math.min(
                    count,
                    currTime * maxCost.getOrDefault(type, 0) - currRobots.getOrDefault(type, 0) * newTime
                )

                newResources.put(type, minNeeded)
            }
        }

        for ((robot, count) in currRobots) {
            newResources.put(robot, newResources.getOrDefault(robot, 0) + count)
        }

        key = newRobots.toString() + newResources.toString() + newTime.toString()
        if (key !in bio) {
            queue.add(0, Triple(newRobots.toMap(), newResources.toMap(), newTime))
            bio.put(key, true)
        }

    }

    return sol
}

fun getBlueprints(filename: String): List<Blueprint> {
    val input: List<String> = File(filename).readLines().plus("")
    val bluePrintRegex = Regex("Blueprint ([0-9]+):")
    val rowRegex = Regex("Each ([a-z]+) robot costs ([\\d+ \\w+ ?a?n?d? ?]+)\\.")

    val blueprints: MutableList<Blueprint> = mutableListOf<Blueprint>()
    var currentBlueprint: Int? = null
    var prices: MutableList<Pair<String, List<Pair<String, Int>>>> =
        mutableListOf<Pair<String, List<Pair<String, Int>>>>()

    for (line in input) {
        val bluePrintMatch = bluePrintRegex.find(line)
        val rowRegex = rowRegex.find(line)

        if (bluePrintMatch != null) {
            if (currentBlueprint != null) {
                blueprints.add(Blueprint(currentBlueprint, prices.toList()))
            }
            prices.clear()

            val (id) = bluePrintMatch.destructured
            currentBlueprint = id.toInt()

        } else if (rowRegex != null) {
            val (type, requirements) = rowRegex.destructured

            val pricesForType: MutableList<Pair<String, Int>> = mutableListOf<Pair<String, Int>>()
            for (price in requirements.split(" and ")) {
                val elems = price.split(" ")
                pricesForType.add(Pair(elems[1], elems[0].toInt()))
            }
            prices.add(Pair(type, pricesForType))

        } else {
            if (currentBlueprint != null) {
                blueprints.add(Blueprint(currentBlueprint, prices.toList()))
            }
            currentBlueprint = null
            prices.clear()
        }
    }

    return blueprints
}

fun first(filename: String) {
    val blueprints = getBlueprints(filename)

    var sum = 0
    for (blueprint in blueprints) {
        val sol = bfs(blueprint, mapOf<String, Int>("ore" to 1), mapOf<String, Int>(), 24)
        println("Blueprint ${blueprint.id} yielded $sol")
        sum += blueprint.id * sol
    }
    println(sum)
}


first("test.in") // 33
first("first.in")

fun second(filename: String) {
    val blueprints = getBlueprints(filename)

    var sum = 1
    for (i in 0..Math.min(2, blueprints.size - 1)) {
        val blueprint = blueprints[i]
        val sol = bfs(blueprint, mapOf<String, Int>("ore" to 1), mapOf<String, Int>(), 32)
        println("Blueprint ${blueprint.id} yielded $sol")
        sum *= sol
    }

    println(sum)
}

second("test.in") // 56 * 62
second("first.in")
