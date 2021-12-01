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

    val allPossibleAllergens = mutableSetOf<String>()

    for ((_, ingredientsList) in allergenToIngredients) {
        var couldBeAllergen = allIngredients.toSet()
        for (subIngrediens in ingredientsList) {
            couldBeAllergen = couldBeAllergen.intersect(subIngrediens)
        }

        allPossibleAllergens.addAll(couldBeAllergen)
    }


    var sol = 0
    for (meal in meals) {
        sol += meal.filter { allPossibleAllergens.contains(it) == false}.size
    }
    println(sol)
}

main("test.in")
