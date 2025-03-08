import java.util.*;

/**
 * Comparator class to compare GameBoard objects based on their cost.
 */
class BoardComparator implements Comparator<GameBoard> {

  /**
   * Compares two GameBoard objects based on their cost.
   * @param board1 The first GameBoard object.
   * @param board2 The second GameBoard object.
   * @return A negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
   */
  public int compare(GameBoard board1, GameBoard board2) {
    return Integer.compare(board1.cost, board2.cost);
  }
}
