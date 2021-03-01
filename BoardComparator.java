import java.util.*;

class BoardComparator implements Comparator<GameBoard> {
	// Compara os tabuleiros pelo seu custo
	public int compare(GameBoard board1, GameBoard board2) {
	    if(board1.cost > board2.cost) {
	      return 1;
	    }
	    else if(board1.cost < board2.cost) {
	      return -1;
	    }

	    return 0;
	}
}
