// Cookbook.java

package edu.ntnu.idi.idatt.foodstorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Cookbook class manages a list of recipes.
 */
public class Cookbook {

  private final List<Recipe> recipes;

  /**
   * Constructs an empty Cookbook.
   */
  public Cookbook() {
    this.recipes = new ArrayList<>();
  }

  /**
   * Adds a recipe to the cookbook.
   *
   * @param recipe the recipe to add
   * @throws IllegalArgumentException if recipe is null or already exists
   */
  public void addRecipe(Recipe recipe) {
    if (recipe == null) {
      throw new IllegalArgumentException("Recipe cannot be null.");
    }
    if (getRecipeByName(recipe.getName()) != null) {
      throw new IllegalArgumentException("Recipe already exists in the cookbook.");
    }
    recipes.add(recipe);
  }

  /**
   * Retrieves a recipe by name.
   *
   * @param name the name of the recipe
   * @return the recipe if found, null otherwise
   */
  public Recipe getRecipeByName(String name) {
    if (name == null || name.trim().isEmpty()) {
      return null;
    }

    String trimmedName = name.trim().toLowerCase();
    for (Recipe recipe : recipes) {
      if (recipe.getName().trim().toLowerCase().equals(trimmedName)) {
        return recipe;
      }
    }
    return null;
  }

  /**
   * Suggests recipes that can be made with the ingredients in the given FoodStorage.
   *
   * @param storage the FoodStorage to check against
   * @return a list of recipes that can be made
   */
  public List<Recipe> suggestRecipes(foodStorage storage) {
    if (storage == null) {
      throw new IllegalArgumentException("FoodStorage cannot be null.");
    }

    List<Recipe> suggestedRecipes = new ArrayList<>();
    for (Recipe recipe : recipes) {
      if (recipe.canMake(storage)) {
        suggestedRecipes.add(recipe);
      }
    }
    return suggestedRecipes;
  }

  /**
   * Returns an unmodifiable list of all recipes in the cookbook.
   *
   * @return a list of recipes
   */
  public List<Recipe> getAllRecipes() {
    return Collections.unmodifiableList(recipes);
  }
}
