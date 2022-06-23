package wonglorentz.robbie;

import java.util.Scanner;

public class Driver {

	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {

		// Setup constants for the Board
		final int side = 8;

		// create the board
		Board board = new Board(side);
		board.setBoard();
		board.display();

		// console input
		boolean done = false;
		String value = "";
		int column = 0;
		int row = 0;
		CellState player = CellState.P1;

		while (!done) {

				column = getColumn(1, side);
				row = getRow(1, side);

				boolean valid = board.validPlace(row, column, player);
				if (valid) {


					board.changePieces(row, column, player);
					board.display();

					if (board.isFull()) {
						CellState winner = board.getWinner();
						if (winner == CellState.EMPTY) {
							System.out.println("tie");
						} else {
							System.out.println(winner + "wins");
						}
						done = true;
					}
					
				// switch
				if (player == CellState.P1) {
					player = CellState.P2;
				} else {
					player = CellState.P1;
				}
			} else {
				System.out.println("invlaid");
			}
		}
	}

	private static int getColumn(int min, int max) {
		boolean valid = false;
		int col = 0;

		while (!valid) {
			System.out.println("input column");
			if (in.hasNextInt()) {
				col = in.nextInt();
				if (col >= min && col <= max) {
					valid = true;
				} else {
					System.out.println("invalid range");
				}
			} else {
				System.out.println("not a number");
			}
			in.nextLine();
		}
		return col;
	}

	private static int getRow(int min, int max) {
		boolean valid = false;
		int row = 0;

		while (!valid) {
			System.out.println("input row");
			if (in.hasNextInt()) {
				row = in.nextInt();
				if (row >= min && row <= max) {
					valid = true;
				} else {
					System.out.println("invalid range");
				}
			} else {
				System.out.println("not a number");
			}
			in.nextLine();
		}
		return row;
	}

}
