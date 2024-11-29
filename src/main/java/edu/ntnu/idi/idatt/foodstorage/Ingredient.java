package edu.ntnu.idi.idatt.foodstorage;

import edu.ntnu.idi.idatt.utils.InputValidation;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Ingredient class represents a grocery item or ingredient. It holds information about the
 * ingredient's name, quantity, unit of measurement, best-before date, and price per unit.
 */
public class Ingredient {

  private String name;
  private double quantity;
  private String unit;
  private Date bestBeforeDate;
  private double pricePerUnit;

  /**
   * Date formatter for parsing and formatting dates.
   */
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

  /**
   * Constructs an Ingredient with the specified name, quantity, unit, best-before date, and price
   * per unit.
   *
   * @param name           the name of the ingredient
   * @param quantity       the amount of the ingredient
   * @param unit           the unit of measurement for the ingredient
   * @param bestBeforeDate the best-before date of the ingredient
   * @param pricePerUnit   the price per unit
   * @throws IllegalArgumentException if any parameter is invalid
   */
  public Ingredient(String name, double quantity, String unit, Date bestBeforeDate,
      double pricePerUnit) {

    InputValidation.validateIngredientName(name);
    InputValidation.validateIngredientQuantity(quantity);
    InputValidation.validateIngredientUnit(unit);
    InputValidation.validatePricePerUnit(pricePerUnit);
    InputValidation.validateBestBeforeDate(bestBeforeDate);

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
    InputValidation.validateIngredientName(name);
    this.name = name.trim();
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
    InputValidation.validateIngredientQuantity(quantity);
    this.quantity = quantity;
  }

  public String getUnit() {
    return unit;
  }

  public Date getBestBeforeDate() {
    return new Date(bestBeforeDate.getTime());
  }

  public double getPricePerUnit() {
    return pricePerUnit;
  }

  /**
   * Checks if the ingredient is expired based on the best-before date.
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
    String dateStr = dateFormat.format(bestBeforeDate);
    String formattedQuantity = String.format("%.2f", quantity);
    return name + ": " + formattedQuantity + " " + unit + ", Best before: "
        + dateStr + ", Price: " + pricePerUnit;
  }

}
