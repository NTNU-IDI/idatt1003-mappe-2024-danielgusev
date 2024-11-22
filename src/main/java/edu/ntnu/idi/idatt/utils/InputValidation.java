package edu.ntnu.idi.idatt.utils;

import edu.ntnu.idi.idatt.foodstorage.FoodStorage;
import edu.ntnu.idi.idatt.foodstorage.Ingredient;
import edu.ntnu.idi.idatt.foodstorage.Recipe;
import java.util.Date;
import java.util.Map;

/**
 * The InputValidation class handles all input validation and exception throwing.
 */
public class InputValidation {

  public static void validateRecipeName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Recipe name cannot be null or empty.");
    }
  }

  public static void validateDescription(String description) {
    if (description == null) {
      throw new IllegalArgumentException("Description cannot be null.");
    }
  }

  public static void validateInstructions(String instructions) {
    if (instructions == null) {
      throw new IllegalArgumentException("Instructions cannot be null.");
    }
  }

  public static void validateIngredientsMap(Map<String, Double> ingredients) {
    if (ingredients == null || ingredients.isEmpty()) {
      throw new IllegalArgumentException("Ingredients cannot be null or empty.");
    }
  }

  public static void validateUnitsMap(Map<String, String> units) {
    if (units == null || units.isEmpty()) {
      throw new IllegalArgumentException("Units cannot be null or empty.");
    }
  }

  public static void validateIngredientAndUnitsSize(
      Map<String, Double> ingredients,
      Map<String, String> units) {
    if (ingredients.size() != units.size()) {
      throw new IllegalArgumentException(
          "Ingredients and unit maps must have the same size.");
    }
  }

  public static void validateIngredientQuantity(String ingredientName, Double quantity) {
    if (quantity == null || quantity <= 0) {
      throw new IllegalArgumentException(
          "Quantity for ingredient '" + ingredientName + "' must be positive.");
    }
  }

  public static void validateIngredientUnit(String ingredientName, String unit) {
    if (unit == null || unit.trim().isEmpty()) {
      throw new IllegalArgumentException(
          "Unit for ingredient '" + ingredientName + "' cannot be null or empty.");
    }
  }

  public static void validateRecipe(Recipe recipe) {
    if (recipe == null) {
      throw new IllegalArgumentException("Recipe cannot be null.");
    }
  }

  public static void validateFoodStorage(FoodStorage storage) {
    if (storage == null) {
      throw new IllegalArgumentException("FoodStorage cannot be null.");
    }
  }

  public static void validateIngredientName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Ingredient name cannot be null or empty.");
    }
  }

  public static void validateIngredientQuantity(double quantity) {
    if (quantity < 0) {
      throw new IllegalArgumentException("Ingredient quantity cannot be negative.");
    }
  }

  public static void validateIngredientUnit(String unit) {
    if (unit == null || unit.trim().isEmpty()) {
      throw new IllegalArgumentException("Ingredient unit cannot be null or empty.");
    }
  }

  public static void validatePricePerUnit(double pricePerUnit) {
    if (pricePerUnit < 0) {
      throw new IllegalArgumentException("Price per unit cannot be negative.");
    }
  }

  public static void validateBestBeforeDate(Date bestBeforeDate) {
    if (bestBeforeDate == null) {
      throw new IllegalArgumentException("Best-before date cannot be null.");
    }
  }

  public static void validateRemoveIngredientQuantity(double quantity) {
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity to remove cannot be negative.");
    }
  }

  public static void validateIngredient(Ingredient ingredient) {
    if (ingredient == null) {
      throw new IllegalArgumentException("Ingredient cannot be null.");
    }
  }
}
