package edu.ntnu.idi.idatt.utils;

import edu.ntnu.idi.idatt.foodstorage.FoodStorage;
import edu.ntnu.idi.idatt.foodstorage.Ingredient;
import edu.ntnu.idi.idatt.foodstorage.Recipe;
import java.util.Date;
import java.util.Map;

/**
 * Handles all input validation and exception throwing for the application.
 */
public class InputValidation {

  /**
   * Validates the recipe name.
   *
   * @param name the name of the recipe
   * @throws IllegalArgumentException if the name is null or empty
   */
  public static void validateRecipeName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Recipe name cannot be null or empty.");
    }
  }

  /**
   * Validates the recipe description.
   *
   * @param description the description of the recipe
   * @throws IllegalArgumentException if the description is null
   */
  public static void validateDescription(String description) {
    if (description == null) {
      throw new IllegalArgumentException("Description cannot be null.");
    }
  }

  /**
   * Validates the recipe instructions.
   *
   * @param instructions the instructions of the recipe
   * @throws IllegalArgumentException if the instructions are null
   */
  public static void validateInstructions(String instructions) {
    if (instructions == null) {
      throw new IllegalArgumentException("Instructions cannot be null.");
    }
  }

  /**
   * Validates the ingredients map.
   *
   * @param ingredients a map of ingredient names to quantities
   * @throws IllegalArgumentException if the map is null or empty
   */
  public static void validateIngredientsMap(Map<String, Double> ingredients) {
    if (ingredients == null || ingredients.isEmpty()) {
      throw new IllegalArgumentException("Ingredients cannot be null or empty.");
    }
  }

  /**
   * Validates the units map.
   *
   * @param units a map of ingredient names to units
   * @throws IllegalArgumentException if the map is null or empty
   */
  public static void validateUnitsMap(Map<String, String> units) {
    if (units == null || units.isEmpty()) {
      throw new IllegalArgumentException("Units cannot be null or empty.");
    }
  }

  /**
   * Validates that the sizes of the ingredients and units maps are equal.
   *
   * @param ingredients the ingredients map
   * @param units       the units map
   * @throws IllegalArgumentException if the sizes of the maps are not equal
   */
  public static void validateIngredientAndUnitsSize(
      Map<String, Double> ingredients,
      Map<String, String> units) {
    if (ingredients.size() != units.size()) {
      throw new IllegalArgumentException(
          "Ingredients and unit maps must have the same size.");
    }
  }

  /**
   * Validates the quantity of an ingredient in a recipe.
   *
   * @param ingredientName the name of the ingredient
   * @param quantity       the quantity required
   * @throws IllegalArgumentException if the quantity is null or not positive
   */
  public static void validateIngredientQuantity(String ingredientName, Double quantity) {
    if (quantity == null || quantity <= 0) {
      throw new IllegalArgumentException(
          "Quantity for ingredient '" + ingredientName + "' must be positive.");
    }
  }

  /**
   * Validates the quantity of an ingredient.
   *
   * @param quantity the quantity of the ingredient
   * @throws IllegalArgumentException if the quantity is negative
   */
  public static void validateIngredientQuantity(double quantity) {
    if (quantity < 0) {
      throw new IllegalArgumentException("Ingredient quantity cannot be negative.");
    }
  }

  /**
   * Validates the unit of an ingredient.
   *
   * @param unit the unit of measurement
   * @throws IllegalArgumentException if the unit is null or empty
   */
  public static void validateIngredientUnit(String unit) {
    if (unit == null || unit.trim().isEmpty()) {
      throw new IllegalArgumentException("Ingredient unit cannot be null or empty.");
    }
  }

  /**
   * Validates the unit of an ingredient in a recipe.
   *
   * @param ingredientName the name of the ingredient
   * @param unit           the unit of measurement
   * @throws IllegalArgumentException if the unit is null or empty
   */
  public static void validateIngredientUnit(String ingredientName, String unit) {
    if (unit == null || unit.trim().isEmpty()) {
      throw new IllegalArgumentException(
          "Unit for ingredient '" + ingredientName + "' cannot be null or empty.");
    }
  }

  /**
   * Validates that a recipe object is not null.
   *
   * @param recipe the Recipe object to validate
   * @throws IllegalArgumentException if the recipe is null
   */
  public static void validateRecipe(Recipe recipe) {
    if (recipe == null) {
      throw new IllegalArgumentException("Recipe cannot be null.");
    }
  }

  /**
   * Validates that a FoodStorage object is not null.
   *
   * @param storage the FoodStorage object to validate
   * @throws IllegalArgumentException if the storage is null
   */
  public static void validateFoodStorage(FoodStorage storage) {
    if (storage == null) {
      throw new IllegalArgumentException("FoodStorage cannot be null.");
    }
  }

  /**
   * Validates the name of an ingredient.
   *
   * @param name the name of the ingredient
   * @throws IllegalArgumentException if the name is null or empty
   */
  public static void validateIngredientName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Ingredient name cannot be null or empty.");
    }
  }

  /**
   * Validates the price per unit of an ingredient.
   *
   * @param pricePerUnit the price per unit
   * @throws IllegalArgumentException if the price is negative
   */
  public static void validatePricePerUnit(double pricePerUnit) {
    if (pricePerUnit < 0) {
      throw new IllegalArgumentException("Price cannot be negative.");
    }
  }

  /**
   * Validates the best-before date of an ingredient.
   *
   * @param bestBeforeDate the best-before date
   * @throws IllegalArgumentException if the date is null
   */
  public static void validateBestBeforeDate(Date bestBeforeDate) {
    if (bestBeforeDate == null) {
      throw new IllegalArgumentException("Best-before date cannot be null.");
    }
  }

  /**
   * Validates the quantity to remove when removing an ingredient.
   *
   * @param quantity the quantity to remove
   * @throws IllegalArgumentException if the quantity is negative
   */
  public static void validateRemoveIngredientQuantity(double quantity) {
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity to remove cannot be negative.");
    }
  }

  /**
   * Validates that an Ingredient object is not null.
   *
   * @param ingredient the Ingredient object to validate
   * @throws IllegalArgumentException if the ingredient is null
   */
  public static void validateIngredient(Ingredient ingredient) {
    if (ingredient == null) {
      throw new IllegalArgumentException("Ingredient cannot be null.");
    }
  }
}
