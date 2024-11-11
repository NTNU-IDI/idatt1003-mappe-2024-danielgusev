package edu.ntnu.idi.idatt.foodstorage;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.jupiter.api.Test;

public class IngredientTest {

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

  @Test
  public void testValidIngredientCreation() throws Exception {
    String dateString = "30.11.2024";
    Date expirationDate = DATE_FORMAT.parse(dateString);
    Ingredient ingredient = new Ingredient(
        "Milk", 1.75, "liter", expirationDate, 33.60);
    assertEquals("Milk", ingredient.getName());
    assertEquals(1.5, ingredient.getQuantity());
    assertEquals("liter", ingredient.getUnit());
    assertEquals(DATE_FORMAT.parse(dateString), ingredient.getBestBeforeDate());
    assertEquals(33.60, ingredient.getPricePerUnit());
  }

  @Test
  public void testInvalidName() {
    Date date = new Date();
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient("", 1.0, "gram", date, 10.0);
    });
    assertEquals("Name cannot be null or empty.", exception.getMessage());
  }

  @Test
  public void testNegativeQuantity() {
    Date date = new Date();
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient("Sugar", -1.0, "gram", date, 10.0);
    });
    assertEquals("Quantity cannot be negative.", exception.getMessage());
  }

  @Test
  public void testInvalidUnit() {
    Date date = new Date();
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient("Sugar", 1.0, "", date, 10.0);
    });
    assertEquals("Unit cannot be null or empty.", exception.getMessage());
  }

  @Test
  public void testNullBestBeforeDate() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient("Sugar", 1.0, "gram", null, 10.0);
    });
    assertEquals("Best-before date cannot be null.", exception.getMessage());
  }

  @Test
  public void testNegativePricePerUnit() {
    Date date = new Date();
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient("Sugar", 1.0, "gram", date, -5.0);
    });
    assertEquals("Price per unit cannot be negative.", exception.getMessage());
  }

}
