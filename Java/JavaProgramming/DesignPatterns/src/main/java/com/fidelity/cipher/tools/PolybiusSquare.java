package com.fidelity.cipher.tools;

public class PolybiusSquare {

	private static final String PLAINTEXT = " 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.";
	
	public static final int MATRIX_SIZE = 8;
	
	private char[][] matrix = new char[MATRIX_SIZE][MATRIX_SIZE];
	private RowCol[] positions = new RowCol[PLAINTEXT.length()];
	
	public static class RowCol {
		private int row;
		private int col;

		public RowCol(int row, int col) {
			super();
			this.row = row;
			this.col = col;
		}

		public int getRow() {
			return row;
		}

		public int getCol() {
			return col;
		}
	}

	public PolybiusSquare(String key) {
		key += PLAINTEXT;
		for (int i = 0, k = 0; i < key.length(); ++i) {
			char ch = key.charAt(i);
	        int index = PLAINTEXT.indexOf(ch);
	        if (index == -1) {
	        	throw new IllegalArgumentException("Key may only contain upper & lower roman alphabet, numbers and space");
	        }
	        // If we haven't seen this character before, add it to the matrix
	        if (positions[index] == null) {
	        	matrix[k / MATRIX_SIZE][k % MATRIX_SIZE] = ch;
	        	positions[index] = new RowCol(k / MATRIX_SIZE, k % MATRIX_SIZE);
	        	k++;
	        }
		}
	}

	public RowCol findChar(char ch) {
		int index = PLAINTEXT.indexOf(ch);
		return positions[index];
	}

	public char charAt(RowCol rc) {
		return charAt(rc.row, rc.col);
	}

	public char charAt(int row, int col) {
		return matrix[row][col];
	}

//	private void printMatrix() {
//		for (int i = 0; i < MATRIX_SIZE; i++) {
//			for (int j = 0; j < MATRIX_SIZE; j++) {
//				System.out.print(matrix[i][j] + " ");
//			}
//			System.out.println("");
//		}
//	}
	
	/*
	 * This is an implementation of the Polybius Square using the extended alphabet common to 
	 * all these algorithms. https://en.wikipedia.org/wiki/Polybius_square
	 * 
	 * In all the tables that follow, underscore (_) stands for space.
	 * 
	 * _0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.
	 * 
	 * With key = ThE_aNsWeR_iS_42, the grid becomes:
	 * 
	 *    0 1 2 3 4 5 6 7
	 *    ---------------
	 * 0: T h E _ a N s W
	 * 1: e R i S 4 2 0 1
	 * 2: 3 5 6 7 8 9 A B
	 * 3: C D F G H I J K
	 * 4: L M O P Q U V X
	 * 5: Y Z b c d f g j
	 * 6: k l m n o p q r
	 * 7: t u v w x y z .
	 * 
	 * 
	 *  With key = RoYgBiV, the grid becomes:
	 *  
	 *    0 1 2 3 4 5 6 7
	 *    ---------------
	 * 0: R o Y g B i V _
	 * 1: 0 1 2 3 4 5 6 7
	 * 2: 8 9 A C D E F G
	 * 3: H I J K L M N O
	 * 4: P Q S T U W X Z
	 * 5: a b c d e f h j
	 * 6: k l m n p q r s
	 * 7: t u v w x y z .
	 * 
	 * With no key:
	 * 
	 *    0 1 2 3 4 5 6 7
	 *    ---------------
	 * 0: _ 0 1 2 3 4 5 6
	 * 1: 7 8 9 A B C D E
	 * 2: F G H I J K L M
	 * 3: N O P Q R S T U
	 * 4: V W X Y Z a b c
	 * 5: d e f g h i j k
	 * 6: l m n o p q r s
	 * 7: t u v w x y z .
	 * 
	 */

}
