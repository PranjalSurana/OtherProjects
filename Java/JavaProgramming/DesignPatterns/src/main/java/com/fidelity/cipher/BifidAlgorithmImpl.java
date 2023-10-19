package com.fidelity.cipher;

import java.util.Objects;

import com.fidelity.cipher.tools.PolybiusSquare;

public class BifidAlgorithmImpl implements CipherAlgorithm {

	private PolybiusSquare psCiphertext;
	private int period;

	@Override
	public String encrypt(String message) {
		checkKey();
		if (message.contains(".")) {
			throw new IllegalArgumentException("Message may only contain upper & lower roman alphabet, numbers and space");
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < message.length(); i += period) {
			String chunk = message.substring(i, Math.min(message.length(), i + period));
			StringBuilder sbTopRow = new StringBuilder();
			StringBuilder sbBottomRow = new StringBuilder();
			for (char ch : chunk.toCharArray()) {
				PolybiusSquare.RowCol rc;
				try {
					rc = psCiphertext.findChar(ch);
				} catch (ArrayIndexOutOfBoundsException e) {
					throw new IllegalArgumentException("Message may only contain upper & lower roman alphabet, numbers and space");
				}
				sbTopRow.append(Integer.toString(rc.getRow()));
				sbBottomRow.append(Integer.toString(rc.getCol()));
			}
			sbTopRow.append(sbBottomRow);
			for (int j = 0; j < sbTopRow.length(); j+= 2) {
				sb.append(psCiphertext.charAt(Character.getNumericValue(sbTopRow.charAt(j)), 
						Character.getNumericValue(sbTopRow.charAt(j + 1))));
			}
		}
		return sb.toString();
	}

	@Override
	public String decrypt(String message) {
		checkKey();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < message.length(); i += period) {
			String chunk = message.substring(i, Math.min(message.length(), i + period));
			StringBuilder sbChunk = new StringBuilder();
			for (char ch : chunk.toCharArray()) {
				PolybiusSquare.RowCol rc = psCiphertext.findChar(ch);
				sbChunk.append(Integer.toString(rc.getRow()));
				sbChunk.append(Integer.toString(rc.getCol()));
			}
			for (int j = 0; j < sbChunk.length() / 2; j++) {
				int row = Character.getNumericValue(sbChunk.charAt(j));
				int col = Character.getNumericValue(sbChunk.charAt(j + (sbChunk.length() / 2)));
				sb.append(psCiphertext.charAt(row, col));
			}
		}
		return sb.toString();
	}

	private void checkKey() {
		if (Objects.isNull(psCiphertext)) {
			throw new IllegalStateException("Keys not set");
		}
	}

	@Override
	public void setKey(String key) {
		// In this case we will split the key on a comma
		String[] keys = key.split(",");
		if (keys.length != 2) {
			throw new IllegalArgumentException("Require key and period, separated by comma");
		}
		psCiphertext = new PolybiusSquare(keys[0]);
		try {
			period = Integer.parseInt(keys[1]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Period must be numeric", e);
		}
	}
	
	/*
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
	 *  H  e  l  l  o  _  W  o  r  l  d
	 * 34 10 61 61 64 03 07 64 67 61 54
	 * 
	 * Write them vertically:
	 *  3  1  6  6  6  0  0  6  6  6  5
	 *  4  0  1  1  4  3  7  4  7  1  4
	 * 
	 * Break at the "period", with period of 7:
	 *  3  1  6  6  6  0  0/ 6  6  6  5
	 *  4  0  1  1  4  3  7/ 4  7  1  4
	 * 
	 * Read off horizontally:
	 * 3166600401143766654714
	 * 
	 * Then divide into pairs and convert back using the square:
	 * 31 66 60 04 01 14 37 66 65 47 14
	 *  D  q  k  a  h  4  K  q  p  X  4
	 * 
	 * W h a t _ I s / _ T h e _ Q u / e s t i o n
	 * 0 0 0 7 0 3 0 / 0 0 0 1 0 4 7 / 1 0 7 1 6 6
	 * 7 1 4 0 3 5 6 / 3 0 1 0 3 4 1 / 0 6 0 2 4 3
	 * 
	 * 00070307140356 00010473010341 107166060243
	 *  T W _ W 4 _ g  T h a w h _ M  e u q s E P
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
	 *  H e l l o _ W / o r l d
	 *  3 5 6 6 0 0 4 / 0 6 6 5
	 *  0 4 1 1 1 7 5 / 1 6 1 3
	 *  
	 *  35660040411175 06651613
	 *   M r R P Q 1 y  V q 6 3
	 *   
	 */

}
