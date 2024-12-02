package edu.ntnu.idi.idatt.foodstorage;

import edu.ntnu.idi.idatt.utils.InputValidation;
import edu.ntnu.idi.idatt.utils.UnitConverter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The FoodStorage class manages a collection of Ingredient instances using a HashMap. It allows
 * adding, removing, searching, and listing ingredients efficiently.
 */
public class FoodStorage {

  /**
   * Map of ingredient keys to Ingredient instances.
   */
  private final Map<String, Ingredient> ingredientMap;

  /**
   * Date formatter for key generation.
   */
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

  /**
   * Constructs an empty FoodStorage.
   */
  public FoodStorage() {
    this.ingredientMap = new HashMap<>();
  }

  /**
   * Generates a unique key for an ingredient based on its attributes.
   *
   * @param ingredient the ingredient for which to generate the key
   * @return the unique key as a string
   */
  private String generateKey(Ingredient ingredient) {
    return generateKey(
        ingredient.getName(),
        ingredient.getUnit(),
        ingredient.getPricePerUnit(),
        ingredient.getBestBeforeDate()
    );
  }

  /**
   * Generates a unique key based on the provided attributes.
   *
   * @param name           the name of the ingredient
   * @param unit           the unit of measurement
   * @param pricePerUnit   the price per unit
   * @param bestBeforeDate the best-before date
   * @return the unique key as a string
   */
  private String generateKey(String name, String unit, double pricePerUnit, Date bestBeforeDate) {
    String dateStr = DATE_FORMAT.format(bestBeforeDate);
    return String.format("%s-%s-%.2f-%s",
        name.trim().toLowerCase(),
        unit.trim().toLowerCase(),
        pricePerUnit,
        dateStr);
  }

  /**
   * Adds an ingredient to the storage. If an ingredient with the same key already exists, it
   * increases the quantity.
   *
   * @param ingredient the ingredient to add
   * @throws IllegalArgumentException if ingredient is null
   */
  public void addIngredient(Ingredient ingredient) {
    InputValidation.validateIngredient(ingredient);

    String key = generateKey(ingredient);

    if (ingredientMap.containsKey(key)) {
      // Increase the quantity of the existing ingredient
      Ingredient existingIngredient = ingredientMap.get(key);
      double newQuantity = existingIngredient.getQuantity() + ingredient.getQuantity();
      existingIngredient.setQuantity(newQuantity);
    } else {
      // Add the new ingredient
      ingredientMap.put(key, ingredient);
    }
  }

  /**
   * Removes a specified quantity of an ingredient.
   *
   * @param name           the name of the ingredient
   * @param unit           the unit of measurement
   * @param pricePerUnit   the price per unit
   * @param bestBeforeDate the best-before date
   * @param quantity       the quantity to remove
   * @throws IllegalArgumentException if quantity is negative, ingredient not found, or insufficient
   *                                  quantity
   */
  public void removeIngredient(String name, String unit, double pricePerUnit,
      Date bestBeforeDate, double quantity) {
    InputValidation.validateRemoveIngredientQuantity(quantity);
    InputValidation.validateIngredientName(name);
    InputValidation.validateIngredientUnit(unit);
    InputValidation.validateBestBeforeDate(bestBeforeDate);

    String key = generateKey(name, unit, pricePerUnit, bestBeforeDate);

    Ingredient ingredient = ingredientMap.get(key);
    if (ingredient == null) {
      throw new IllegalArgumentException("Ingredient not found in storage.");
    }

    double currentQuantity = ingredient.getQuantity();
    if (currentQuantity < quantity) {
      throw new IllegalArgumentException("Not enough quantity to remove.");
    }

    ingredient.setQuantity(currentQuantity - quantity);

    // Remove ingredient if quantity is zero
    if (ingredient.getQuantity() == 0) {
      ingredientMap.remove(key);
    }
  }


  /**
   * Searches for an ingredient by its attributes.
   *
   * @param name           the name of the ingredient
   * @param unit           the unit of measurement
   * @param pricePerUnit   the price per unit
   * @param bestBeforeDate the best-before date
   * @return the ingredient if found, null otherwise
   */
  public Ingredient searchIngredient(String name, String unit, double pricePerUnit,
      Date bestBeforeDate) {
    if (name == null || name.trim().isEmpty()) {
      return null;
    }
    if (unit == null || unit.trim().isEmpty()) {
      return null;
    }
    if (bestBeforeDate == null) {
      return null;
    }

    String key = generateKey(name, unit, pricePerUnit, bestBeforeDate);
    return ingredientMap.get(key);
  }

  /**
   * This is for finding the ingredient without using some parameters.
   *
   * @param name name of the ingredient in the recipe
   * @param unit unit of the ingredient in the recipe
   * @return returns the desired ingredient
   */
  public Ingredient findIngredientByNameAndUnit(String name, String unit) {
    if (name == null || unit == null) {
      return null;
    }

    String trimmedName = name.trim().toLowerCase();
    String standardUnit = UnitConverter.getStandardUnit(unit);

    for (Ingredient ingredient : ingredientMap.values()) {
      if (ingredient.getName().trim().toLowerCase().equals(trimmedName)
          && ingredient.getUnit().equalsIgnoreCase(standardUnit) && !ingredient.isExpired()) {
        return ingredient;
      }
    }
    return null;
  }

  /**
   * This is mainly for the search ability in the user menu.
   *
   * @param name name of the ingredient in the recipe
   * @param unit unit of the ingredient in the recipe
   * @return returns the desired ingredient
   */

  public Ingredient findIngredientWithExpired(String name, String unit) {
    if (name == null || unit == null) {
      return null;
    }

    String trimmedName = name.trim().toLowerCase();
    String standardUnit = UnitConverter.getStandardUnit(unit);

    for (Ingredient ingredient : ingredientMap.values()) {
      if (ingredient.getName().trim().toLowerCase().equals(trimmedName)
          && ingredient.getUnit().equalsIgnoreCase(standardUnit)) {
        return ingredient;
      }
    }
    return null;
  }


  /**
   * Returns a list of all ingredients in the storage.
   *
   * @return an array of all ingredients
   */
  public Ingredient[] listAllIngredients() {
    return ingredientMap.values().toArray(new Ingredient[0]);
  }

  /**
   * Returns a list of all expired ingredients.
   *
   * @return an array of expired ingredients
   */
  public Ingredient[] listExpiredIngredients() {
    return ingredientMap.values().stream()
        .filter(Ingredient::isExpired)
        .toArray(Ingredient[]::new);
  }

  /**
   * Calculates the total value of all ingredients in the storage.
   *
   * @return the total value in NOK
   */
  public double calculateTotalValue() {
    return ingredientMap.values().stream()
        .mapToDouble(Ingredient::getTotalValue)
        .sum();
  }

  /**
   * Calculates the total value of expired ingredients.
   *
   * @return the total value of expired ingredients in NOK
   */
  public double calculateExpiredIngredientsValue() {
    return ingredientMap.values().stream()
        .filter(Ingredient::isExpired)
        .mapToDouble(Ingredient::getTotalValue)
        .sum();
  }
}
