package com.fidelity.cipher;

import java.util.Objects;

import com.fidelity.cipher.tools.PolybiusSquare;

public class FourSquareAlgorithmImpl implements CipherAlgorithm {
	
	private PolybiusSquare psPlaintext;
	private PolybiusSquare psCiphertext1;
	private PolybiusSquare psCiphertext2;

	@Override
	public String encrypt(String message) {
		checkKey();
		if (message.contains(".")) {
			throw new IllegalArgumentException("Message may only contain upper & lower roman alphabet, numbers and space");
		}

		return convert(message, psPlaintext, psCiphertext1, psCiphertext2, psPlaintext).toString();
	}

	@Override
	public String decrypt(String message) {
		checkKey();
		if (message.length() % 2 == 1) {
			throw new IllegalArgumentException("Four-square messages to be decrypted must have even length");
		}
		StringBuilder plaintext = convert(message, psCiphertext1, psPlaintext, psPlaintext, psCiphertext2);

		if (plaintext.charAt(plaintext.length() - 1) == '.') {
			plaintext.deleteCharAt(plaintext.length() - 1);
		}
		return plaintext.toString();
	}
	
	private StringBuilder convert(String message,
			PolybiusSquare psTopLeft, PolybiusSquare psTopRight, 
			PolybiusSquare psBottomLeft, PolybiusSquare psBottomRight) {
		StringBuilder text = new StringBuilder();

		for (int i = 0; i < message.length(); i += 2) {
			char char1 = message.charAt(i);
			char char2 = ((i + 1) == message.length()) ? '.' : message.charAt(i + 1);
			
			PolybiusSquare.RowCol rc1 = psTopLeft.findChar(char1);
			PolybiusSquare.RowCol rc2 = psBottomRight.findChar(char2);

			char cchar1 = psTopRight.charAt(rc1.getRow(), rc2.getCol());
			char cchar2 = psBottomLeft.charAt(rc2.getRow(), rc1.getCol());
			
			text.append(cchar1);
			text.append(cchar2);
		}
		return text;
	}

	private void checkKey() {
		if (Objects.isNull(psCiphertext1)) {
			throw new IllegalStateException("Keys not set");
		}
	}

	@Override
	public void setKey(String key) {
		psPlaintext = new PolybiusSquare("");

		// In this case we will split the key on a comma
		String[] keys = key.split(",");
		if (keys.length != 2) {
			throw new IllegalArgumentException("Two keys required, separated by comma");
		}
		psCiphertext1 = new PolybiusSquare(keys[0]);
		psCiphertext2 = new PolybiusSquare(keys[1]);
	}
	
	/*
	 * The four square cipher uses four grids arranged in a square to encrypt digrams.
	 * https://en.wikipedia.org/wiki/Four-square_cipher. Two of the grids contain
	 * plaintext and two are keyed.
	 * 
	 * The first character of the digram is located in the upper left plaintext grid
	 * and the second in the bottom right plaintext grid. The encrypted characters are
	 * read from the opposite corners of the rectangle, starting with the character on
	 * the same line as the first character of the digram.
	 * 
	 * Because of the way these grids are arranged, there are no issues of repeated
	 * characters or characters in the same row or column, as there are with Playfair.
	 * 
	 * As usual, this is an extension including upper and lower case, digits and space.
	 * 
	 * With key = ThE_aNsWeR_iS_42,RoYgBiV the grid becomes:
	 * 
	 *  _ 0 1 2 3 4 5 6        T h E _ a N s W
	 *  7 8 9 A B C D E        e R i S 4 2 0 1
	 *  F G H I J K L M        3 5 6 7 8 9 A B
	 *  N O P Q R S T U        C D F G H I J K
	 *  V W X Y Z a b c        L M O P Q U V X
	 *  d e f g h i j k        Y Z b c d f g j
	 *  l m n o p q r s        k l m n o p q r
	 *  t u v w x y z .        t u v w x y z .
	 *  
	 *  R o Y g B i V _        _ 0 1 2 3 4 5 6
	 *  0 1 2 3 4 5 6 7        7 8 9 A B C D E
	 *  8 9 A C D E F G        F G H I J K L M
	 *  H I J K L M N O        N O P Q R S T U
	 *  P Q S T U W X Z        V W X Y Z a b c
	 *  a b c d e f h j        d e f g h i j k
	 *  k l m n p q r s        l m n o p q r s
	 *  t u v w x y z .        t u v w x y z .
	 * 
	 * Hello World:
	 * 
	 * He -> 5c
	 * ll -> kk
	 * o_ -> kg
	 * Wo -> Pl
	 * rl -> kr
	 * d. -> jt
	 * 
	 * 
	 * What Is The Question:
	 * 
	 * Wh -> Qb
	 * at -> Ly
	 * _I -> _8
	 * s_ -> k_
	 * Th -> Hh
	 * e_ -> Yo
	 * Qu -> Dw
	 * es -> jl
	 * ti -> ya
	 * on -> mn
	 *
	 *
	 * If we reverse the keys, we get this:
	 * 
	 *  _ 0 1 2 3 4 5 6        R o Y g B i V _  
	 *  7 8 9 A B C D E        0 1 2 3 4 5 6 7  
	 *  F G H I J K L M        8 9 A C D E F G  
	 *  N O P Q R S T U        H I J K L M N O  
	 *  V W X Y Z a b c        P Q S T U W X Z  
	 *  d e f g h i j k        a b c d e f h j  
	 *  l m n o p q r s        k l m n p q r s  
	 *  t u v w x y z .        t u v w x y z .  
	 *  
	 *  T h E _ a N s W        _ 0 1 2 3 4 5 6
	 *  e R i S 4 2 0 1        7 8 9 A B C D E
	 *  3 5 6 7 8 9 A B        F G H I J K L M
	 *  C D F G H I J K        N O P Q R S T U
	 *  L M O P Q U V X        V W X Y Z a b c
	 *  Y Z b c d f g j        d e f g h i j k
	 *  k l m n o p q r        l m n o p q r s
	 *  t u v w x y z .        t u v w x y z .
	 * 
	 *  He -> 9b
	 *  ll -> kk
	 *  o_ -> k_
	 *  Wo -> Tl
	 *  rl -> kq
	 *  d. -> jt
	 *  
	 *  Which is similar, but not the same!
	 */

}
