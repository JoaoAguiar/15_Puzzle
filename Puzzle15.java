import java.util.*;

class Puzzle15 {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

    System.out.println();
    System.out.println("***********************");
    System.out.println("****** 15 PUZZLE ******");
    System.out.println("***********************");
    System.out.println();

    // Criar os tabuleiros inicial e final
    System.out.println("Initial Board:");
    GameBoard inicial_board = new GameBoard(input);
    System.out.println("Final Board:");
    GameBoard final_board = new GameBoard(input);

    if(inicial_board.equals(final_board)) {
      System.out.println("ERROR: Your initial configuration is equal to your final configuration");
    }
    else {
      int validation = checkValidation(inicial_board);

      if(validation == 0) {
        strategyChoice(inicial_board, final_board, input);
      }
      else {
        System.out.println("ERROR: You can not reach the final configuration through your initial configuration");
      }
    }
  }

  public static int checkValidation(GameBoard inicial_board) {
    int[] board = inicial_board.getBoard();
    int line = 0;
    int count = 0;

    // Descobrir em que linha do tabuleiro o 0 se encontra
    for(int i=1; i<17; i++) {
      if(i == 5 || i == 8 || i == 13) {
        line++;

        if(board[i] == 0) {
          break;
        }
      }
      else {
        if(board[i] == 0) {
          break;
        }
      }
    }
    // Conta o numero de vezes que um valor do tabuleiro é menor que o valore à sua frente
    for(int i=1; i<17; i++) {
      for(int j=i+1; j<17; j++) {
        if((board[i] < board[j]) && (board[i] != 0)) {
          count++;
        }
      }
    }

    if((line == 0 || line == 2) && count%2 == 0) {
      return 0;
    }
    else if((line == 1 || line == 3) && count%2 != 0) {
      return 0;
    }
    else {
      return 1;
    }
  }

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

    int choice = choice(input, 5);

    if(choice == 1) {
      TypesOfSearch.bfs(inicial_board, final_board);
    }
    else if(choice == 2) {
      TypesOfSearch.dfs(inicial_board, final_board);
    }
    else if(choice == 3) {
      System.out.println();
      System.out.print("Maximum Depth Intended: ");

      int max_depth = input.nextInt();

      TypesOfSearch.idfs(inicial_board, final_board, max_depth);
    }
    else if(choice == 4) {
      System.out.println();
      System.out.println("Which heuristic to use:");
      System.out.println("1) Off-site parts count");
      System.out.println("2) Manhattan distance");
      System.out.println();
      System.out.print(">>> ");

      int heuristic_choice = choice(input, 2);

      TypesOfSearch.a(inicial_board, final_board, heuristic_choice);
    }
    else {
      System.out.println();
      System.out.println("Which heuristic to use:");
      System.out.println("1) Off-site parts count");
      System.out.println("2) Manhattan distance");
      System.out.println();
      System.out.print(">>> ");

      int heuristic_choice = choice(input, 2);

      TypesOfSearch.greedy(inicial_board, final_board, heuristic_choice);
    }
  }

  // Verifica se as escolhas foram corretas
  public static int choice(Scanner input, int nOptions) {
    int choice = input.nextInt();

    if((choice < 1) || (choice > nOptions)) {
      System.out.println();
      System.out.println("You chose an invalid number, try again!!");
      System.out.print(">>> ");
      choice = choice(input, 5);
    }

    return choice;
  }
}