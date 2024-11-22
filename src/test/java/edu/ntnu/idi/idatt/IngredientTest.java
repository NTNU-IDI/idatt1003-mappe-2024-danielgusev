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
    Date bestBeforeDate = DATE_FORMAT.parse("02.12.2024"); // Within range, not excluded dates
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

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient(null, 1.75, "liter", bestBeforeDate, 33.60);
    });
    assertEquals("Ingredient name cannot be null or empty", exception.getMessage());
  }

  @Test
  public void testIngredientCreationWithEmptyName() throws Exception {
    Date bestBeforeDate = DATE_FORMAT.parse("02.12.2024");

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient("   ", 1.75, "liter", bestBeforeDate, 33.60);
    });
    assertEquals("Ingredient name cannot be null or empty", exception.getMessage());
  }

  @Test
  public void testIngredientCreationWithNegativeQuantity() throws Exception {
    Date bestBeforeDate = DATE_FORMAT.parse("02.12.2024");

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient("Milk", -1.0, "liter", bestBeforeDate, 33.60);
    });
    assertEquals("Ingredient quantity cannot be negative", exception.getMessage());
  }

  @Test
  public void testIngredientCreationWithNullUnit() throws Exception {
    Date bestBeforeDate = DATE_FORMAT.parse("02.12.2024");

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient("Milk", 1.75, null, bestBeforeDate, 33.60);
    });
    assertEquals("Ingredient unit cannot be null or empty", exception.getMessage());
  }

  @Test
  public void testIngredientCreationWithEmptyUnit() throws Exception {
    Date bestBeforeDate = DATE_FORMAT.parse("02.12.2024");

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient("Milk", 1.75, "   ", bestBeforeDate, 33.60);
    });
    assertEquals("Ingredient unit cannot be null or empty", exception.getMessage());
  }

  @Test
  public void testIngredientCreationWithNegativePricePerUnit() throws Exception {
    Date bestBeforeDate = DATE_FORMAT.parse("02.12.2024");

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient("Milk", 1.75, "liter", bestBeforeDate, -5.0);
    });
    assertEquals("Ingredient price per unit cannot be negative", exception.getMessage());
  }

  @Test
  public void testSetQuantityValid() throws Exception {
    Date bestBeforeDate = DATE_FORMAT.parse("15.12.2024"); // Within range, not excluded dates
    Ingredient ingredient = new Ingredient("Butter", 250.0, "g", bestBeforeDate, 37.70);
    ingredient.setQuantity(500.0);
    assertEquals(500.0, ingredient.getQuantity(), 0.001);
  }

  @Test
  public void testSetQuantityNegative() throws Exception {
    Date bestBeforeDate = DATE_FORMAT.parse("15.12.2024");
    Ingredient ingredient = new Ingredient("Butter", 250.0, "g", bestBeforeDate, 37.70);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      ingredient.setQuantity(-100.0);
    });
    assertEquals("Ingredient quantity cannot be negative", exception.getMessage());
  }

  @Test
  public void testIsExpiredWhenNotExpired() throws Exception {
    Date futureDate = DATE_FORMAT.parse("01.01.2025"); // Within range, future date
    Ingredient ingredient = new Ingredient("Eggs", 12.0, "pcs", futureDate, 40.70);
    assertFalse(ingredient.isExpired());
  }

  @Test
  public void testIsExpiredWhenExpired() throws Exception {
    Date pastDate = DATE_FORMAT.parse("10.11.2024"); // Before 01.12.2024
    Ingredient ingredient = new Ingredient("Eggs", 12.0, "pcs", pastDate, 40.70);
    assertTrue(ingredient.isExpired());
  }

  @Test
  public void testGetTotalValue() throws Exception {
    Date bestBeforeDate = DATE_FORMAT.parse("15.12.2024");
    Ingredient ingredient = new Ingredient("Milk", 1.75, "liter", bestBeforeDate, 33.60);
    double expectedTotalValue = 1.75 * 33.60;
    assertEquals(expectedTotalValue, ingredient.getTotalValue(), 0.001);
  }

  @Test
  public void testToString() throws Exception {
    Date bestBeforeDate = DATE_FORMAT.parse("15.12.2024");
    Ingredient ingredient = new Ingredient("Milk", 1.75, "liter", bestBeforeDate, 33.60);
    String expectedString =
        "Milk:1.75 liter, Best before: " + bestBeforeDate + ", Price per unit" + 33.60;
    assertEquals(expectedString, ingredient.toString());
  }
}
