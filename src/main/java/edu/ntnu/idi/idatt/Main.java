package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.views.UserInterface;

/**
 * Main class to start the FoodStorage application.
 */
public class Main {

  /**
   * The main method to launch the application.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    System.out.println("This is your fridge!");
    UserInterface ui = new UserInterface();
    ui.init();
    ui.start();
  }
}
