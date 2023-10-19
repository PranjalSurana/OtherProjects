package com.fidelity.cipher;

import java.util.Objects;

import com.fidelity.cipher.tools.PolybiusSquare;

public class PlayfairAlgorithmImpl implements CipherAlgorithm {

	/*
	 * When the two letters of a digram are on the same row or column, we use the offset
	 * instead of finding the opposite corners of a rectangle. Note that in modulo 8
	 * arithmetic, adding 7 is the same as subtracting 1, but without messy edge tests.
	 */
	private static final int ENCRYPTION_OFFSET = 1;
	private static final int DECRYPTION_OFFSET = PolybiusSquare.MATRIX_SIZE - ENCRYPTION_OFFSET;
	
	private PolybiusSquare ps;

	private class Digram {
		char char1;
		char char2;
		
		public Digram(char char1, char char2) {
			this.char1 = char1;
			this.char2 = char2;
		}
	}
	
	@Override
	public String encrypt(String message) {
		checkKey();
		if (message.contains(".")) {
			throw new IllegalArgumentException("Message may only contain upper & lower roman alphabet, numbers and space");
		}

		// Use StringBuilder because we may add characters
		StringBuilder sb = new StringBuilder(message);
		StringBuilder ciphertext = new StringBuilder();

		// Cannot pre-store length of string because it may change, clearly we could optimise
		for (int i = 0; i < sb.length(); i += 2) {
			char char1 = sb.charAt(i);
			char char2 = ((i + 1) == sb.length()) ? '.' : sb.charAt(i + 1);
			if (char1 == char2) {
				sb.insert(i + 1, '.');
				char2 = '.';
			}
			
			Digram d = processDigram(char1, char2, ENCRYPTION_OFFSET);
			ciphertext.append(d.char1);
			ciphertext.append(d.char2);
		}
		return ciphertext.toString();
	}

	/*
	 * There are a number of optimizations possible here, but this algorithm seems like the simplest
	 */
	private Digram processDigram(char char1, char char2, int offset) {
		PolybiusSquare.RowCol rc1 = ps.findChar(char1);
		PolybiusSquare.RowCol rc2 = ps.findChar(char2);

		int row1 = rc1.getRow();
		int row2 = rc2.getRow();
		int col1 = rc1.getCol();
		int col2 = rc2.getCol();

		if (row1 == row2) {
			col1 = (col1 + offset) % PolybiusSquare.MATRIX_SIZE;
			col2 = (col2 + offset) % PolybiusSquare.MATRIX_SIZE;
		} else if (col1 == col2) {
			row1 = (row1 + offset) % PolybiusSquare.MATRIX_SIZE;
			row2 = (row2 + offset) % PolybiusSquare.MATRIX_SIZE;
		} else {
			col1 = col2;
			col2 = rc1.getCol();
		}

		return new Digram(ps.charAt(row1, col1), ps.charAt(row2, col2));
	}

	@Override
	public String decrypt(String message) {
		checkKey();
		if (message.length() % 2 == 1) {
			throw new IllegalArgumentException("Playfair messages to be decrypted must have even length");
		}
		StringBuilder text = new StringBuilder();

		for (int i = 0; i < message.length(); i += 2) {
			char char1 = message.charAt(i);
			char char2 = message.charAt(i + 1);

			Digram d = processDigram(char1, char2, DECRYPTION_OFFSET);
			text.append(d.char1);
			if (d.char2 != '.') {
				text.append(d.char2);
			}
		}
		return text.toString();
	}
	
	private void checkKey() {
		if (Objects.isNull(ps)) {
			throw new IllegalStateException("Key not set");
		}
	}

	@Override
	public void setKey(String key) {
		ps = new PolybiusSquare(key);
	}

