package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.foodstorage.FoodStorage;
import edu.ntnu.idi.idatt.foodstorage.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FoodStorageTest {

  private FoodStorage storage;
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

  @BeforeEach
  public void setUp() {
    storage = new FoodStorage();
  }

  @Test
  @DisplayName("Test adding ingredient with valid data")
  public void FoodStorageTest1() throws Exception {
    Date date = DATE_FORMAT.parse("10.12.2024");
    Ingredient ingredient = new Ingredient("Butter", 0.2, "kilogram", date, 30.0);
    storage.addIngredient(ingredient);

    Ingredient[] ingredients = storage.listAllIngredients();
    Assertions.assertEquals(1, ingredients.length);
    Assertions.assertEquals("Butter", ingredients[0].getName());
    Assertions.assertEquals(0.2, ingredients[0].getQuantity());
  }

  @Test
  @DisplayName("Test adding duplicate ingredient increases quantity")
  public void FoodStorageTest2() throws Exception {
    Date date = DATE_FORMAT.parse("10.12.2024");
    Ingredient ingredient1 = new Ingredient("Butter", 0.2, "kilogram", date, 30.0);
    Ingredient ingredient2 = new Ingredient("Butter", 0.3, "kilogram", date, 30.0);

    storage.addIngredient(ingredient1);
    storage.addIngredient(ingredient2);

    Ingredient[] ingredients = storage.listAllIngredients();
    Assertions.assertEquals(1, ingredients.length);
    Assertions.assertEquals(0.5, ingredients[0].getQuantity());
  }

  @Test
  @DisplayName("Test removing ingredient decreases quantity")
  public void FoodStorageTest3() throws Exception {
    Date date = DATE_FORMAT.parse("10.12.2024");
    Ingredient ingredient = new Ingredient("Butter", 0.5, "kilogram", date, 30.0);
    storage.addIngredient(ingredient);

    storage.removeIngredient("Butter", "kilogram", 30.0, date, 0.2);

    Ingredient[] ingredients = storage.listAllIngredients();
    Assertions.assertEquals(1, ingredients.length);
    Assertions.assertEquals(0.3, ingredients[0].getQuantity());
  }

  @Test
  @DisplayName("Test removing ingredient when quantity becomes zero")
  public void FoodStorageTest4() throws Exception {
    Date date = DATE_FORMAT.parse("10.12.2024");
    Ingredient ingredient = new Ingredient("Milk", 1.0, "liter", date, 20.0);
    storage.addIngredient(ingredient);

    storage.removeIngredient("Milk", "liter", 20.0, date, 1.0);

    Ingredient[] ingredients = storage.listAllIngredients();
    Assertions.assertEquals(0, ingredients.length);
  }

  @Test
  @DisplayName("Test removing ingredient with insufficient quantity")
  public void FoodStorageTest5() throws Exception {
    Date date = DATE_FORMAT.parse("10.12.2024");
    Ingredient ingredient = new Ingredient("Butter", 0.2, "kilogram", date, 30.0);
    storage.addIngredient(ingredient);

    Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
      storage.removeIngredient("Butter", "kilogram", 30.0, date, 0.3);
    });

    Assertions.assertEquals("Not enough quantity to remove.", exception.getMessage());
  }

  @Test
  @DisplayName("Test removing ingredient with negative quantity")
  public void FoodStorageTest6() throws Exception {
    Date date = DATE_FORMAT.parse("10.12.2024");
    Ingredient ingredient = new Ingredient("Cheese", 0.5, "kilogram", date, 50.0);
    storage.addIngredient(ingredient);

    Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
      storage.removeIngredient("Cheese", "kilogram", 50.0, date, -0.1);
    });

    Assertions.assertEquals("Quantity to remove cannot be negative.", exception.getMessage());
  }

  @Test
  @DisplayName("Test removing non-existent ingredient")
  public void FoodStorageTest7() throws Exception {
    Date date = DATE_FORMAT.parse("10.12.2024");

    Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
      storage.removeIngredient("Milk", "liter", 20.0, date, 1.0);
    });

    Assertions.assertEquals("Ingredient not found in storage.", exception.getMessage());
  }

  @Test
  @DisplayName("Test searching for existing ingredient")
  public void FoodStorageTest8() throws Exception {
    Date date = DATE_FORMAT.parse("10.12.2024");
    Ingredient ingredient = new Ingredient("Butter", 0.2, "kilogram", date, 30.0);
    storage.addIngredient(ingredient);

    Ingredient found = storage.searchIngredient("Butter", "kilogram", 30.0, date);
    Assertions.assertNotNull(found);
    Assertions.assertEquals("Butter", found.getName());
  }

  @Test
  @DisplayName("Test searching ingredient with null or empty inputs")
  public void FoodStorageTest9() throws Exception {
    Date date = DATE_FORMAT.parse("10.12.2024");

    Ingredient found1 = storage.searchIngredient(null, "kilogram", 30.0, date);
    Assertions.assertNull(found1);

    Ingredient found2 = storage.searchIngredient("  ", "kilogram", 30.0, date);
    Assertions.assertNull(found2);

    Ingredient found3 = storage.searchIngredient("Butter", null, 30.0, date);
    Assertions.assertNull(found3);

    Ingredient found4 = storage.searchIngredient("Butter", "  ", 30.0, date);
    Assertions.assertNull(found4);

    Ingredient found5 = storage.searchIngredient("Butter", "kilogram", 30.0, null);
    Assertions.assertNull(found5);
  }

  @Test
  @DisplayName("Test finding ingredient by name and unit")
  public void FoodStorageTest10() throws Exception {
    Date date = DATE_FORMAT.parse("10.12.2024");
    Ingredient ingredient = new Ingredient("Butter", 0.2, "kilogram", date, 30.0);
    storage.addIngredient(ingredient);

    Ingredient found = storage.findIngredientByNameAndUnit("Butter", "g");
    Assertions.assertNotNull(found);
    Assertions.assertEquals("Butter", found.getName());
    Assertions.assertEquals("kilogram", found.getUnit());
  }

  @Test
  @DisplayName("Test finding ingredient by name and unit with null inputs")
  public void FoodStorageTest11() {
    Ingredient found1 = storage.findIngredientByNameAndUnit(null, "kilogram");
    Assertions.assertNull(found1);

    Ingredient found2 = storage.findIngredientByNameAndUnit("Butter", null);
    Assertions.assertNull(found2);
  }

  @Test
  @DisplayName("Test listing all ingredients")
  public void FoodStorageTest12() throws Exception {
    Date date1 = DATE_FORMAT.parse("10.12.2024");
    Date date2 = DATE_FORMAT.parse("15.01.2025");
    Ingredient ingredient1 = new Ingredient("Butter", 0.2, "kilogram", date1, 30.0);
    Ingredient ingredient2 = new Ingredient("Milk", 1.0, "liter", date2, 20.0);
    storage.addIngredient(ingredient1);
    storage.addIngredient(ingredient2);

    Ingredient[] ingredients = storage.listAllIngredients();
    Assertions.assertEquals(2, ingredients.length);
  }

  // Continue renaming the rest of the test methods accordingly...

  // public void FoodStorageTest13() { ... }
  // public void FoodStorageTest14() { ... }
  // etc.

}
