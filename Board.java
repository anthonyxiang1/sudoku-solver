package regSudoku;

public class Board {

	Tile[][] arr;

	// Initializes the Board
	public Board() {
		arr = new Tile[9][9];

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				arr[i][j] = new Tile("-");
			}
		}
	}

	// Get the hex string of the number
	public static String getHexStr(int num) {
		return Integer.toHexString(num).toUpperCase();
	}

	// Get the number of the hex string
	public static int getHexNum(String s) {
		return Integer.parseInt(s.toLowerCase(), 10);
	}

	// Tests if a specific Tile NOT YET PLACED is legal - by row, column, grid
	public boolean isLegal(int r, int c, Tile t) {
		int rowStartUnit = (r / 3) * 3;
		int colStartUnit = (c / 3) * 3;

		for (int i = 0; i < 9; i++)
			if (t.getLetter().equals(arr[i][c].getLetter())) 
				return false;

		for (int j = 0; j < 9; j++)
			if (t.getLetter().equals(arr[r][j].getLetter()))
				return false;

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (t.getLetter().equals(arr[rowStartUnit + i][colStartUnit + j].getLetter()))
					return false;

		return true;
	}

	// Tests if there are duplicate numbers on the current board
	public boolean isDuplicate() {
		for (int i = 0;i<9;i++) {
			for (int j =0;j<9;j++) {
				if(!arr[i][j].letter.equals("-")) {
					String s = arr[i][j].letter;
					arr[i][j].setLetter("-");

					if (!isLegal(i,j,new Tile(s))) {
						arr[i][j].setLetter(s);
						return true;
					}
					else
						arr[i][j].setLetter(s);
				}
			}
		}
		return false;
	}

	// Test if current board is legal to be solved
	public boolean isBoardLegal() {
		for (int i = 0;i<9;i++) {
			for (int j =0;j<9;j++) {
				if ((arr[i][j].letter.charAt(0)) != '-')
					if ((int) (arr[i][j].letter.charAt(0)) < 49 || (int) (arr[i][j].letter.charAt(0)) > 57)
						return false;

				if (arr[i][j].letter.length() != 1)
					return false;

				if (isDuplicate())
					return false;
			}
		}
		return true;
	}

	// Backtracking algorithm to fill the board
	public boolean solve(int r, int c) {
		if (r == 9) {
			r = 0;

			if (++c == 9)
				return true;
		}

		if (!arr[r][c].getLetter().equals("-")) {
			return solve(r + 1, c); 		// Go to the next if it's filled
		}

		for (int a = 1; a <= 9; ++a) {
			if (isLegal(r, c, new Tile(getHexStr(a)))) {

				arr[r][c].setLetter(getHexStr(a));
				if (solve(r + 1, c))  		// Move to the next number
					return true;
			}
		}

		arr[r][c].setLetter("-");
		return false;
	}
}