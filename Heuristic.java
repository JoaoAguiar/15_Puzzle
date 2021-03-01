class Heuristic {
	public static void heuristicChoice(GameBoard board, GameBoard final_board, int choice, int search_type) {
		if(choice == 1 && search_type == 1) {
			board.cost = offSiteParts(board, final_board);
		}
		else if(choice == 2 && search_type == 1) {
			board.cost = manhattanDistance(board, final_board);
		}
	    else if(choice == 1 && search_type == 2) {
			board.cost = board.depth + offSiteParts(board, final_board);
		}
		else if(choice == 2 && search_type == 2) {
			board.cost = board.depth + manhattanDistance(board, final_board);
		}
		else {
	      System.out.println();
	      System.out.println("That heuristic option does not exist");

	      System.exit(0);
		}

		return;
	}

	public static int offSiteParts(GameBoard board, GameBoard final_board) {
		int outside_positions = 0;
	    int[] aux1_board = board.getBoard();
	    int[] aux2_board = final_board.getBoard();

	    for(int i=1; i<17; i++) {
	    	if(aux1_board[i] != aux2_board[i]) {
	    		outside_positions++;
	    	}
	    }

	    return outside_positions;
	}

	public static int manhattanDistance(GameBoard board, GameBoard final_board) {
	    int[] aux1_board = board.getBoard();
	    int[] aux2_board = final_board.getBoard();
		int[][] aux1_matrix = new int[5][5];
	    int[][] aux2_matrix = new int[5][5];
		int i = 1;
		int[] aux = new int[2];
		int result = 0;
		int value = 0;

		for(int x=1; x<5; x++) {
			for(int y=1; y<5; y++) {
				aux1_matrix[x][y] = aux1_board[i];
				aux2_matrix[x][y] = aux2_board[i];
				i++;
			}
		}

		for(int x=1; x<5; x++) {
			for(int y=1; y<5; y++) {
				value = aux1_matrix[x][y];
				aux = helper(aux2_matrix, value);

				if((value != 0) && (value != (x+y-1))) {
					result += (Math.abs(x-aux[0]) + Math.abs(y-aux[1]));
				}
			}
		}

		return result;
	}

	public static int[] helper(int matrix[][], int value) {
		int[] position = new int[2];

		for(int i=1; i<5; i++) {
			for(int j=1; j<5; j++) {
				if(matrix[i][j] == value) {
					position[0] = i;
					position[1] = j;

					return position;
				}
			}
	    }

	    System.out.println();
	    System.out.println("The board do not have the same numbers!!");
	    
	    System.exit(0);

	    return position;
	}
}
