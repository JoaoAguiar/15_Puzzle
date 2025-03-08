import java.util.*;

/**
 * This class contains various search algorithms to solve the 15-puzzle problem.
 */
class TypesOfSearch {
  // Default maximum depth for DFS
  private static final int DEFAULT_MAX_DEPTH = 50;

  /**
   * Performs Breadth-First Search (BFS) to find the solution.
   * @param initial_board The initial configuration of the board.
   * @param final_board The goal configuration of the board.
   */
  public static void bfs(GameBoard initial_board, GameBoard final_board) {
    long timeStart = System.currentTimeMillis();
    int visited = 0;
    int generated = 1;
    String end_board = final_board.toString();

    Set<String> visited_boards = new HashSet<>();
    Queue<GameBoard> game_queue = new LinkedList<>();

    game_queue.add(initial_board);
    visited_boards.add(initial_board.toString());

    while (!game_queue.isEmpty()) {
      GameBoard board = game_queue.remove();
      visited++;
      generated--;
      
      // Check if current board is the goal board
      if (board.toString().equals(end_board)) {
        printPath(board, timeStart, visited, generated);
        return;
      }
      
      expandNode(board, end_board, visited_boards, game_queue, generated);
    }

    System.out.println("No solution found!!");
  }

  /**
   * Expands a node by generating all possible moves.
   * @param board Current board state
   * @param end_board Goal board state as string
   * @param visited_boards Set of visited board configurations
   * @param queue Queue for BFS/A*/Greedy (can be null for DFS)
   * @param generated Counter for generated nodes
   * @return Goal board if found, otherwise null (for DFS)
   */
  private static GameBoard expandNode(GameBoard board, String end_board, 
                                     Set<String> visited_boards, Queue<GameBoard> queue, 
                                     int generated) {
    LinkedList<Integer> list_actions = board.getActions();

    for (int board_action : list_actions) {
      GameBoard child = board.makeAction(board_action);
      child.setParent(board);

      if (!visited_boards.contains(child.toString())) {
        visited_boards.add(child.toString());
        
        if (child.toString().equals(end_board)) {
          return child; // Found goal for DFS
        }
        
        if (queue != null) {
          queue.add(child);
          generated++;
        }
      }
    }
    
    return null; // No goal found in this branch
  }

  /**
   * Performs Iterative Deepening Depth-First Search (IDFS) to find the solution.
   * @param initial_board The initial configuration of the board.
   * @param final_board The goal configuration of the board.
   * @param maxDepth The maximum depth to search.
   */
  public static void idfs(GameBoard initial_board, GameBoard final_board, int maxDepth) {
    long timeStart = System.currentTimeMillis();
    int generated = 0;
    int visited = 0;
    String end_board = final_board.toString();

    Set<String> visited_boards = new HashSet<>();

    GameBoard leaf_board = dfs(initial_board, end_board, visited_boards, generated, visited, maxDepth);

    if (leaf_board != null) {
      printPath(leaf_board, timeStart, visited, generated);
    }
  }

  /**
   * Performs Depth-First Search (DFS) to find the solution.
   * @param initial_board The initial configuration of the board.
   * @param final_board The goal configuration of the board.
   */
  public static void dfs(GameBoard initial_board, GameBoard final_board) {
    long timeStart = System.currentTimeMillis();
    int generated = 0;
    int visited = 0;
    String end_board = final_board.toString();

    Set<String> visited_boards = new HashSet<>();

    GameBoard leaf_board = dfs(initial_board, end_board, visited_boards, generated, visited, DEFAULT_MAX_DEPTH);

    if (leaf_board != null) {
      printPath(leaf_board, timeStart, visited, generated);
    }
  }

