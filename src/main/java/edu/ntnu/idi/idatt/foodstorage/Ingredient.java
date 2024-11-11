package edu.ntnu.idi.idatt.foodstorage;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The ingredient class represents a grocery item or ingredient. It holds information about the
 * ingredient´s name, quantity, unit of measurement, best-before date, and price per unit.
 */

public class Ingredient {

  String name;
  double quantity;
  String unit;
  Date bestBeforeDate;
  double pricePerUnit;

  /**
   * Date formatter for parsing and formatting dates.
   */
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

  /**
   * Constructs an Ingredient with the specified name, quantity, unit, and best-before date.
   * Initializes the price per unit to 0 by default.
   *
   * @param name           the name of the ingredient
   * @param quantity       the amount of the ingredient
   * @param unit           the unit of measurement for the ingredient
   * @param bestBeforeDate the best-before date of the ingredient
   */

  public Ingredient(String name, double quantity, String unit, Date bestBeforeDate,
      Double pricePerUnit) {

    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Ingredient name cannot be null or empty");
    }
    if (quantity < 0) {
      throw new IllegalArgumentException("Ingredient quantity cannot be negative");
    }
    if (unit == null || unit.trim().isEmpty()) {
      throw new IllegalArgumentException("Ingredient unit cannot be null or empty");
    }
    if (pricePerUnit < 0) {
      throw new IllegalArgumentException("Ingredient price per unit cannot be negative");
    }

    this.name = name.trim();
    this.quantity = quantity;
    this.unit = unit.trim();
    this.pricePerUnit = pricePerUnit;
    this.bestBeforeDate = new Date(bestBeforeDate.getTime());
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getQuantity() {
    return quantity;
  }

  /**
   * Sets the quantity of the ingredient.
   *
   * @param quantity the new quantity of the ingredient
   * @throws IllegalArgumentException if the specified quantity is negative
   */
  public void setQuantity(double quantity) {
    if (quantity < 0) {
      throw new IllegalArgumentException("Ingredient quantity cannot be negative");
    }
    this.quantity = quantity;
  }

  public String getUnit() {
    return unit;
  }

  public Date getBestBeforeDate() {
    return bestBeforeDate;
  }

  public double getPricePerUnit() {
    return pricePerUnit;
  }

  /**
   * Checks if the ingredient is expired based on the best before date.
   *
   * @return true if expired, otherwise false
   */
  public boolean isExpired() {

    return bestBeforeDate != null && bestBeforeDate.before(new Date());
  }

  /**
   * Calculates the total value of the ingredient based on the quantity and price per unit.
   *
   * @return total value
   */
  public double getTotalValue() {

    return quantity * pricePerUnit;
  }

  @Override
  public String toString() {

    return name + ":" + quantity + " " + unit + ", Best before: "
        + bestBeforeDate + ", Price per unit" + pricePerUnit;
  }
}