	/*
	 * This is an adaptation of the Playfair cipher (https://en.wikipedia.org/wiki/Playfair_cipher).
	 * 
	 * The Playfair cipher was the first to encode digrams (pairs of letters), thus significantly increasing
	 * the difficulty of frequency analysis. In the original cipher, the 26 letters are arranged in a 5 x 5
	 * grid (with I & J occupying the same space, or Q omitted). The grid is a Polybius Square, so it is treated 
	 * as such. The message to be encoded is split into digrams, which are encoded using 4 simple rules:
	 * 
	 * 1. If any digram contains a repeated letter, or only one letter (end of message), then write an X after
	 *    the first letter of the pair and carry on. Note that this may shift the remaining letters to the right.
	 * 2. If the letters appear on the same row of the grid, replace them with the letters immediately to the
	 *    right. Wrap around from right to left on the same row, if necessary.
	 * 3. If the letters appear in the same column, replace them with letters immediately below. Wrap if needed.
	 * 4. Otherwise, treat as two corners of a rectangle and replace them with the letters in the opposite corners.
	 *    The first letter of the encrypted pair is the one on the same row as the first letter of the digram.
	 * 
	 * This means that there may be stray Xs in a decrypted message, and I/J are uncertain or there are missing
	 * Qs. This is not a problem for human decryption.
	 * 
	 * However, this adaptation uses the upper & lower case letters, numbers and space (63 characters). In addition,
	 * it uses the full stop or period (.) in place of X in rule 1. The full stop may appear in the key, but not
	 * in the message and any occurrences in a decrypted message are removed. This makes 64 characters, which are
	 * arranged in an 8 x 8 grid.

	 * In all the tables that follow, underscore (_) stands for space.
	 * 
	 * _0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.
	 * 
	 * With key = ThE_aNsWeR_iS_42, the grid becomes:
	 * 
	 *  T h E _ a N s W
	 *  e R i S 4 2 0 1
	 *  3 5 6 7 8 9 A B
	 *  C D F G H I J K
	 *  L M O P Q U V X
	 *  Y Z b c d f g j
	 *  k l m n o p q r
	 *  t u v w x y z .
	 *  
	 *  Hello_World -> He l. lo _W or ld
	 *  He -> C4 (rule 4)
	 *  l. -> ru (rule 1, rule 4)
	 *  lo -> mp (rule 2)
	 *  _W -> aT (rule 2)
	 *  or -> pk (rule 2)
	 *  ld -> oZ (rule 4)
	 *  
	 *  ciphertext = C4rumpaTpkoZ
	 *  
	 *  What_Is_The_Question -> Wh at _I s_ Th e_ Qu es ti on
	 *  Wh -> TE
	 *  at -> Tx
	 *  _I -> NG
	 *  s_ -> Wa
	 *  Th -> hE
	 *  e_ -> ST
	 *  Qu -> Mx
	 *  es -> 0T
	 *  ti -> ve
	 *  on -> po
	 *  
	 *  ciphertext = TETxNGWahESTMx0Tvepo
	 * 
	 *  
	 *  With key = RoYgBiV, the grid becomes:
	 *  
	 *  R o Y g B i V _
	 *  0 1 2 3 4 5 6 7
	 *  8 9 A C D E F G
	 *  H I J K L M N O
	 *  P Q S T U W X Z
	 *  a b c d e f h j
	 *  k l m n p q r s
	 *  t u v w x y z .
	 *  
	 *  Hello_World -> He l. lo _W or ld
	 *  He -> La (rule 4)
	 *  l. -> su (rule 1, rule 4)
	 *  lo -> u1 (rule 3)
	 *  _W -> iZ (rule 4)
	 *  or -> Vl (rule 4)
	 *  ld -> nb (rule 4)
	 *  
	 *  ciphertext = Lasuu1iZVlnb
	 *  
	 *  Short -> Sh or t.
	 *  Sh -> Xc (rule 4)
	 *  or -> Vl (rule 4)
	 *  t. -> ut (rule 2)
	 *  
	 *  ciphertext = XcVlut
	 */
}
