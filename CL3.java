import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
// DO NOT ADD ANY ADDITIONAL IMPORTS ------

public class CL3{
  // TODO: IMPLEMENT main() METHOD LOGIC TO CREATE A GAME AND INTERACT WITH THE USER
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
        Game game = new Game();

        // First thing: set up the universe
        game.setupGame();

        boolean exit = false;

        while (!exit) {
          System.out.println("\nWelcome to the Battle Simulator");
          System.out.println("What would you like to do?");
          System.out.println("\t1) Play a one-player game");
          System.out.println("\t2) Play a two-player game");
          System.out.println("\t3) Watch a recursive battle");
          System.out.println("\t4) Tournament mode (best of three)");
          System.out.println("\t5) Change Universe");
          System.out.println("\t6) View Game Credits");
          System.out.println("\t7) Exit");

          int choice = getIntInRange(scanner, 1, 7, "Enter your choice (1-7): ");

          if (choice == 1) {
            // one-player
            game.playOptionOne();
          } else if (choice == 2) {
            // two-player
            game.playOptionTwo();
          } else if (choice == 3) {
            // recursive battle (easy vs medium)
            game.playRecursiveBattleOption();
          } else if (choice == 4) {
            // new CL3 tournament mode
            game.playTournamentMode();
          } else if (choice == 5) {
            // change universe = run setupGame again
            game.setupGame();
          } else if (choice == 6) {
            // credits
            showCredits();
          } else if (choice == 7) {
            System.out.println("Exiting the game... Thank you for playing! Goodbye!");
            exit = true;
          }
        }

        scanner.close();
      }

      // Helper for safe integer input in CL3 main menu
      private static int getIntInRange(Scanner scanner, int min, int max, String prompt) {
        int value = -1;
        boolean valid = false;

        while (!valid) {
          System.out.print(prompt);
          if (scanner.hasNextInt()) {
            value = scanner.nextInt();
            scanner.nextLine(); // consume newline
            if (value >= min && value <= max) {
              valid = true;
            } else {
              System.out.println("Invalid choice. Please enter a number between " + min + " and " + max + ".");
            }
          } else {
            String badWordEntry = scanner.next(); 
            System.out.println("Invalid input. \"" + badWordEntry + "\" is not a number. Try again.");
            scanner.nextLine();
          }
        }

        return value;
      }

      // Simple credits for option 6
      private static void showCredits() {
        System.out.println("\nGame Credits:");
        System.out.println("\tDeveloped by: Anahise Garcia");
        System.out.println("\tUnits 1, 2, and 3");
        System.out.println("\tCourse: Introduction to Computer Science (CS1101)");
        System.out.println("\tDecember 5th, 2025");
        System.out.println("\nReturning to main menu...");
      }

      // TODO: Implement any additional methods as needed for CL3 logic.
    }