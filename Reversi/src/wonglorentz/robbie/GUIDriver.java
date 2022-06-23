package wonglorentz.robbie;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
/**
 * 
 * @author R. Wong-Lorentz & W. Quach
 * This program runs the board game Othello
 */
public class GUIDriver extends Application {

	final int WIDTH = 800;
	final int HEIGHT = 900;
	final int SIDE = 8;
	// create the board
	Board board = new Board(SIDE);
	CellState player = CellState.P1;
	boolean winner = false;
	private AdvButton[][] locations = new AdvButton[SIDE][SIDE];
	
	/**
	 * This switches the player placing a piece
	 */
	private void switchUser() {
		if (player == CellState.P1) {
			player = CellState.P2;
		} else if (player == CellState.P2) {
			player = CellState.P1;
		}
	}
	
	/**
	 * Updates the board appearance, (changes button colour)
	 * @param board is the area where pieces are played
	 * @param locations is the array of buttons
	 */
	private void update(Board board, Button[][] locations) {
		Cell[][] cells = board.getCell();
		for (int r = 0; r < cells.length; r++) {
			for (int c = 0; c < cells[0].length; c++) {
				if (cells[r][c].getState() == CellState.P1) {
					locations[r][c].setStyle("-fx-background-color: #000000; -fx-background-radius: 100px; ");

				} else if (cells[r][c].getState() == CellState.P2) {
					locations[r][c].setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 100px; ");

				} else {
					locations[r][c].setStyle("-fx-background-color: transparent; ");
				}

			}

		}
	}

	// private int getCol(Button button) {

	// }
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub

		board.display();
		
		//create components of interface to be displayed
		VBox screen = new VBox();
		HBox columnSelector = new HBox(5);
		columnSelector.setAlignment(Pos.CENTER);

		Rectangle menu = new Rectangle(800, 100);
		menu.setFill(Color.WHEAT);
		Font font = new Font(20);

		Button newGame = new Button("RESET GAME");
		newGame.setFont(font);

		Label title = new Label("REVERSI");
		title.setFont(font);

		Label playerState = new Label("Player 1");
		playerState.setFont(font);

		Label popMsg = new Label("PLACE PIECE");
		popMsg.setFont(font);

		HBox HMenuBox = new HBox(50);
		HMenuBox.setAlignment(Pos.CENTER);

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);

		board.setBoard();

		//Creates an array of buttons that contain a row and column value
		for (int r = 0; r < SIDE; r++) {
			for (int c = 0; c < SIDE; c++) {
				// Create a Button and add it to the grid
				AdvButton button = new AdvButton("", r, c);
				button.setStyle("-fx-background-color: transparent; -fx-background-radius: 100px; ");

				button.setMinSize(WIDTH / SIDE - 7, WIDTH / SIDE - 7);

				locations[r][c] = button;
				grid.setHgap(5);
				grid.setVgap(5);
				grid.add(button, c, r);

				button.setOnAction(e -> {
					if (!board.isFull()) {

						if (board.validTurn(player)) {

							//gets column and row from button input
							int col = ((AdvButton) e.getSource()).getCol() + 1;
							int row = ((AdvButton) e.getSource()).getRow() + 1;
							
							//checks to see is the player selects a valid spot
							boolean valid = board.validPlace(row, col, player);
							if (valid) {
								
								//places pieces and flips the required pieces
								board.changePieces(row, col, player);
								board.display();

								update(board, locations);
								
								//outputs appropriate message
								if (player == CellState.P1) {
									playerState.setText("Player 2");
								} else {
									playerState.setText("Player 1");
								}

								popMsg.setText("PLACE PIECE");

								// Determines winner and outputs appropriate message
								if (board.isFull()) {
									CellState winner = board.getWinner();
									if (winner == CellState.EMPTY) {
										popMsg.setText("TIE");
									} else if (winner == CellState.P1) {
										popMsg.setText("PLAYER 1 WINS!");
									} else {
										popMsg.setText("PLAYER 2 WINS!");
									}
								}
								switchUser();
							} else {
								popMsg.setText("INVALID MOVE");
							}
						} else {
							switchUser();
							popMsg.setText("TURN PASSED");
						}
					}
				});

			}
		}
		
		//resets the board
		newGame.setOnAction(e -> {
			board.clearBoard();
			board.setBoard();
			update(board, locations);

			player = CellState.P1;
			playerState.setText("Player 1");
			popMsg.setText("Place Piece");
		});

		update(board, locations);
		
		//Adds components to the interface
		BackgroundFill backF = new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY);
		Background Back = new Background(backF);
		screen.setBackground(Back);
		HMenuBox.getChildren().addAll(title, newGame, playerState, popMsg);
		screen.getChildren().addAll(new StackPane(menu, HMenuBox), grid);

		Scene scene = new Scene(screen, WIDTH, HEIGHT, Color.GREEN);
		// Add Scene to stage
		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
