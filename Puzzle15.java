import java.util.*;

/**
 * Main class to run the 15-puzzle solver.
 */
class Puzzle15 {
  // Constants for menu choices
  private static final int BFS = 1;
  private static final int DFS = 2;
  private static final int IDFS = 3;
  private static final int ASTAR = 4;
  private static final int GREEDY = 5;

  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

    try {
      printHeader();

      // Read initial and final board configurations
      System.out.println("Initial Board:");
      GameBoard inicial_board = new GameBoard(input);
      System.out.println();
      System.out.println("Final Board:");
      GameBoard final_board = new GameBoard(input);

      // Check if initial and final configurations are the same
      if (inicial_board.equals(final_board)) {
        System.out.println("ERROR: Your initial configuration is equal to your final configuration");
      } else {
        boolean isSolvable = isSolvable(inicial_board);

        // Check if the initial configuration can reach the final configuration
        if (isSolvable) {
          strategyChoice(inicial_board, final_board, input);
        } else {
          System.out.println("ERROR: You cannot reach the final configuration through your initial configuration");
        }
      }
    } catch (InputMismatchException e) {
      System.out.println("ERROR: Invalid input. Please enter only numbers.");
    } catch (IllegalStateException | IllegalArgumentException e) {
      System.out.println("ERROR: " + e.getMessage());
    }
  }

  /**
   * Prints the program header.
   */
  private static void printHeader() {
    System.out.println();
    System.out.println("***********************");
    System.out.println("****** 15 PUZZLE ******");
    System.out.println("***********************");
    System.out.println();
  }

  /**
   * Checks if the initial configuration is solvable.
   * @param inicial_board The initial configuration of the board.
   * @return true if the puzzle is solvable, false otherwise.
   */
  public static boolean isSolvable(GameBoard inicial_board) {
    int[] board = inicial_board.getBoard();
    int inversions = 0;
    int blankRow = 0;

    // Count the number of inversions and find the row of the blank space
    for (int i = 1; i < 17; i++) {
      if (board[i] == 0) {
        blankRow = (i - 1) / 4 + 1;
        continue;
      }
      for (int j = i + 1; j < 17; j++) {
        if (board[j] != 0 && board[i] > board[j]) {
          inversions++;
        }
      }
    }

    // Determine if the puzzle is solvable
    // For a 4x4 puzzle:
    // If blank is on an even row counting from the bottom, then puzzle is solvable if inversions is odd
    // If blank is on an odd row counting from the bottom, then puzzle is solvable if inversions is even
    return (blankRow % 2 == 0) ? (inversions % 2 != 0) : (inversions % 2 == 0);
  }

  /**
   * Method to choose the search strategy.
   * @param inicial_board The initial configuration of the board.
   * @param final_board The goal configuration of the board.
   * @param input Scanner object to read user input.
   */
  public static void strategyChoice(GameBoard inicial_board, GameBoard final_board, Scanner input) {
    System.out.println();
    System.out.println("What strategy do you want to implement:");
    System.out.println("1) Search in Width (BFS)");
    System.out.println("2) Search in Depth (DFS)");
    System.out.println("3) Iterative Search in Depth");
    System.out.println("4) Search A*");
    System.out.println("5) Search Greedy");
    System.out.println();
    System.out.print(">>> ");

    int choice = getValidChoice(input, 5);

    // Execute the chosen search strategy
    switch (choice) {
      case BFS:
        TypesOfSearch.bfs(inicial_board, final_board);
        break;
      case DFS:
        TypesOfSearch.dfs(inicial_board, final_board);
        break;
      case IDFS:
        System.out.println();
        System.out.print("Maximum Depth Intended: ");
        int max_depth = input.nextInt();
        TypesOfSearch.idfs(inicial_board, final_board, max_depth);
        break;
      case ASTAR:
      case GREEDY:
        System.out.println();
        System.out.println("Which heuristic to use:");
        System.out.println("1) Off-site parts count");
        System.out.println("2) Manhattan distance");
        System.out.println();
        System.out.print(">>> ");
        int heuristic_choice = getValidChoice(input, 2);
        if (choice == ASTAR) {
          TypesOfSearch.a(inicial_board, final_board, heuristic_choice);
        } else {
          TypesOfSearch.greedy(inicial_board, final_board, heuristic_choice);
        }
        break;
      default:
        System.out.println("Invalid choice");
    }
  }

  /**
   * Validates and returns the user's choice.
   * @param input Scanner object to read user input.
   * @param nOptions The number of available options.
   * @return The validated choice.
   * @throws InputMismatchException if input is not numeric
   */
  public static int getValidChoice(Scanner input, int nOptions) {
    int choice = input.nextInt();
    while (choice < 1 || choice > nOptions) {
      System.out.println("You chose an invalid number, try again!!");
      System.out.print(">>> ");
      choice = input.nextInt();
    }
    return choice;
  }
}