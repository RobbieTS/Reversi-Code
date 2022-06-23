package wonglorentz.robbie;

import javafx.scene.control.Button;

/**
 *Button class that contains a row and column value
 */
public class AdvButton extends Button {

	private int col;
	private int row;
	
	public AdvButton(String s, int r, int c) {
		super(s);
		col = c;
		row = r;	
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * gets the column value
	 * @return column value
	 */
	public int getCol() {
		return col;
	}
	
	/**
	 * gets the row value
	 * @return row value
	 */
	public int getRow() {
		return row;
	}
}
