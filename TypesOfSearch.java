import java.util.*;

class TypesOfSearch {
  public static void bfs(GameBoard initial_board, GameBoard final_board) {
    long timeStart = System.currentTimeMillis();
    int visited = 0;
    int generated = 1;
    String start_board = initial_board.toString();
    String end_board = final_board.toString();

    // Vai guardar as tabelas visitadas
    Set<String> visited_boards = new HashSet<String>();
    Queue<GameBoard> game_queue = new LinkedList<GameBoard>();

    game_queue.add(initial_board);
    visited_boards.add(start_board);

    while(!game_queue.isEmpty()) {
      // Remove a cabeça da lista
      GameBoard board = game_queue.remove();
      visited++;
      generated--;

      for(Integer board_action : board.getActions()) {
        GameBoard child = board.makeAction(board_action);

        if(!visited_boards.contains(child.toString())) {
          child.setParent(board);

          if(child.toString().equals(end_board)) {
            printPath(child, timeStart, visited, generated);

            return;
          }

          visited_boards.add(child.toString());
          game_queue.add(child);

          generated++;
        }  
      }
    }

    System.out.println("No solution found!!");

    return;
  }

  public static void idfs(GameBoard initial_board, GameBoard final_board, int maxDepth) {
    long timeStart = System.currentTimeMillis();
    int generated = 0;
    int visited = 0;
    String end_board = final_board.toString();

    Set<String> visited_boards = new HashSet<String>();

    GameBoard leaf_board = dfs(initial_board, end_board, visited_boards, generated, visited, maxDepth);

    if(leaf_board != null) {
      printPath(leaf_board, timeStart, visited, generated);
    }

    return;
  }

  public static void dfs(GameBoard initial_board, GameBoard final_board) {
    long timeStart = System.currentTimeMillis();
    int generated = 0;
    int visited = 0;
    String end_board = final_board.toString();

    Set<String> visited_boards = new HashSet<String>();

    GameBoard leaf_board = dfs(initial_board, end_board, visited_boards, generated, visited, 50);

    if(leaf_board != null) {
      printPath(leaf_board, timeStart, visited, generated);
    }

    return;
  }

  public static GameBoard dfs(GameBoard board, String end_board, Set<String> visited_boards, int generated, int visited, int maxDepth) {
    if(visited_boards.contains(board.toString()) || maxDepth == 0) {
      System.out.println("No solution found!!");

      return null;
    }
    else {
      visited_boards.add(board.toString());
      visited++;

      for(Integer board_action : board.getActions()) {
        GameBoard child = board.makeAction(board_action);
        child.setParent(board);

        if(child.depth > generated) {
          generated = child.depth;
        }
        if(child.toString().equals(end_board)) {
          return child;
        }
        else {
          GameBoard aux = dfs(child, end_board, visited_boards, generated, visited, maxDepth-1);

          if(aux != null) {
            return aux;
          }
        }
      }

      System.out.println("No solution found!!");

      return null;
    }
  }

  public static void greedy(GameBoard initial_board, GameBoard final_board, int choice) {
    long timeStart = System.currentTimeMillis();
    int visited = 0;
    int generated = 1;

    PriorityQueue<GameBoard> board_queue = new PriorityQueue<GameBoard>(new BoardComparator());
    Set<String> visited_boards = new HashSet<String>();

    board_queue.add(initial_board);
    visited_boards.add(initial_board.toString());

    while(!board_queue.isEmpty()) {
      GameBoard board = board_queue.remove();
      generated--;

      for(Integer board_action : board.getActions()) {
        GameBoard child = board.makeAction(board_action);

        Heuristic.heuristicChoice(child, final_board, choice, 1);
        
        if(!visited_boards.contains(child.toString())) {
          child.setParent(board);
          visited_boards.add(board.toString());

          if(child.toString().equals(final_board.toString())) {
            printPath(board, timeStart, visited, generated);

            return;
          }

          board_queue.add(child);
          generated++;
        }
      }
    }
  }

  public static void a(GameBoard initial_board, GameBoard final_board, int choice) {
    long timeStart = System.currentTimeMillis();
    int visited = 0;
    int generated = 1;

    PriorityQueue<GameBoard> board_queue = new PriorityQueue<GameBoard>(new BoardComparator());
    Set<String> visited_boards = new HashSet<String>();

    board_queue.add(initial_board);
    visited_boards.add(initial_board.toString());

    while(!board_queue.isEmpty()) {
      GameBoard board = board_queue.remove();
      generated--;

      for(int board_action : board.getActions()) {
        GameBoard child = board.makeAction(board_action);

        Heuristic.heuristicChoice(child, final_board, choice, 2);
        
        if(!visited_boards.contains(child.toString())) {
          child.setParent(board);
          visited_boards.add(board.toString());

          if(child.toString().equals(final_board.toString())) {
            printPath(board, timeStart, visited, generated);

            return;
          }

          board_queue.add(child);
          generated++;
        }
      }
    }
  }

  public static void printPath(GameBoard board, long timeStart, int visited, int generated) {
    long timeEnd = System.currentTimeMillis();
    String path = "";
    int depth = board.depth;

    while(board != null) {
      path = board.printBoard() + "\n\n" + path;
      board = board.getParent();
    }

    System.out.println("The path from the finish to the start: " + path);
    System.out.println("Elapsed time: " + (double)((timeEnd - timeStart)/1000.0) + " seconds");
    System.out.println("Depth of the solution: " + depth);
    System.out.println("Number of tables generated: " + generated);
    System.out.println("Number of tables visited: " + visited);

    return;
  } 
}
