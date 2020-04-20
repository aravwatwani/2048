import java.util.Random;

public class Board {

	private int[][] board; // holds state of game
	private Random rnd = new Random(0); // setup random # generator
	private int tilesOccupied; // tiles occupied to use populate function

	/* default constructor for board */
	// constructors must match exactly the name
	// of the class.
	public Board() {
		// instantiate the board
		tilesOccupied = 0;
		board = new int[4][4];
		populateOne(); // populates board
		populateOne(); // populates board
	}

	/*
	 * return a String representation of the 2D array board
	 * 
	 * each row should be in its own line
	 * 
	 * Example:
	 * 
	 * { {1, 2, 3}, {4, 5, 6}} -> 1 2 3
	 * 
	 * 4 5 6
	 */

	// overriding a method is when a child
	// class implement is the exact same method
	// that its parent class has
	public String toString() {

		/*
		 * Use the String formatter to pad the numbers with leading 0s so that the print
		 * out does not become jagged An example is shown below. String str =
		 * String.format("%04d", 9); // 0009 int x = 30;
		 * System.out.println(String.format("%04d",x));
		 */
		String str = "";
		for (int r = 0; r < board.length; r++) {

			for (int c = 0; c < board[0].length; c++) {
				str += board[r][c] + "";
			}

		}

		return str;
	}

	/*
	 * set one of the empty spaces (at random)
	 * 
	 * to a 2 or 4 (90/10 chance). an empty spot is defined to be a 0 element
	 * 
	 * Must use the Random class object rnd.
	 * 
	 * Example Use of rnd object.
	 * 
	 * int randomNum = rnd.nextInt(10); //returns a number in range [0 10) (not
	 * inclusive on the 10)
	 */

