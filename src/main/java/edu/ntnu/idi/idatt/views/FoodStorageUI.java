package edu.ntnu.idi.idatt.views;

import edu.ntnu.idi.idatt.foodstorage.Cookbook;
import edu.ntnu.idi.idatt.foodstorage.FoodStorage;
import edu.ntnu.idi.idatt.foodstorage.Ingredient;
import edu.ntnu.idi.idatt.foodstorage.Recipe;
import edu.ntnu.idi.idatt.utils.UnitConverter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * Represents the user interface for the FoodStorage application.
 */

public class FoodStorageUI {

  private FoodStorage storage;
  private Cookbook cookbook;
  private Scanner scanner;
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");


  /**
   * Initializes the FoodStorage application.
   */
  public void init() {

    System.out.println("Initializing FoodStorage application...");
    storage = new FoodStorage();
    cookbook = new Cookbook();
    scanner = new Scanner(System.in);

    try {
      preloadIngredients();
      preloadRecipes();
    } catch (ParseException e) {
      System.out.println("Error preloading data: " + e.getMessage());
    }
  }


  /**
   * Starts the FoodStorage application.
   */
  public void start() {
    boolean exit = false;
    while (!exit) {
      printMenu();
      System.out.print("Enter your choice: ");
      int choice = scanner.nextInt();
      switch (choice) {
        case 1:
          addIngredient();
          break;
        case 2:
          removeIngredient();
          break;
        case 3:
          searchIngredient();
          break;
        case 4:
          listAllIngredients();
          break;
        case 5:
          listAllExpiredIngredients();
          break;
        case 6:
          calculateTotalValue();
          break;
        case 7:
          calculateExpiredValue();
          break;
        case 8:
          addRecipe();
          break;
        case 9:
          suggestRecipes();
          break;
        case 10:
          exit = true;
          System.out.println("Exiting the program goodbye!");
          break;
        default:
          System.out.println("Invalid choice. Try again.");
      }
    }
    scanner.close();
  }

  private void printMenu() {
    System.out.println("""
        
        FoodStorage Application Menu:
        1. Add Ingredient
        2. Remove Ingredient
        3. Search Ingredient
        4. List All Ingredients
        5. List Expired Ingredients
        6. Calculate Total Value
        7. Calculate Total Expired Value
        8. Add Recipe
        9. Suggest Recipes
        10. Exit
        """);
  }


  private void addIngredient() {
    try {
      System.out.println("Enter ingredient name: ");
      String name = scanner.next().trim().toLowerCase();

      System.out.println("Enter ingredient unit: ");
      String unit = scanner.next().trim().toLowerCase();

      System.out.println("Enter ingredient quantity: ");
      double quantity = getDoubleInput();

      // Convert quantity to standard units
      double standardQuantity = UnitConverter.convertToStandardUnits(quantity, unit);
      String standardUnit = UnitConverter.getStandardUnit(unit);

      System.out.println("Enter best-before date: ");
      Date bestBeforeDate = DATE_FORMAT.parse(scanner.next().trim().toLowerCase());

      System.out.println("Enter price per unit: ");
      double pricePerUnit = getDoubleInput();

      Ingredient ingredient = new Ingredient(name, standardQuantity, standardUnit, bestBeforeDate,
          pricePerUnit);
      storage.addIngredient(ingredient);
      System.out.println("Ingredient successfully added!");
    } catch (ParseException e) {
      System.out.println("Invalid date format. Please enter date in dd.MM.yyyy format.");
    } catch (IllegalArgumentException e) {
      System.out.println("Error adding ingredient: " + e.getMessage());
    }
  }


  private void removeIngredient() {
    try {
      System.out.println("Enter ingredient name: ");

      final String name = scanner.next().trim().toLowerCase();

      System.out.println("Enter unit: ");
      String unit = scanner.next().trim().toLowerCase();

      String standardUnit = UnitConverter.getStandardUnit(unit);

      System.out.println("Enter price per unit: ");
      double pricePerUnit = getDoubleInput();

      System.out.println("Enter best-before date (dd.MM.yyyy): ");
      Date bestBeforeDate = DATE_FORMAT.parse(scanner.next().trim());

      System.out.println("Enter quantity to remove: ");
      double quantity = getDoubleInput();

      double standardQuantity = UnitConverter.convertToStandardUnits(quantity, unit);

      storage.removeIngredient(name, standardUnit, pricePerUnit, bestBeforeDate, standardQuantity);
      System.out.println("Ingredient successfully removed!");
    } catch (ParseException e) {
      System.out.println("Invalid date format. Please enter date in dd.MM.yyyy format.");
    } catch (IllegalArgumentException e) {
      System.out.println("Error removing ingredient: " + e.getMessage());
    }
  }


