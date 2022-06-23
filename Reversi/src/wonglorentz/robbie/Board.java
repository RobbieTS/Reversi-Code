package wonglorentz.robbie;

public class Board {

	private int sideLength;
	private Cell[][] board;
	
	/**
	 * Board constructor
	 * @param sideLength is the number of spots along the side
	 */
	Board(int sideLength) {
		this.sideLength = sideLength;
		this.board = new Cell[sideLength][sideLength];
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length; y++) {
				board[x][y] = new Cell(CellState.EMPTY);
			}
		}
	}
	
	/**
	 * gets the length of the board
	 * @return length of side
	 */
	public int getLength() {
		return sideLength;
	}

	/**
	 * returns the current board
	 * @return board
	 */
	public Cell[][] getCell() {
		return board;
	}

	/**
	 * sets the starting pieces in the center
	 */
	public void setBoard() {
		int pos = sideLength / 2;
		board[pos - 1][pos - 1].setState(CellState.P1);
		board[pos][pos].setState(CellState.P1);
		board[pos][pos - 1].setState(CellState.P2);
		board[pos - 1][pos].setState(CellState.P2);
	}
	
	/**
	 * sets the CellState of each index to empty
	 */
	public void clearBoard() {
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length; y++) {
				board[x][y] = new Cell(CellState.EMPTY);
			}
		}
	}
	
	/**
	 * Displays the board
	 */
	public void display() {
		System.out.println("BOARD");
		for (int i = 0; i < sideLength; i++) {
			for (int j = 0; j < sideLength; j++) {
				System.out.printf("%s ", board[i][j]);
			}
			System.out.println();
		}
	}
	
	/**
	 * checks to see if input is in the valid range
	 * @param rowIndex is the row input
	 * @param colIndex is the column input
	 * @return true if within restrictions
	 */
	public boolean isValid(int rowIndex, int colIndex) {
		return (rowIndex >= 0 && rowIndex < sideLength) && (colIndex >= 0 && colIndex < sideLength);
	}

	//public void placePiece(int row, int col, CellState player) {
		//if (board[row - 1][col - 1].getState() == CellState.EMPTY) {
			//board[row - 1][col - 1].setState(player);
		//} else {
			//System.out.println("place full");
		//}
	//}

	/**
	 * checks to see if the selected placement is valid
	 * @param row is the row index
	 * @param col is the column index
	 * @param player is the state of the player (P1 or P2)
	 * @return whether the spot is valid or not
	 */
	public boolean validPlace(int row, int col, CellState player) {
		// doesn't run if spot already played
		if (board[row - 1][col - 1].getState() != CellState.EMPTY) {
			return false;
		}
		
		//Checks through the surrounding spots of the selected area
		for (int c = col - 2; c < col + 1; c++) {
			
			//prevents the column value from going out of bounds
			if (c < 0) {
				c++;
			} else if (c >= board.length) {
				break;
			}

			for (int r = row - 2; r < row + 1; r++) {
				
				//prevents the row value from going out of bounds
				if (r < 0) {
					r++;
				} else if (r >= board.length) {
					break;
				}
				
				//checks to see if the surrounding piece is the opposite players
				if (board[r][c].getState() != player && board[r][c].getState() != CellState.EMPTY) {

					// finds the difference in indexes to increase
					int indexR = r - (row - 1);
					int indexC = c - (col - 1);
					boolean valid = true;

					while (valid) {
						//return true if a similar piece is placed across from an opposite piece
						if (row - 1 + indexR < 0 || row - 1 + indexR >= board.length) {
							valid = false;
						} else if (col - 1 + indexC < 0 || col - 1 + indexC >= board.length) {
							valid = false;
						} else if (board[row - 1 + indexR][col - 1 + indexC].getState() == CellState.EMPTY) {
							valid = false;
						} else if (board[row - 1 + indexR][col - 1 + indexC].getState() == player) {
							return true;
						}

						indexR += r - (row - 1);
						indexC += c - (col - 1);

					}
				}

			}
		}
		return false;
	}
	
	/**
	 * Checks to see if there are any possible locations to move
	 * @param player is player going (P1 or P2)
	 * @return true if a valid place is found
	 */
	public boolean validTurn(CellState player) {
        for(int r = 1; r <= board.length; r++) {
            for(int c = 1; c <= board.length; c++) {
                if(board[r - 1][c - 1].getState() == CellState.EMPTY) {
                    if(validPlace(r, c, player)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
	
	/**
	 * Changes the state of the pieces in between two of similar colour
	 * @param row is the row index input
	 * @param col is the colun index input
	 * @param player is the state of the player going
	 */
	public void changePieces(int row, int col, CellState player) {

		for (int c = col - 2; c < col + 1; c++) {

			if (c < 0) {
				c++;
			} else if (c >= board.length) {
				break;
			}

			for (int r = row - 2; r < row + 1; r++) {

				if (r < 0) {
					r++;
				} else if (r >= board.length) {
					break;
				}

				if (board[r][c].getState() != player && board[r][c].getState() != CellState.EMPTY) {

					// set increments
					int indexR = r - (row - 1);
					int indexC = c - (col - 1);
					int count = 0;
					boolean valid = true;

					while (valid) {
						count++;

						if (row - 1 + indexR < 0 || row - 1 + indexR >= board.length) {
							valid = false;
						} else if (col - 1 + indexC < 0 || col - 1 + indexC >= board.length) {
							valid = false;
						} else if (board[row - 1 + indexR][col - 1 + indexC].getState() == CellState.EMPTY) {
							valid = false;
						} else if (board[row - 1 + indexR][col - 1 + indexC].getState() == player) {
							//When encountering another piece of the same colour, the program checks
							//indexes in reverse and flips pieces
							for (int a = count; a > 0; a--) {
								indexR -= r - (row - 1);
								indexC -= c - (col - 1);
								board[row - 1 + indexR][col - 1 + indexC].setState(player);

							}
							valid = false;
						}

						indexR += r - (row - 1);
						indexC += c - (col - 1);

					}
				}
			}
		}
	}
	
	/**
	 * Checks to see if the board is full
	 * @return true if the board is full
	 */
	public boolean isFull() {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				if (board[r][c].getState() == CellState.EMPTY) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * determines the winner
	 * @return who has more pieces as the winner
	 */
	public CellState getWinner() {
		int countP1 = 0;
		//counts the number of P1 pieces there are
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				if (board[r][c].getState() == CellState.P1) {
					countP1++;
				}
			}
		}
		//compares the number of P1 pieces with the total number of spots to deetermine the winner
		if (countP1 > (board.length * board.length) / 2) {
			return CellState.P1;
		} else if (countP1 < (board.length * board.length) / 2) {
			return CellState.P2;
		} else {
			return CellState.EMPTY;
		}
	}
}