  /**
   * Helper method for DFS and IDFS.
   * @param board The current board configuration.
   * @param end_board The goal configuration of the board.
   * @param visited_boards Set of visited board configurations.
   * @param generated Number of generated nodes.
   * @param visited Number of visited nodes.
   * @param maxDepth The maximum depth to search.
   * @return The goal board configuration if found, otherwise null.
   */
  public static GameBoard dfs(GameBoard board, String end_board, Set<String> visited_boards, int generated, int visited, int maxDepth) {
    if (visited_boards.contains(board.toString()) || maxDepth == 0) {
      return null;
    }

    visited_boards.add(board.toString());
    visited++;
    LinkedList<Integer> list_actions = board.getActions();

    for (int board_action : list_actions) {
      GameBoard child = board.makeAction(board_action);
      child.setParent(board);

      if (child.depth > generated) {
        generated = child.depth;
      }
      if (child.toString().equals(end_board)) {
        return child;
      } else {
        GameBoard aux = dfs(child, end_board, visited_boards, generated, visited, maxDepth - 1);
        if (aux != null) {
          return aux;
        }
      }
    }

    return null;
  }

  /**
   * Performs Greedy Search to find the solution.
   * @param initial_board The initial configuration of the board.
   * @param final_board The goal configuration of the board.
   * @param choice The heuristic choice.
   */
  public static void greedy(GameBoard initial_board, GameBoard final_board, int choice) {
    long timeStart = System.currentTimeMillis();
    int visited = 0;
    int generated = 1;

    PriorityQueue<GameBoard> board_queue = new PriorityQueue<>(new BoardComparator());
    Set<String> visited_boards = new HashSet<>();

    board_queue.add(initial_board);
    visited_boards.add(initial_board.toString());

    while (!board_queue.isEmpty()) {
      GameBoard board = board_queue.remove();
      generated--;
      LinkedList<Integer> list_actions = board.getActions();

      for (int board_action : list_actions) {
        GameBoard child = board.makeAction(board_action);
        Heuristic.heuristicChoice(child, final_board, choice, 1);

        if (!visited_boards.contains(child.toString())) {
          child.setParent(board);
          visited_boards.add(child.toString());

          if (child.toString().equals(final_board.toString())) {
            printPath(board, timeStart, visited, generated);
            return;
          }

          board_queue.add(child);
          generated++;
        }
      }
    }
  }

  /**
   * Performs A* Search to find the solution.
   * @param initial_board The initial configuration of the board.
   * @param final_board The goal configuration of the board.
   * @param choice The heuristic choice.
   */
  public static void a(GameBoard initial_board, GameBoard final_board, int choice) {
    long timeStart = System.currentTimeMillis();
    int visited = 0;
    int generated = 1;

    PriorityQueue<GameBoard> board_queue = new PriorityQueue<>(new BoardComparator());
    Set<String> visited_boards = new HashSet<>();

    board_queue.add(initial_board);
    visited_boards.add(initial_board.toString());

    while (!board_queue.isEmpty()) {
      GameBoard board = board_queue.remove();
      generated--;
      LinkedList<Integer> list_actions = board.getActions();

      for (int board_action : list_actions) {
        GameBoard child = board.makeAction(board_action);
        Heuristic.heuristicChoice(child, final_board, choice, 2);

        if (!visited_boards.contains(child.toString())) {
          child.setParent(board);
          visited_boards.add(child.toString());

          if (child.toString().equals(final_board.toString())) {
            printPath(board, timeStart, visited, generated);
            return;
          }

          board_queue.add(child);
          generated++;
        }
      }
    }
  }

  /**
   * Prints the solution path with statistics.
   * @param board The final board configuration.
   * @param timeStart The start time of the search.
   * @param visited The number of visited nodes.
   * @param generated The number of generated nodes.
   */
  public static void printPath(GameBoard board, long timeStart, int visited, int generated) {
    long timeEnd = System.currentTimeMillis();
    double elapsedTime = (timeEnd - timeStart) / 1000.0;
    int depth = board.depth;
    
    List<GameBoard> solutionPath = new ArrayList<>();
    
    // Build the path from goal to start
    while (board != null) {
      solutionPath.add(0, board); // Add at the beginning to reverse the order
      board = board.getParent();
    }
    
    System.out.println("\nSolution path from start to goal:");
    for (GameBoard step : solutionPath) {
      System.out.println(step.printBoard());
    }
    
    System.out.println("Elapsed time: " + elapsedTime + " seconds");
    System.out.println("Depth of the solution: " + depth);
    System.out.println("Number of tables generated: " + generated);
    System.out.println("Number of tables visited: " + visited);
  }
}