  private void searchIngredient() {

    try {
      System.out.println("Enter ingredient name: ");
      String name = scanner.next();

      System.out.println("Enter unit: ");
      String unit = scanner.next();

      String standardUnit = UnitConverter.getStandardUnit(unit);

      System.out.println("Enter price per unit: ");
      double pricePerUnit = getDoubleInput();

      System.out.println("Enter best-before date (dd.MM.yyyy): ");
      Date bestBeforeDate = DATE_FORMAT.parse(scanner.nextLine());

      Ingredient ingredient = storage.searchIngredient(name, standardUnit, pricePerUnit,
          bestBeforeDate);
      if (ingredient != null) {
        printIngredient(ingredient);
      } else {
        System.out.println("Ingredient not found");
      }
    } catch (ParseException e) {
      System.out.println("Invalid date format. Please enter date in dd.MM.yyyy format.");
    }
  }

  private void listAllIngredients() {
    Ingredient[] ingredients = storage.listAllIngredients();
    if (ingredients.length == 0) {
      System.out.println("No ingredients in storage.");
    } else {
      Arrays.stream(ingredients)
          .sorted(Comparator
              .comparing(Ingredient::getName, String.CASE_INSENSITIVE_ORDER)
              .thenComparing(Ingredient::getBestBeforeDate))
          .forEach(this::printIngredient);

    }
  }

  private void listAllExpiredIngredients() {
    Ingredient[] expiredIngredients = storage.listAllIngredients();
    if (expiredIngredients.length == 0) {
      System.out.println("No expired ingredients in storage");
    } else {
      Arrays.stream(expiredIngredients)
          .sorted(Comparator
              .comparing(Ingredient::getName, String.CASE_INSENSITIVE_ORDER)
              .thenComparing(Ingredient::getBestBeforeDate))
          .forEach(this::printIngredient);

    }
  }

  private void calculateTotalValue() {
    double totalValue = storage.calculateTotalValue();
    System.out.printf("\"Total value of all ingredients: %.2f%n\"", totalValue);
  }

  private void calculateExpiredValue() {
    double expiredValue = storage.calculateExpiredIngredientsValue();
    System.out.printf("\"Value of all expired ingredients: %.2f%n\"", expiredValue);
  }

  private void addRecipe() {
    try {
      System.out.println("Enter recipe name: ");
      final String name = scanner.next().trim().toLowerCase();

      System.out.println("Enter description: ");
      final String description = scanner.nextLine();

      System.out.println("Enter instructions: ");
      final String instructions = scanner.nextLine();

      Map<String, Double> ingredients = new HashMap<>();
      Map<String, String> units = new HashMap<>();

      System.out.println("Enter number of ingredients: ");
      int numIngredients = scanner.nextInt();

      IntStream.range(0, numIngredients).forEach(i -> {
        System.out.printf("Ingredient %d name: ", i + 1);
        String ingredientName = scanner.next().trim().toLowerCase();

        System.out.printf("Ingredient %d quantity: ", i + 1);
        double quantity = getDoubleInput();

        System.out.printf("Ingredient %d unit: ", i + 1);
        String unit = scanner.next().trim().toLowerCase();

        // Convert to standard units
        double standardQuantity = UnitConverter.convertToStandardUnits(quantity, unit);
        String standardUnit = UnitConverter.getStandardUnit(unit);

        ingredients.put(ingredientName, standardQuantity);
        units.put(ingredientName, standardUnit);
      });

      Recipe recipe = new Recipe(name, description, instructions, ingredients, units);
      cookbook.addRecipe(recipe);
      System.out.println("Recipe successfully added!");
    } catch (IllegalArgumentException e) {
      System.out.println("Error adding ingredient: " + e.getMessage());
    }
  }

  private void suggestRecipes() {
    List<Recipe> suggestedRecipes = cookbook.suggestRecipes(storage);
    if (suggestedRecipes.isEmpty()) {
      System.out.println("No recipes can be made with current ingredients.");
    } else {
      System.out.println("Recipes you can make:");
      suggestedRecipes.forEach(recipe -> System.out.println(recipe.getName()));
    }

  }


  private int getIntInput() {
    while (!scanner.hasNextInt()) {
      System.out.print("Invalid input. Please enter a number: ");
      scanner.next();
    }
    int input = scanner.nextInt();
    scanner.nextLine(); // Consume newline
    return input;
  }

  private double getDoubleInput() {
    while (!scanner.hasNextDouble()) {
      System.out.print("Invalid input. Please enter a number: ");
      scanner.next();
    }
    double input = scanner.nextDouble();
    scanner.nextLine(); // Consume newline
    return input;
  }


  private void printIngredient(Ingredient ingredient) {
    System.out.println(ingredient.toString());
  }

