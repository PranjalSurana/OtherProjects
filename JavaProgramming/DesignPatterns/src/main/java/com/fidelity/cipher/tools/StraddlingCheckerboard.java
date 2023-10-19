package com.fidelity.cipher.tools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StraddlingCheckerboard {

	private static final String PLAINTEXT = "etaoinsrhdlucETAOINSRHDLUC 0123456789mfywgpbvkxqjzMFYWGPBVKXQJZ";

	private static final int BOARD_LENGTH = 10;
	private static final int BOARD_DEPTH = 7;

	private String[] rowKeys = new String[BOARD_DEPTH];

	private Map<Character, String> encoding;
	private Map<String, Character> decoding;
	
	public StraddlingCheckerboard(String key) {
		buildCheckerboard(key);
	}

	/*
	 * Counter-intuitively, this method doesn't actually build a checkerboard. Instead,
	 * it loops through a notional board and uses the positions to define two maps (one
	 * for encoding and one for decoding) that contain all the checkerboard positional
	 * information.
	 */
	private void buildCheckerboard(String key) {
		encoding = new HashMap<>();
		decoding = new HashMap<>();

		// Note this method also populates rowKeys[]
		int[][] nullPositions = generateKeys(key);

		int positionInPlaintext = 0;
		for (int i = 0; i < BOARD_DEPTH; i++) {
			for (int j = 0; j < BOARD_LENGTH; j++) {
				if (!arrayContains(nullPositions[i], j)) {
					Character ch = PLAINTEXT.charAt(positionInPlaintext++);
					encoding.put(ch, rowKeys[i] + Integer.toString(j));
					decoding.put(rowKeys[i] + Integer.toString(j), ch);
				}
			}
		}
	}

	private boolean arrayContains(final int[] array, int valueToFind) {
		if (array == null) {
			return false;
		}
		for (int i = 0; i < array.length; i++) {
			if (valueToFind == array[i]) {
				return true;
			}
		}
		return false;
	}

	/*
	 * The String key passed here is a string of digits that represent the position of
	 * empty spaces in the checkerboard. Empty spaces are represented by null, to 
	 * distinguish them from the space character. Where there is a null on the board,
	 * it indicates that the position really refers to one of the extension rows (except
	 * on the last row, where it just allows us to position the empty value wherever we
	 * want - rather than always in the last position).
	 * 
	 * The first two digits of the key refer to the nulls on the first row, next two to
	 * the nulls on the second row (first extension row), next two to the nulls on the
	 * third row (second extension row) and the last digit is the position of the null
	 * in the last row.
	 */
	private int[][] generateKeys(String key) {
		Objects.requireNonNull(key, "Key may not be null");
		if (!key.matches("[\\d]{7}")) {
			throw new IllegalArgumentException("Key must be 7 digits long");
		}
		String[] keys = key.split("");
		rowKeys[0] = "";
		rowKeys[1] = keys[0];
		rowKeys[2] = keys[1];
		rowKeys[3] = rowKeys[1] + keys[2];
		rowKeys[4] = rowKeys[1] + keys[3];
		rowKeys[5] = rowKeys[2] + keys[4];
		rowKeys[6] = rowKeys[2] + keys[5];
		
		int[][] nullPositions = new int[BOARD_DEPTH][];
		nullPositions[0] = new int[2];
		nullPositions[0][0] = Integer.parseInt(keys[0]);
		nullPositions[0][1] = Integer.parseInt(keys[1]);
		nullPositions[1] = new int[2];
		nullPositions[1][0] = Integer.parseInt(keys[2]);
		nullPositions[1][1] = Integer.parseInt(keys[3]);
		nullPositions[2] = new int[2];
		nullPositions[2][0] = Integer.parseInt(keys[4]);
		nullPositions[2][1] = Integer.parseInt(keys[5]);
		nullPositions[3] = new int[0];
		nullPositions[4] = new int[0];
		nullPositions[5] = new int[0];
		nullPositions[6] = new int[1];
		nullPositions[6][0] = Integer.parseInt(keys[6]);
		
		return nullPositions;
	}

	// Although using a Map makes encoding and decoding simplest, arrays would probably be faster
	public String findChar(char ch) {
		String s = encoding.get(ch);
		if (s == null) {
			throw new IllegalArgumentException("Character may only be upper & lower roman alphabet, numbers and space");
		}
		return s;
	}

	public char charAt(String s) {
		Character ch = decoding.get(s);
		if (ch == null) {
			throw new IllegalArgumentException("Unrecognized character code");
		}
		return ch.charValue();
	}
	
	public String encrypt(String plaintext) {
		StringBuilder sb = new StringBuilder();
		try {
			for (Character ch : plaintext.toCharArray()) {
				sb.append(findChar(ch));
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Message may only contain upper & lower roman alphabet, numbers and space", e);
		}
		return sb.toString();
	}

	public String decrypt(String ciphertext) {
		if (!ciphertext.matches("\\d+")) {
			throw new IllegalArgumentException("Ciphertext may only contain digits");
		}
		StringBuilder sb = new StringBuilder();
		Matcher m = createPattern().matcher(ciphertext);
		while (m.find()) {
			char ch = charAt(m.group(1));
			sb.append(ch);
		}
		return sb.toString();
	}
	
	/*
	 * Create a pattern that matches an encrypted character.
	 * 
	 * If the row keys are empty string (always the first key) 3, 5, 31, 37, 52 & 56,
	 * then the pattern would be: (56.|52.|37.|31.|5.|3.|.), which means that it matches a
	 * three digit number starting 56, a three digit number starting 52 etc in preference
	 * order, with the final pattern being a single digit.
	 */
	private Pattern createPattern() {
		String p = "";
		for (int i = rowKeys.length - 1; i >= 0; i--) {
			if (p.length() > 0) {
				p += "|";
			}
			p += rowKeys[i] + ".";
		}
		return Pattern.compile("(" + p + ")");
	}

	// Make safe copy rather than returning mutable array
	public String[] getRowKeys() {
		return Arrays.copyOf(rowKeys, BOARD_DEPTH);
	}


	/*
	 * A straddling checkerboard is a mechanism for converting plaintext to digits while also
	 * achieving fractionation and compression (relative to, say, the Polybius Square).
	 * https://en.wikipedia.org/wiki/VIC_cipher
	 * 
	 * A traditional checkerboard would contain the letters A-Z with, possibly, a numeric shift
	 * and another character of choice. It would be 3 rows of 10 digits. In the first row, two
	 * digits are missed out and these are used as an index into the other rows.
	 * 
	 * A character that appears in the first row is represented by the numeric value of the column
	 * that it appears in. No characters are in the two extension positions. A character in one of 
	 * the other rows is represented by the extension digit for that row (one of the two missing 
	 * values from the first row) and the position in the row. A character is thus represented by 
	 * one or two digits (and all two digit combinations start with one of the extension digits).
	 * 
	 * There is clearly an advantage of compression if the characters in the first row are the most 
	 * commonly used in the target language.
	 * 
	 * This version of the straddling checkerboard uses 7 rows of 10 characters. It uses two levels 
	 * of extension, so a character may be represented by 1, 2 or 3 digits. The second extension 
	 * digit may be different for each of the primary extension digits. The key is the list of 
	 * extension digits in order down the board, together with the value of the empty position in 
	 * the last row.
	 * 
	 * Characters are positioned in the checkerboard based on English frequency analysis, which gives
	 * an order of: ETAOINSRHDLUCMFYWGPBVKXQJ. Although recent work suggests there may be a better order 
	 * available (http://norvig.com/mayzner.html). The most frequent lower case letters are considered 
	 * to be more frequent than the most frequent upper case ones. Digits and space are included in the 
	 * middle of the sequence between the most and least used, giving an unusual plaintext string of:
	 * etaoinsrhdlucETAOINSRHDLUC_0123456789mfywgpbvkxqjzMFYWGPBVKXQJZ
	 * 
	 * Using this string and a key of 3517269, the checkerboard becomes:
	 * 
	 *    0 1 2 3 4 5 6 7 8 9
	 *  . e t a   o   i n s r
	 *  3 h   d l u c E   T A
	 *  5 O I   N S R   H D L
	 * 31 U C _ 0 1 2 3 4 5 6
	 * 37 7 8 9 m f y w g p b
	 * 52 v k x q j z M F Y W
	 * 56 G P B V K X Q J Z 
	 * 
	 *  H e  l  l o   _   W o r  l  d
	 * 57 0 33 33 4 312 529 4 9 33 32
	 * 
	 * which becomes just 57033334312529493332 (20 digits compared to 22 for Polybius Square, 
	 * longer text would show more of an advantage of compression).
	 * 
	 *  N o   w   _ i s   _ t  h e   _ t i   m e   _   f o r   _ a  l  l   _   g o o  d   _   m e n 
	 * 53 4 376 312 6 8 312 1 30 0 312 1 6 373 0 312 374 4 9 312 2 33 33 312 377 4 4 32 312 373 0 7
	 *   _ t o   _  c o   m e   _ t o   _ t  h e   _ a i  d   _ o   f   _ t  h e   _   p a r t   y 
	 * 312 1 4 312 35 4 373 0 312 1 4 312 1 30 0 312 2 6 32 312 4 374 312 1 30 0 312 378 2 9 1 375
	 * 
	 * which becomes just 5343763126831213003121637303123744931223333312377443231237307
	 * 312143123543730312143121300312263231243743121300312378291375 (121 digits as opposed to 128, 
	 * but this text is chosen to contain unusual letters for typing practice).
	 * 
	 * A great deal of extra length is added by the spaces (3 digits) and better compression could be
	 * achieved if they were 1 or 2 digits. It is useful for space to be an odd number of digits because
	 * that allows messages to be extended to an even number of digits, if required.
	 * 
	 * Note that when decrypting, the sequence length is unambiguous, because any number that starts 
	 * with 3 or 5 must be at least two digits long. Any number that starts 31, 37, 52 or 56 will be 
	 * 3 digits long. All other numbers are a single digit.
	 * 
	 * Other ways to populate checkerboards include filling the top line with the most frequent letters 
	 * (perhaps in a scrambled order from a Mnemonic - see below) and the rest of the grid with the 
	 * letters in alphabetic order. Or the number at the top of the board could be derived from a keyword 
	 * (effectively scrambling the whole table), this is what the VIC cipher did.
	 * 
	 * Mnemonics for the most commonly used letters in English, that have actually been used by spies, 
	 * include ESTONIA-R, A-SIN-TO-ER(R) and AT-ONE-SIR.
	 * 
	 */
}
