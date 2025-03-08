/**
 * This class contains heuristic functions used in search algorithms.
 */
class Heuristic {
  // Constants for heuristic choices
  public static final int OFFSITE_PARTS = 1;
  public static final int MANHATTAN_DISTANCE = 2;
  
  // Constants for search types
  public static final int GREEDY = 1;
  public static final int ASTAR = 2;
  
  // Board size constants
  private static final int BOARD_SIZE = 4;
  private static final int ARRAY_SIZE = BOARD_SIZE * BOARD_SIZE + 1;

  /**
   * Chooses the heuristic based on the search type.
   * @param board The current board configuration.
   * @param final_board The goal configuration of the board.
   * @param choice The heuristic choice.
   * @param search_type The type of search algorithm.
   * @throws IllegalArgumentException if an invalid heuristic choice is provided
   */
  public static void heuristicChoice(GameBoard board, GameBoard final_board, int choice, int search_type) {
    if (choice == OFFSITE_PARTS && search_type == GREEDY) {
      board.cost = offSiteParts(board, final_board);
    } else if (choice == MANHATTAN_DISTANCE && search_type == GREEDY) {
      board.cost = manhattanDistance(board, final_board);
    } else if (choice == OFFSITE_PARTS && search_type == ASTAR) {
      board.cost = board.depth + offSiteParts(board, final_board);
    } else if (choice == MANHATTAN_DISTANCE && search_type == ASTAR) {
      board.cost = board.depth + manhattanDistance(board, final_board);
    } else {
      throw new IllegalArgumentException("Invalid heuristic or search type combination");
    }
  }

  /**
   * Heuristic: Counts the number of tiles that are not in their goal position.
   * @param board The current board configuration.
   * @param final_board The goal configuration of the board.
   * @return The number of tiles that are not in their goal position.
   */
  public static int offSiteParts(GameBoard board, GameBoard final_board) {
    int outside_positions = 0;
    int[] aux1_board = board.getBoard();
    int[] aux2_board = final_board.getBoard();

    for (int i = 1; i < ARRAY_SIZE; i++) {
      if (aux1_board[i] != aux2_board[i]) {
        outside_positions++;
      }
    }

    return outside_positions;
  }

  /**
   * Heuristic: Calculates the Manhattan distance.
   * @param board The current board configuration.
   * @param final_board The goal configuration of the board.
   * @return The Manhattan distance.
   */
  public static int manhattanDistance(GameBoard board, GameBoard final_board) {
    int[] aux1_board = board.getBoard();
    int[] aux2_board = final_board.getBoard();
    int[][] aux1_matrix = new int[BOARD_SIZE + 1][BOARD_SIZE + 1];
    int[][] aux2_matrix = new int[BOARD_SIZE + 1][BOARD_SIZE + 1];
    int i = 1;
    int[] aux;
    int result = 0;

    // Convert the board arrays to 2D matrices
    for (int x = 1; x < BOARD_SIZE + 1; x++) {
      for (int y = 1; y < BOARD_SIZE + 1; y++) {
        aux1_matrix[x][y] = aux1_board[i];
        aux2_matrix[x][y] = aux2_board[i];
        i++;
      }
    }

    // Calculate the Manhattan distance
    for (int x = 1; x < BOARD_SIZE + 1; x++) {
      for (int y = 1; y < BOARD_SIZE + 1; y++) {
        int value = aux1_matrix[x][y];
        aux = helper(aux2_matrix, value);

        if (value != 0 && value != (x + y - 1)) {
          result += (Math.abs(x - aux[0]) + Math.abs(y - aux[1]));
        }
      }
    }

    return result;
  }

  /**
   * Helper method to find the position of a value in the matrix.
   * @param matrix The matrix to search.
   * @param value The value to find.
   * @return The position of the value in the matrix.
   * @throws IllegalStateException if the value is not found in the matrix
   */
  public static int[] helper(int[][] matrix, int value) {
    int[] position = new int[2];

    for (int i = 1; i < BOARD_SIZE + 1; i++) {
      for (int j = 1; j < BOARD_SIZE + 1; j++) {
        if (matrix[i][j] == value) {
          position[0] = i;
          position[1] = j;
          return position;
        }
      }
    }

    throw new IllegalStateException("The board does not have the same numbers");
  }
}