	public void populateOne() {
		// System.out.println(tilesOccupied);
		// find an empty space on the board and randomly populate either a 2 or
		// 4 at that location
		if (tilesOccupied == 16)
			return;
		boolean check = false; // boolean to check if there is empty space
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (board[i][j] == 0) {
					check = true;
					break;
				}
			}
		}

		int r = (int) (rnd.nextInt(4)); // generates random integer
		int c = (int) (rnd.nextInt(4));
		while (board[r][c] != 0 && check == true) {
			r = (int) (rnd.nextInt(4));
			c = (int) (rnd.nextInt(4));
		}

		if (Math.random() < 0.9) { // probability generator
			board[r][c] = 2;
		} else {
			board[r][c] = 4;
		}
		tilesOccupied++;
	}

	public void slideRight(int[] row) {

		for (int i = row.length - 1; i >= 0; i--) {
			// check if spot is open
			if (row[i] == 0) {
				// open, find the next non-zero element
				for (int j = i - 1; j >= 0; j--) {
					if (row[j] != 0) {
						// swap j and i
						int temp = row[i];
						row[i] = row[j];
						row[j] = temp;
						break;
					}
				}
			}
		}
	}

	public void slideRight() { // slides right so no space on left
		// go through 2D array, move all digits as far right as possible
		for (int i = 0; i < board.length; i++) {
			slideRight(board[i]);
		}

	}

	public void slideLeft(int[] arr) {

		// find the first open left most spot (a zero element)
		// then find the first non zero element and swap

		for (int i = 0; i < arr.length; i++) {
			// check if spot is open

			if (arr[i] == 0) {
				// open, find the next non-zero element
				for (int j = i + 1; j < arr.length; j++) {
					if (arr[j] != 0) {
						// swap j and i
						int temp = arr[i];
						arr[i] = arr[j];
						arr[j] = temp;
						break;
					}
				}
			}
		}

	}

	public void slideLeft() { // slides left so no space on right
		// grabbing row from 2d array
		// call the slideLeft method that takes in one argument
		for (int i = 0; i < board.length; i++) {
			slideLeft(board[i]);
		}

	}

	public int[] getCol(int[][] data, int c) {
		// returns a 1D array from 2D representing the elements in column
		int[] col = new int[data.length];
		for (int i = 0; i < data.length; i++) {
			col[i] = data[i][c];
		}
		return col;
	}

	public void slideUp(int[] arr) {

		// find the first open left most spot (a zero element)
		// then find the first non zero element and swap

		for (int i = 0; i < arr.length; i++) {
			// check if spot is open

			if (arr[i] == 0) {
				// open, find the next non-zero element
				for (int j = i + 1; j < arr.length; j++) {
					if (arr[j] != 0) {
						// swap j and i
						int temp = arr[i];
						arr[i] = arr[j];
						arr[j] = temp;
						break;
					}
				}
			}
		}

	}

	public void slideUp() { // slides up so no space on bottom

		for (int col = 0; col < 4; col++) {
			int arr[] = getCol(board, col); // turns column to 1d array to manipulate
			for (int row = 0; row < 4; row++) {
				slideUp(arr);
				board[row][col] = arr[row];
			}

		}
	}

	public void slideDown(int[] arr) {

		for (int i = arr.length - 1; i >= 0; i--) {
			// check if spot is open
			if (arr[i] == 0) {
				// open, find the next non-zero element
				for (int j = i - 1; j >= 0; j--) {
					if (arr[j] != 0) {
						// swap j and i
						int temp = arr[i];
						arr[i] = arr[j];
						arr[j] = temp;
						break;
					}
				}
			}
		}
	}

	public void slideDown() { // slides down so no space on top
		for (int col = 0; col < 4; col++) {
			int arr[] = getCol(board, col);
			for (int row = 0; row < 4; row++) {
				slideDown(arr);
				board[row][col] = arr[row];
			}

		}
	}

	public void combineRight() {
		for (int i = 0; i < board.length; i++) {
			for (int j = board[i].length - 1; j >= 1; j--) {
				if (board[i][j] == board[i][j - 1] && board[i][j] != 0) { // check if two numbers before combined are
																			// the same
					board[i][j] *= 2; // combines
					board[i][j - 1] = 0; // sets initial place to 0
					tilesOccupied--; // reduces the tile occupied
				}
			}
		}
	}

	public void combineLeft() {

		for (int i = 0; i < board.length; i++) {
			for (int j = 1; j < board[i].length; j++) {
				if (board[i][j] == board[i][j - 1] && board[i][j] != 0) { // check if two numbers before combined are
																			// the same
					board[i][j - 1] *= 2; // combines
					board[i][j] = 0; // sets initial place to 0
					tilesOccupied--; // reduces the tile occupied
				}
			}
		}
	}

	private void combineUp() {
		for (int i = 1; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i - 1][j] == board[i][j] && board[i][j] != 0) { // check if two numbers before combined are
																			// the same
					board[i - 1][j] *= 2; // combines
					board[i][j] = 0; // sets initial place to 0
					tilesOccupied--; // reduces the tile occupied
				}
			}
		}
	}

	private void combineDown() {
		for (int i = 0; i < board.length - 1; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == board[i + 1][j] && board[i][j] != 0) { // check if two numbers before combined are
																			// the same
					board[i + 1][j] *= 2; // combines
					board[i][j] = 0; // sets initial place to 0
					tilesOccupied--; // reduces the tile occupied
				}
			}
		}
	}

	public void left() {
		if (canMove(0)) {
			slideLeft(); // high level command to simplify when left key is pressed
			combineLeft();
			slideLeft();
			populateOne();
		}
	}

	public void right() {
		if (canMove(1)) {
			slideRight(); // high level command to simplify when right key is pressed
			combineRight();
			slideRight();
			populateOne();
		}
	}

	public void up() {
		if (canMove(2)) {
			slideUp(); // high level command to simplify when up key is pressed
			combineUp();
			slideUp();
			populateOne();
		}
	}

	public void down() {
		if (canMove(3)) {
			slideDown(); // high level command to simplify when down key is pressed
			combineDown();
			slideDown();
			populateOne();
		}
	}

	public boolean canMove(int d) {
		if (d == 0) {
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[0].length - 1; j++) {
					if (board[i][j] == board[i][j + 1] || (board[i][j] == 0 && board[i][j + 1] > 0))
						return true;
				}
			}
			return false;
		} else if (d == 1) {
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[0].length - 1; j++) {
					if (board[i][j] == board[i][j + 1] || (board[i][j] > 0 && board[i][j + 1] == 0))
						return true;
				}
			}
			return false;
		} else if (d == 2) {
			for (int i = 0; i < board.length - 1; i++) {
				for (int j = 0; j < board[0].length; j++) {
					if (board[i][j] == board[i + 1][j] || (board[i][j] == 0 && board[i + 1][j] > 0))
						return true;
				}
			}
			return false;
		} else {
			for (int i = 0; i < board.length - 1; i++) {
				for (int j = 0; j < board[0].length; j++) {
					if (board[i][j] == board[i + 1][j] || (board[i][j] > 0 && board[i + 1][j] == 0))
						return true;
				}
			}
			return false;
		}
	}

	public boolean gameOver() {
		if (tilesOccupied != board.length * board[0].length) {
			return false;
		}

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length - 1; j++) {
				if (board[i][j] == board[i][j + 1]) {
					return false;
				}
			}
		}

		for (int i = 0; i < board.length - 1; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == board[i + 1][j]) {
					return false;
				}
			}
		}
		return true;
	}

	public int[][] getBoard() {
		return board;
	}

	public float[] flattenBoard() {
		float[] flat = new float[board.length * board[0].length];

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				flat[i * board.length + j] = board[i][j];
			}
		}
		return flat;
	}

	// populate with a given 2d array
	public void populate(int[][] arr) {
		board = new int[4][4];
		tilesOccupied = 0;
		for (int r = 0; r < arr.length; r++) {
			for (int c = 0; c < arr[r].length; c++) {
				if (board[r][c] != 0) {
					tilesOccupied++;
				}
				board[r][c] = arr[r][c];
			}
		}
		if (tilesOccupied == 0) {
			populateOne();
			populateOne();
		}
	}

}