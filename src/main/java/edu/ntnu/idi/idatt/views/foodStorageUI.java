package edu.ntnu.idi.idatt.views;

import edu.ntnu.idi.idatt.foodstorage.Cookbook;
import edu.ntnu.idi.idatt.foodstorage.Ingredient;
import edu.ntnu.idi.idatt.foodstorage.FoodStorage;
import edu.ntnu.idi.idatt.utils.UnitConverter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Represents the user interface for the FoodStorage application.
 */

public class foodStorageUI {

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
  }


  /**
   * Starts the FoodStorage application.
   */
  public void start() {
    boolean exit = false;
    while (!exit) {
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
          calculateTotalExpiredValue();
          break;
        case 8:
          addRecipe();
          break;
        case 9:
          suggestRecipe();
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


  private void addIngredient() {
    try {
      System.out.println("Enter ingredient name: ");
      String name = scanner.next();

      System.out.println("Enter ingredient quantity: ");
      double quantity = getDoubleInput();

      System.out.println("Enter ingredient unit: ");
      String unit = scanner.next();

      // Convert quantity to standard units
      double standardQuantity = UnitConverter.convertToStandardUnits(quantity, unit);
      String standardUnit = UnitConverter.getStandardUnit(unit);

      System.out.println("Enter best-before date: ");
      Date bestBeforeDate = DATE_FORMAT.parse(scanner.nextLine());

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
      String name = scanner.next();

      System.out.println("Enter unit: ");
      String unit = scanner.next();

      String standardUnit = UnitConverter.getStandardUnit(unit);

      System.out.println("Enter price per unit: ");
      double pricePerUnit = getDoubleInput();

      System.out.println("Enter best-before date (dd.MM.yyyy): ");
      Date bestBeforeDate = DATE_FORMAT.parse(scanner.nextLine());

      System.out.println("Enter quantity to remove: ");
      double quantity = getDoubleInput();

      double standardQuantity = UnitConverter.convertToStandardUnits(quantity, standardUnit);

      //Ingredient ingredient = new Ingredient(name, standardQuantity, standardUnit, bestBeforeDate, pricePerUnit);
      storage.removeIngredient(name, standardUnit, standardQuantity, bestBeforeDate, pricePerUnit);
      System.out.println("Ingredient successfully added!");
    } catch (ParseException e) {
      System.out.println("Invalid date format. Please enter date in dd.MM.yyyy format.");
    } catch (IllegalArgumentException e) {
      System.out.println("Error adding ingredient: " + e.getMessage());
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
          .sorted()
          .toList()
          .forEach(this::printIngredient);
    }
  }

  private void listAllExpiredIngredients() {
    Ingredient[] expiredIngredients = storage.listAllIngredients();
    if (expiredIngredients.length == 0) {
      System.out.println("No expired ingredients in storage");
    } else {
      Arrays.stream(expiredIngredients)
          .sorted()
          .toList()
          .forEach(this::printIngredient);
    }
  }

  private void calculateTotalValue() {
    double totalValue = storage.calculateTotalValue();
    System.out.printf("\"Total value of all ingredients: %.2f%n\"", totalValue);
  }

  private void calculateValue() {
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
}

