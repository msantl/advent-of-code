import java.io.File
import java.util.regex.Pattern
import kotlin.math.E

fun main(filename: String) {

    val allergenToIngredients = mutableMapOf<String, MutableList<Set<String>>>()
    val allIngredients = mutableSetOf<String>()

    val meals = mutableListOf<List<String>>()

    val linePattern = Pattern.compile("([\\p{Alpha}\\p{Space}]+)\\(contains ([\\p{Alpha}\\p{Space},]+)\\)")
    File(filename).readLines().forEach {
        val matcher = linePattern.matcher(it)
        val (ingrediends, allergens) = if (matcher.matches()) {
            Pair(matcher.group(1)?.split(" ")?.map { it.trim() }?.filter { it.length > 0 }
                     ?: throw Exception("invalid line $it"),
                 matcher.group(2)?.split(",")?.map { it.trim() }?.filter { it.length > 0 }
                     ?: throw Exception("invalid line $it"))
        } else {
            throw Exception("Invalid line $it")
        }

        for (allergen in allergens) {
            if (allergenToIngredients.containsKey(allergen)) {
                allergenToIngredients[allergen]?.add(ingrediends.toSet())
            } else {
                allergenToIngredients[allergen] = mutableListOf(ingrediends.toSet())
            }
        }

        allIngredients.addAll(ingrediends)
        meals.add(ingrediends)
    }

    val mapping = mutableMapOf<String, Set<String>>()

    for ((allergen, ingredientsList) in allergenToIngredients) {
        var couldBeAllergen = allIngredients.toSet()
        for (subIngrediens in ingredientsList) {
            couldBeAllergen = couldBeAllergen.intersect(subIngrediens)
        }

        mapping[allergen] = couldBeAllergen
    }


    val finalMapping = mutableMapOf<String, String>()

    while (true) {
        var found = false

        for ((alg, ing) in mapping) {
            if (finalMapping.containsKey(alg)) continue
            val ingredient = ing.filter { finalMapping.containsKey(it) == false }
            if (ingredient.size == 1) {
                finalMapping[ingredient.first()] = alg
                found = true
            }
        }

        if (!found) break
    }

    println(finalMapping.entries.sortedBy { (_, alg) -> alg }.map { (ing, _) -> ing }.joinToString(separator = ","))

}

main("second.in")
