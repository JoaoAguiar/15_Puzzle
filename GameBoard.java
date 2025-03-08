import java.util.*;

/**
 * This class represents the game board for the 15-puzzle.
 * It encapsulates the board state and operations that can be performed on it.
 */
class GameBoard {
  // Constants
  private static final int BOARD_SIZE = 4;
  private static final int ARRAY_SIZE = BOARD_SIZE * BOARD_SIZE + 1;
  
  // Instance variables
  public int[] board; // The board configuration
  public int depth; // The depth of the board in the search tree
  public int position_zero; // The position of the empty space (0)
  public GameBoard parent_board; // The parent board
  public int cost; // The cost associated with the board

  /**
   * Constructor to create a game board from user input.
   * @param input Scanner object to read user input.
   * @throws InputMismatchException if input is not valid
   */
  public GameBoard(Scanner input) {
    board = new int[ARRAY_SIZE];
    depth = 0;

    for (int i = 1; i < ARRAY_SIZE; i++) {
      board[i] = input.nextInt();
      if (board[i] == 0) {
        position_zero = i;
      }
    }
    validateBoard();
  }

  /**
   * Constructor to create a game board from an array.
   * @param array The array representing the board configuration.
   */
  public GameBoard(int[] array) {
    board = new int[ARRAY_SIZE];
    depth = 0;
    for (int i = 1; i < array.length; i++) {
      board[i] = array[i];
      if (board[i] == 0) {
        position_zero = i;
      }
    }
  }

  /**
   * Validates that the board contains all required numbers 0-15 exactly once.
   * @throws IllegalStateException if the board is invalid
   */
  private void validateBoard() {
    boolean[] seen = new boolean[ARRAY_SIZE];
    for (int i = 1; i < ARRAY_SIZE; i++) {
      if (board[i] < 0 || board[i] >= ARRAY_SIZE - 1 || seen[board[i]]) {
        throw new IllegalStateException("Invalid board configuration: each number from 0 to 15 must appear exactly once");
      }
      seen[board[i]] = true;
    }
  }

  /**
   * Creates a string representation of the board configuration.
   * @return A formatted string displaying the board.
   */
  public String printBoard() {
    StringBuilder boardStr = new StringBuilder();
    for (int i = 1; i < ARRAY_SIZE; i += BOARD_SIZE) {
      boardStr.append(this.board[i]).append(" ").append(this.board[i + 1]).append(" ")
              .append(this.board[i + 2]).append(" ").append(this.board[i + 3]).append("\n");
    }
    return boardStr.toString();
  }

  /**
   * Makes a move on the board.
   * @param action The position to move the empty space to.
   * @return A new GameBoard object with the updated configuration.
   */
  public GameBoard makeAction(int action) {
    GameBoard new_board = new GameBoard(this.board);
    new_board.board[new_board.position_zero] = new_board.board[action];
    new_board.board[action] = 0;
    new_board.position_zero = action;
    new_board.depth = this.depth + 1;
    return new_board;
  }

  /**
   * Method to get the string representation of the board.
   * @return A string representing the board configuration.
   */
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append(this.board[1]);
    for (int i = 2; i < 17; i++) {
      str.append(" ").append(this.board[i]);
    }
    return str.toString();
  }

  /**
   * Method to get the board configuration.
   * @return An array representing the board configuration.
   */
  public int[] getBoard() {
    return board;
  }

  /**
   * Method to get the possible actions (moves) from the current board configuration.
   * @return A list of possible actions.
   */
  public LinkedList<Integer> getActions() {
    LinkedList<Integer> actions = new LinkedList<>();
    if (getLeft() != -1) actions.add(getLeft());
    if (getRight() != -1) actions.add(getRight());
    if (getUp() != -1) actions.add(getUp());
    if (getDown() != -1) actions.add(getDown());
    return actions;
  }

  /**
   * Method to get the position to the left of the empty space.
   * @return The position to the left or -1 if not possible.
   */
  public int getLeft() {
    return ((position_zero - 1) % 4) == 0 ? -1 : position_zero - 1;
  }

  /**
   * Method to get the position to the right of the empty space.
   * @return The position to the right or -1 if not possible.
   */
  public int getRight() {
    return (position_zero % 4) == 0 ? -1 : position_zero + 1;
  }

  /**
   * Method to get the position above the empty space.
   * @return The position above or -1 if not possible.
   */
  public int getUp() {
    return position_zero < 5 ? -1 : position_zero - 4;
  }

  /**
   * Method to get the position below the empty space.
   * @return The position below or -1 if not possible.
   */
  public int getDown() {
    return position_zero > 12 ? -1 : position_zero + 4;
  }

  /**
   * Method to get the parent board.
   * @return The parent GameBoard object.
   */
  public GameBoard getParent() {
    return parent_board;
  }

  /**
   * Method to set the parent board.
   * @param parent The parent GameBoard object.
   */
  public void setParent(GameBoard parent) {
    parent_board = parent;
  }

  /**
   * Overrides the equals method to compare board configurations.
   * @param obj The object to compare with.
   * @return true if the boards have the same configuration, false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    
    GameBoard other = (GameBoard) obj;
    return Arrays.equals(this.board, other.board);
  }

  /**
   * Overrides the hashCode method.
   * @return A hash code value for this board.
   */
  @Override
  public int hashCode() {
    return Arrays.hashCode(this.board);
  }
}