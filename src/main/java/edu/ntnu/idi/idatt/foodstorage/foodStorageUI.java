package edu.ntnu.idi.idatt.foodstorage;

import java.util.Date;

/**
 * Represents the user interface for the FoodStorage application.
 */

public class foodStorageUI {

  /**
   * Initializes the FoodStorage application.
   */
  public void init() {
    System.out.println("Initializing FoodStorage application...");
  }

  /**
   * Starts the FoodStorage application.
   */
  public void start() {
    try {
      Ingredient milk = new Ingredient(
          "Milk", 1.75, "liter", new Date(), 33.60);
      Ingredient butter = new Ingredient(
          "Butter", 250.0, "g", new Date(), 37.70);
      Ingredient eggs = new Ingredient(
          "Eggs", 12.0, "pcs", new Date(), 40.70);

      printIngredient(milk);
      printIngredient(butter);
      printIngredient(eggs);

    } catch (IllegalArgumentException e) {
      System.out.println("Error creating ingredient:" + e.getMessage());
    }
  }

  /**
   * Prints the details of the ingredient
   *
   * @param ingredient the ingredient to print
   */
  private void printIngredient(Ingredient ingredient) {
    System.out.println(ingredient.toString());
  }
}
