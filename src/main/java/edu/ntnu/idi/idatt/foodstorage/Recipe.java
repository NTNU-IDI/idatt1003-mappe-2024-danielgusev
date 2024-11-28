package edu.ntnu.idi.idatt.foodstorage;

import edu.ntnu.idi.idatt.utils.InputValidation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The Recipe class represents a recipe with a name, description, instructions, and a list of
 * required ingredients with their quantities and units.
 */
public class Recipe {

  private final String name;
  private final String description;
  private final String instructions;
  private final Map<String, Double> ingredients; // Ingredient name to quantity
  private final Map<String, String> units;       // Ingredient name to unit

  /**
   * Constructs a Recipe with the specified parameters.
   *
   * @param name         the name of the recipe
   * @param description  a brief description of the recipe
   * @param instructions preparation instructions
   * @param ingredients  a map of ingredient names to required quantities
   * @param units        a map of ingredient names to their units
   * @throws IllegalArgumentException if any parameter is invalid
   */
  public Recipe(String name, String description, String instructions,
      Map<String, Double> ingredients, Map<String, String> units) {

    InputValidation.validateRecipeName(name);
    InputValidation.validateDescription(description);
    InputValidation.validateInstructions(instructions);
    InputValidation.validateIngredientsMap(ingredients);
    InputValidation.validateUnitsMap(units);
    InputValidation.validateIngredientAndUnitsSize(ingredients, units);

    this.name = name.trim();
    this.description = description.trim();
    this.instructions = instructions.trim();

    // Create defensive copies of the ingredient maps
    this.ingredients = new HashMap<>();
    this.units = new HashMap<>();

    for (String ingredientName : ingredients.keySet()) {
      Double quantity = ingredients.get(ingredientName);
      String unit = units.get(ingredientName);

      InputValidation.validateIngredientQuantity(ingredientName, quantity);
      InputValidation.validateIngredientUnit(ingredientName, unit);

      this.ingredients.put(ingredientName.trim().toLowerCase(), quantity);
      this.units.put(ingredientName.trim().toLowerCase(), unit.trim());
    }
  }

  /**
   * Returns the name of the recipe.
   *
   * @return the recipe name
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the description of the recipe.
   *
   * @return the recipe description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Returns the preparation instructions.
   *
   * @return the instructions
   */
  public String getInstructions() {
    return instructions;
  }

  /**
   * Returns an unmodifiable map of ingredient names to quantities.
   *
   * @return an unmodifiable map of ingredients
   */
  public Map<String, Double> getIngredients() {
    return Collections.unmodifiableMap(ingredients);
  }

  /**
   * Returns an unmodifiable map of ingredient names to units.
   *
   * @return an unmodifiable map of units
   */
  public Map<String, String> getUnits() {
    return Collections.unmodifiableMap(units);
  }

  /**
   * Checks if the recipe can be made with the ingredients available in the given FoodStorage.
   *
   * @param storage the FoodStorage to check against
   * @return true if the recipe can be made, false otherwise
   */
  public boolean canMake(FoodStorage storage) {
    InputValidation.validateFoodStorage(storage);
    for (String ingredientName : ingredients.keySet()) {
      Double requiredQuantity = ingredients.get(ingredientName);
      String requiredUnit = units.get(ingredientName);

      // Quantities and units are already in standard units
      Ingredient ingredient = storage.findIngredientByNameAndUnit(ingredientName, requiredUnit);

      if (ingredient == null || ingredient.getQuantity() < requiredQuantity) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns a map of missing ingredients and their required quantities.
   *
   * @param storage the FoodStorage to check against
   * @return a map of missing ingredient names to required quantities
   */
  public Map<String, Double> getMissingIngredients(FoodStorage storage) {
    InputValidation.validateFoodStorage(storage);
    Map<String, Double> missingIngredients = new HashMap<>();

    for (String ingredientName : ingredients.keySet()) {
      Double requiredQuantity = ingredients.get(ingredientName);
      String requiredUnit = units.get(ingredientName);

      Ingredient ingredient = storage.findIngredientByNameAndUnit(ingredientName, requiredUnit);

      if (ingredient == null) {
        missingIngredients.put(ingredientName, requiredQuantity);
      } else if (ingredient.getQuantity() < requiredQuantity) {
        double missingQuantity = requiredQuantity - ingredient.getQuantity();
        missingIngredients.put(ingredientName, missingQuantity);
      }
    }

    return missingIngredients;
  }

  @Override
  public String toString() {
    return "Recipe: " + name
        + "\nDescription: " + description
        + "\nInstructions: " + instructions;
  }
}