  private void preloadIngredients() throws ParseException {
    Date butterBestBefore = DATE_FORMAT.parse("15.12.2024");
    Date milkBestBefore = DATE_FORMAT.parse("10.12.2024");
    Date creamBestBefore = DATE_FORMAT.parse("05.12.2024");
    Date potatoBestBefore = DATE_FORMAT.parse("20.01.2025");
    Date saltBestBefore = DATE_FORMAT.parse("31.12.2026");
    Date pepperBestBefore = DATE_FORMAT.parse("20.06.2025");

    // Create ingredients with individual best-before dates
    Ingredient butter = new Ingredient(
        "Butter", 0.5, "kilogram", butterBestBefore, 50.0);
    Ingredient milk = new Ingredient(
        "Milk", 2.0, "liter", milkBestBefore, 20.0);
    Ingredient cream = new Ingredient(
        "Cream", 1.0, "liter", creamBestBefore, 25.0);
    Ingredient potato = new Ingredient(
        "Potato", 5.0, "kilogram", potatoBestBefore, 15.0);
    Ingredient salt = new Ingredient(
        "Salt", 0.5, "kilogram", saltBestBefore, 5.0);
    Ingredient pepper = new Ingredient(
        "Pepper", 0.2, "kilogram", pepperBestBefore, 10.0);

    // Add ingredients to storage
    storage.addIngredient(butter);
    storage.addIngredient(milk);
    storage.addIngredient(cream);
    storage.addIngredient(potato);
    storage.addIngredient(salt);
    storage.addIngredient(pepper);

    System.out.println("Preloaded ingredients into storage.");
  }

  private void preloadRecipes() {
    // Recipe 1: Mashed Potatoes
    Map<String, Double> mashedPotatoesIngredients = new HashMap<>();
    Map<String, String> mashedPotatoesUnits = new HashMap<>();

    mashedPotatoesIngredients.put("potato", 1.0);   // 1 kilogram
    mashedPotatoesUnits.put("potato", "kilogram");

    mashedPotatoesIngredients.put("milk", 0.2);     // 0.2 liters
    mashedPotatoesUnits.put("milk", "liter");

    mashedPotatoesIngredients.put("butter", 0.05);  // 0.05 kilograms
    mashedPotatoesUnits.put("butter", "kilogram");

    mashedPotatoesIngredients.put("salt", 0.005);   // 0.005 kilograms
    mashedPotatoesUnits.put("salt", "kilogram");

    mashedPotatoesIngredients.put("pepper", 0.002); // 0.002 kilograms
    mashedPotatoesUnits.put("pepper", "kilogram");

    String mashedPotatoesDescription = "Creamy mashed potatoes.";
    String mashedPotatoesInstructions =
        """
            1. Peel and cut the potatoes into chunks.
            2. Boil potatoes until tender.
            3. Drain and mash the potatoes.
            4. Stir in milk and butter.
            5. Season with salt and pepper.""";

    Recipe mashedPotatoes = new Recipe(
        "Mashed Potatoes",
        mashedPotatoesDescription,
        mashedPotatoesInstructions,
        mashedPotatoesIngredients,
        mashedPotatoesUnits
    );

    cookbook.addRecipe(mashedPotatoes);

    // Recipe 2: Creamy Potato Soup
    Map<String, Double> potatoSoupIngredients = new HashMap<>();
    Map<String, String> potatoSoupUnits = new HashMap<>();

    potatoSoupIngredients.put("potato", 0.5);       // 0.5 kilograms
    potatoSoupUnits.put("potato", "kilogram");

    potatoSoupIngredients.put("cream", 0.3);        // 0.3 liters
    potatoSoupUnits.put("cream", "liter");

    potatoSoupIngredients.put("butter", 0.02);      // 0.02 kilograms
    potatoSoupUnits.put("butter", "kilogram");

    potatoSoupIngredients.put("salt", 0.005);       // 0.005 kilograms
    potatoSoupUnits.put("salt", "kilogram");

    potatoSoupIngredients.put("pepper", 0.002);     // 0.002 kilograms
    potatoSoupUnits.put("pepper", "kilogram");

    String potatoSoupDescription = "Delicious creamy potato soup.";
    String potatoSoupInstructions =
        """
            1. Chop potatoes into small pieces.
            2. Cook potatoes in water until soft.
            3. Add cream and butter.
            4. Blend until smooth.
            5. Season with salt and pepper.""";

    Recipe potatoSoup = new Recipe(
        "Creamy Potato Soup",
        potatoSoupDescription,
        potatoSoupInstructions,
        potatoSoupIngredients,
        potatoSoupUnits
    );

    cookbook.addRecipe(potatoSoup);

    System.out.println("Preloaded recipes into cookbook.");
  }
}

