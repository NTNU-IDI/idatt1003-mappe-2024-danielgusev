package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.views.FoodStorageUI;

public class Main {

  public static void main(String[] args) {
    System.out.println("This is your fridge!");
    FoodStorageUI ui = new FoodStorageUI();
    ui.init();
    ui.start();
  }

}
