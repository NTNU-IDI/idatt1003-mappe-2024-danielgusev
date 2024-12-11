package edu.ntnu.idi.idatt;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.foodstorage.Ingredient;
import java.text.SimpleDateFormat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Date;

public class IngredientTest {

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

  @Test
  @DisplayName("Testing if ingredients are created correctly")
  public void IngredientTest1() throws Exception {
    Date bestBeforeDate = DATE_FORMAT.parse("02.12.2024");
    Ingredient ingredient = new Ingredient("Milk", 1.75, "liter", bestBeforeDate, 33.60);

    assertEquals("Milk", ingredient.getName());
    assertEquals(1.75, ingredient.getQuantity(), 0.001);
    assertEquals("liter", ingredient.getUnit());
    assertEquals(bestBeforeDate, ingredient.getBestBeforeDate());
    assertEquals(33.60, ingredient.getPricePerUnit(), 0.001);
  }

  @Test
  @DisplayName("Testing ingredient creation with null name")
  public void IngredientTest2() throws Exception {
    Date bestBeforeDate = DATE_FORMAT.parse("02.12.2024");

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> new Ingredient(null, 1.75, "liter", bestBeforeDate, 33.60));
    assertEquals("Ingredient name cannot be null or empty.", exception.getMessage());
  }

  @Test
  @DisplayName("Testing ingredient creation with empty name")
  public void IngredientTest3() throws Exception {
    Date bestBeforeDate = DATE_FORMAT.parse("02.12.2024");

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> new Ingredient("   ", 1.75, "liter", bestBeforeDate, 33.60));
    assertEquals("Ingredient name cannot be null or empty.", exception.getMessage());
  }

  @Test
  @DisplayName("Testing ingredient creation with negative quantity")
  public void IngredientTest4() throws Exception {
    Date bestBeforeDate = DATE_FORMAT.parse("02.12.2024");

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> new Ingredient("Milk", -1.0, "liter", bestBeforeDate, 33.60));
    assertEquals("Ingredient quantity cannot be negative.", exception.getMessage());
  }

  @Test
  @DisplayName("Testing ingredient creation with null unit")
  public void IngredientTest5() throws Exception {
    Date bestBeforeDate = DATE_FORMAT.parse("02.12.2024");

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> new Ingredient("Milk", 1.75, null, bestBeforeDate, 33.60));
    assertEquals("Ingredient unit cannot be null or empty.", exception.getMessage());
  }

  @Test
  @DisplayName("Testing ingredient creation with empty unit")
  public void IngredientTest6() throws Exception {
    Date bestBeforeDate = DATE_FORMAT.parse("02.12.2024");

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> new Ingredient("Milk", 1.75, "   ", bestBeforeDate, 33.60));
    assertEquals("Ingredient unit cannot be null or empty.", exception.getMessage());
  }

  @Test
  @DisplayName("Testing ingredient creation with negative price per unit")
  public void IngredientTest7() throws Exception {
    Date bestBeforeDate = DATE_FORMAT.parse("02.12.2024");

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> new Ingredient("Milk", 1.75, "liter", bestBeforeDate, -5.0));
    assertEquals("Price per unit cannot be negative.", exception.getMessage());
  }

  @Test
  @DisplayName("Testing setting valid quantity")
  public void IngredientTest8() throws Exception {
    Date bestBeforeDate = DATE_FORMAT.parse("15.12.2024");
    Ingredient ingredient = new Ingredient("Butter", 250.0, "g", bestBeforeDate, 37.70);
    ingredient.setQuantity(500.0);
    assertEquals(500.0, ingredient.getQuantity(), 0.001);
  }

  @Test
  @DisplayName("Testing setting negative quantity")
  public void IngredientTest9() throws Exception {
    Date bestBeforeDate = DATE_FORMAT.parse("15.12.2024");
    Ingredient ingredient = new Ingredient("Butter", 250.0, "g", bestBeforeDate, 37.70);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> ingredient.setQuantity(-100.0));
    assertEquals("Ingredient quantity cannot be negative.", exception.getMessage());
  }

  @Test
  @DisplayName("Testing isExpired method when ingredient is not expired")
  public void IngredientTest10() throws Exception {
    Date futureDate = DATE_FORMAT.parse("01.01.2025");
    Ingredient ingredient = new Ingredient("Eggs", 12.0, "pcs", futureDate, 40.70);
    assertFalse(ingredient.isExpired());
  }

  @Test
  @DisplayName("Testing isExpired method when ingredient is expired")
  public void IngredientTest11() throws Exception {
    Date pastDate = DATE_FORMAT.parse("10.11.2020");
    Ingredient ingredient = new Ingredient("Eggs", 12.0, "pcs", pastDate, 40.70);
    assertTrue(ingredient.isExpired());
  }

  @Test
  @DisplayName("Testing getTotalValue method")
  public void IngredientTest12() throws Exception {
    Date bestBeforeDate = DATE_FORMAT.parse("15.12.2024");
    Ingredient ingredient = new Ingredient("Milk", 1.75, "liter", bestBeforeDate, 33.60);
    double expectedTotalValue = 1.75 * 33.60;
    assertEquals(expectedTotalValue, ingredient.getTotalValue(), 0.001);
  }

  @Test
  @DisplayName("Testing toString method")
  public void IngredientTest13() throws Exception {
    Date bestBeforeDate = DATE_FORMAT.parse("15.12.2024");
    Ingredient ingredient = new Ingredient("Milk", 1.75, "liter", bestBeforeDate, 33.60);

    String expectedString = "Milk: 1.75 liter, Best before: 15.12.2024, Price per unit: 33.6";
    assertEquals(expectedString, ingredient.toString());
  }
}
