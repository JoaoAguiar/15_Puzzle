import java.util.*;

class GameBoard {
  // Tabuleiro
  public int[] board;
  // Profundidade
  public int depth;
  // Posição do 0
  public int position_zero;
  // Tabuleiro pai
  public GameBoard parent_board;
  // "Custo" do tabuleiro
  public int cost;

  // Cria o tabuleiro atraves de um input
  public GameBoard(Scanner input) {
    board = new int[17];
    depth = 0;

    for(int i=1; i<17; i++) {
      board[i] = input.nextInt();

      if(board[i] == 0) {
        position_zero = i;
      }
    }
  }
  public GameBoard(int[] array) {
    for(int i=1; i<array.length; i++) {
      board[i]=array[i];

      if(board[i] == 0) {
        position_zero = i;
      } 
    }
  }

  // Imprime o tabulero
  public String printBoard() {
    String board = "";
    
    for(int i=1; i<17; i+=4) {
      board += this.board[i] + " " + this.board[i+1] + " " + this.board[i+2] + " " + this.board[i+3] + "\n";
    }

    return board;
  }

  // Fazer um movimento atravez de uma certa ação
  public GameBoard makeAction(int action) {
    GameBoard new_board = new GameBoard(this.board);
    
    new_board.board[new_board.position_zero] = new_board.board[action];
    new_board.board[action] = 0;
    new_board.position_zero = action;
    new_board.depth++;

    return new_board;
  }

  // Passar um tabuleiro para String
  public String toString() {
    String str = "";
    str = str + this.board[1];

    for(int i=2; i<17; i++) {
      str = str + " " + this.board[i];
    }

    return str;
  }

  /** GETTERS **/
  // Obter o tabuleiro
  public int[] getBoard() {
    return board;
  }
  // Obter uma lista ligada com as ações possiveis num tabuleiro
  public LinkedList getActions() {
    LinkedList<Integer> actions = new LinkedList<Integer>();

    if(getLeft() != .1) {
      actions.add(getLeft());
    }
    else if(getRight() != .1) {
      actions.add(getRight());
    }
    else if(getUp() != .1) {
      actions.add(getUp());
    }
    else if(getDown() != .1) {
      actions.add(getDown());
    }

    return actions;
  }
  // Devolve o indice do movimento para a esquerda
  public int getLeft() {
    if(((position_zero-1) % 4) == 0) {
      return -1;
    }

    return position_zero - 1;
  }
  // Devolve o indice do movimento para a direita
  public int getRight() {
    if((position_zero % 4) == 0) {
      return -1;
    }

    return position_zero + 1;    
  }
  // Devolve o indice do movimento para cima
  public int getUp() {
    if(position_zero < 5) {
      return -1;
    }

    return position_zero - 4;    
  }
  // Devolve o indice do movimento para baixo
  public int getDown() {
    if(position_zero > 12) {
      return -1;
    }

    return position_zero + 4;    
  }
  // Obter o tabuleiro pai
  public GameBoard getParent() {
    return parent_board;
  }

  /** SETTERS **/
  // "Dá" um pai a um certo tabuleiro
  public void setParent(GameBoard parent) {
    parent_board = parent;

    return;
  }
}