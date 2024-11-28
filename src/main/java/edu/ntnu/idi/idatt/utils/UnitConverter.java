package edu.ntnu.idi.idatt.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * The UnitConverter class converts units that use a different measurement unit than liter and kilo
 */
public class UnitConverter {

  private static final Map<String, Double> VOLUME_CONVERSION_FACTORS = new HashMap<>();
  private static final Map<String, Double> WEIGHT_CONVERSION_FACTORS = new HashMap<>();
  private static final Map<String, String> STANDARD_UNIT_MAP = new HashMap<>();

  static {
    // Volume units to liters
    VOLUME_CONVERSION_FACTORS.put("ml", 0.001);
    VOLUME_CONVERSION_FACTORS.put("milliliter", 0.001);
    VOLUME_CONVERSION_FACTORS.put("milliliters", 0.001);
    VOLUME_CONVERSION_FACTORS.put("cl", 0.01);
    VOLUME_CONVERSION_FACTORS.put("centiliter", 0.01);
    VOLUME_CONVERSION_FACTORS.put("centiliters", 0.01);
    VOLUME_CONVERSION_FACTORS.put("dl", 0.1);
    VOLUME_CONVERSION_FACTORS.put("deciliter", 0.1);
    VOLUME_CONVERSION_FACTORS.put("deciliters", 0.1);
    VOLUME_CONVERSION_FACTORS.put("l", 1.0);
    VOLUME_CONVERSION_FACTORS.put("liter", 1.0);
    VOLUME_CONVERSION_FACTORS.put("liters", 1.0);

    // Weight units to kilograms
    WEIGHT_CONVERSION_FACTORS.put("mg", 0.000001);
    WEIGHT_CONVERSION_FACTORS.put("milligram", 0.000001);
    WEIGHT_CONVERSION_FACTORS.put("milligrams", 0.000001);
    WEIGHT_CONVERSION_FACTORS.put("g", 0.001);
    WEIGHT_CONVERSION_FACTORS.put("gram", 0.001);
    WEIGHT_CONVERSION_FACTORS.put("grams", 0.001);
    WEIGHT_CONVERSION_FACTORS.put("kg", 1.0);
    WEIGHT_CONVERSION_FACTORS.put("kilogram", 1.0);
    WEIGHT_CONVERSION_FACTORS.put("kilograms", 1.0);

    // Map units to standard units
    VOLUME_CONVERSION_FACTORS.keySet().forEach(unit -> STANDARD_UNIT_MAP.put(unit, "liter"));
    WEIGHT_CONVERSION_FACTORS.keySet().forEach(unit -> STANDARD_UNIT_MAP.put(unit, "kilogram"));
  }

  /**
   * Converts the given amount from the specified unit to standard units (liter or kilogram).
   *
   * @param amount the amount of the ingredient
   * @param unit   the unit of the ingredient
   * @return returns the correct unit conversion
   */
  public static double convertToStandardUnits(double amount, String unit) {
    String normalizedUnit = unit.trim().toLowerCase();

    if (VOLUME_CONVERSION_FACTORS.containsKey(normalizedUnit)) {
      double factor = VOLUME_CONVERSION_FACTORS.get(normalizedUnit);
      return amount * factor; // Convert to liters
    } else if (WEIGHT_CONVERSION_FACTORS.containsKey(normalizedUnit)) {
      double factor = WEIGHT_CONVERSION_FACTORS.get(normalizedUnit);
      return amount * factor; // Convert to kilograms
    } else {
      throw new IllegalArgumentException("Invalid unit '" + unit
          +
          "'. Supported units include volume units (ml, cl, dl, l) and weight units (mg, g, kg).");
    }

  }

  /**
   * Returns the standard unit (liter or kilogram) for the given unit.
   *
   * @param unit the unit to convert
   * @return the standard unit ("liter" or "kilogram")
   */
  public static String getStandardUnit(String unit) {
    String normalizedUnit = unit.trim().toLowerCase();
    if (STANDARD_UNIT_MAP.containsKey(normalizedUnit)) {
      return STANDARD_UNIT_MAP.get(normalizedUnit);
    } else {
      throw new IllegalArgumentException("Invalid unit '" + unit
          + "'. Supported units include volume units (ml, cl, dl, l) and weight units (mg, g, kg).");
    }
  }

}
