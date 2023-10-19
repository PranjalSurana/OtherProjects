package com.fidelity.cipher.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class ColumnKey {

	private static final String PLAINTEXT = " 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	public static int[] keyOrderFromText(String key, boolean allowDuplicates) {
		Map<Integer, List<Integer>> keyChars = new TreeMap<>();
		int keyPos = identifyKeyCharacters(key, allowDuplicates, keyChars);

		int[] keyOrder = new int[keyPos];
		int index = 0;
		for (Entry<Integer, List<Integer>> e : keyChars.entrySet()) {
			List<Integer> positionList = e.getValue();
			for (Integer position : positionList) {
				keyOrder[index++] = position;
			}
		}
		
		return keyOrder;
	}

	private static int identifyKeyCharacters(String key, boolean allowDuplicates, Map<Integer, List<Integer>> keyChars) {
		int keyPos = 0;
		for (int i = 0; i < key.length(); i++) {
			// Sadly putIfAbsent always executes the increment, so do it the hard way
			int index = PLAINTEXT.indexOf(key.charAt(i));
			if (keyChars.get(index) == null) {
				List<Integer> newList = new ArrayList<>();
				newList.add(keyPos++);
				keyChars.put(index, newList);
			} else if (allowDuplicates) {
				keyChars.get(index).add(keyPos++);
			}
		}
		return keyPos;
	}

	public static String keyFromText(String key, boolean allowDuplicates) {
		Map<Integer, List<Integer>> keyChars = new TreeMap<>();
		int keyPos = identifyKeyCharacters(key, allowDuplicates, keyChars);

		int[] keyOrder = assignNumericValuesToKey(keyChars, keyPos);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keyPos; i++) {
			sb.append(keyOrder[i]);
		}
		
		return sb.toString();
	}

	private static int[] assignNumericValuesToKey(Map<Integer, List<Integer>> keyChars, int keyLength) {
		int[] keyOrder = new int[keyLength];
		int index = 0;
		for (Entry<Integer, List<Integer>> e : keyChars.entrySet()) {
			List<Integer> positionList = e.getValue();
			for (Integer position : positionList) {
				keyOrder[position] = index++;
			}
		}
		return keyOrder;
	}

	public static String keyFromTextModulo10(String key, boolean allowDuplicates) {
		Map<Integer, List<Integer>> keyChars = new TreeMap<>();
		int keyPos = identifyKeyCharacters(key, allowDuplicates, keyChars);

		int[] keyOrder = assignNumericValuesToKey(keyChars, keyPos);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keyPos; i++) {
			sb.append(keyOrder[i] % 10);
		}
		
		return sb.toString();
	}

	
	
	/*
	 * A column key is part of a columnar transposition, and other techniques.
	 * The process starts with a key word. Each character in the keyword is 
	 * assigned a number based on the usual sorting sequence.
	 * 
	 * In the representation here, the key order that is returned either contains
	 * the order in which the columns should be considered, or it returns a
	 * number representing the key.
	 * 
	 * _0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.
	 * 
	 * RoYgBiV <- keyword
	 * 1634052 <- keyword numbers from plaintext above
	 * 
	 * the key order equivalent is 4, 0, 6, 2, 3, 5, 1
	 * 
	 * 
	 * ThE_aNsWeRiS42
	 *  1    1 1 1   
	 * 71309438052621
	 * 
	 * the key order equivalent is 3, 13, 12, 2, 5, 9, 11, 0, 7, 4, 8, 1, 10, 6
	 * 
	 * 
	 * The key can also be constructed with repeated letters. Some transpositions
	 * treat the repeated letters differently (perhaps reading them backwards) and
	 * others just number them from left to right.
	 * 
	 * ThE_aNsWeR_iS_42
	 *  1  1 111  1
	 * 9350165027148243
	 * 
	 * 0123456789012345
	 * the key order equivalent is 3, 10, 13, 15, 14, 2, 5, 9, 12, 0, 7, 4, 8, 1, 11, 6
	 * 
	 */

}
