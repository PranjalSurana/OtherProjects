package com.fidelity.cipher;

import java.util.Objects;

public class VigenereAlgorithmImpl implements CipherAlgorithm {

	private static final String PLAINTEXT = " 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	private String[] encryptKeytext;
	private String[] decryptKeytext;

	@Override
	public String encrypt(String message) {
		checkKey();
	    return convert(message, encryptKeytext);
	}

	@Override
	public String decrypt(String message) {
		checkKey();
		return convert(message, decryptKeytext);
	}

	@Override
	public void setKey(String key) {
		encryptKeytext = new String[key.length()];
		decryptKeytext = new String[key.length()];
		
		for (int i = 0; i < key.length(); ++i) {
			char ch = key.charAt(i);
	        int index = PLAINTEXT.indexOf(ch);
	        if (index == -1) {
	        	throw new IllegalArgumentException("Key may only contain upper & lower roman alphabet, numbers and space");
	        }

	        // Create the ciphertext key for this character
			encryptKeytext[i] = createKeytext(ch);

	        // Create the complementary key for decryption
			decryptKeytext[i] = createKeytext(PLAINTEXT.charAt((63 - index) % 63));
		}
	}

	private String convert(String message, String[] keyText) {
	    StringBuilder builder = new StringBuilder();
	    for (int i = 0; i < message.length(); ++i) {
	        char ch = message.charAt(i);
	        String ciphertext = keyText[i % keyText.length];
	        int index = PLAINTEXT.indexOf(ch);
	        if (index >= 0) {
	            char ench = ciphertext.charAt(index);
	            builder.append(ench);
	        }
	    }
	    return builder.toString();
	}

	private void checkKey() {
		if (Objects.isNull(encryptKeytext)) {
			throw new IllegalStateException("Key not set");
		}
	}
	
	private String createKeytext(char keyChar) {
		int keyNum = PLAINTEXT.indexOf(keyChar);
		String keytext = PLAINTEXT.substring(keyNum) + PLAINTEXT.substring(0, keyNum);
		return keytext;
	}

	/* 
	 * The Vigenere cipher (https://en.wikipedia.org/wiki/Vigen%C3%A8re_cipher) is a simple
	 * variant of the Caesar cipher that is surprisingly hard to break because it resists 
	 * simple frequency analysis. For short messages or long keys, it has similar 
	 * characteristics to a one time pad.
	 * 
	 * A message is encoded by using multiple shifted alphabets (polyalphabetic) that are 
	 * each like a simple Caesar cipher. Which alphabets to use is indicated by a key. 
	 * The characters of the key are repeated to make it as long as the message and each
	 * character is used in turn. Using a simple table as shown below (tabula recta), find
	 * the character to be encoded in the plaintext across the top and read down to find 
	 * the row that corresponds to the appropriate character of the key.
	 * 
	 * Decrypting can be done by finding the letter to be decrypted on the appropriate row 
	 * and reading up to find the corresponding character of the plaintext. Or, it can be
	 * accomplished by creating the complementary key.
	 * 
	 * In the following tables, underscore (_) represents a space
	 * 
	 *   _0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz
	 * 
	 * _ _0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz
	 * 0 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_
	 * 1 123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0
	 * 2 23456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_01
	 * 3 3456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_012
	 * 4 456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123
	 * 5 56789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_01234
	 * 6 6789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_012345
	 * 7 789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456
	 * 8 89ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_01234567
	 * 9 9ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_012345678
	 * A ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789
	 * B BCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789A
	 * C CDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789AB
	 * D DEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789ABC
	 * E EFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCD
	 * F FGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCDE
	 * G GHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEF
	 * H HIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFG
	 * I IJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGH
	 * J JKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHI
	 * K KLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJ
	 * L LMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJK
	 * M MNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKL
	 * N NOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLM
	 * O OPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMN
	 * P PQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNO
	 * Q QRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOP
	 * R RSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQ
	 * S STUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQR
	 * T TUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRS
	 * U UVWXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRST
	 * V VWXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTU
	 * W WXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUV
	 * X XYZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVW
	 * Y YZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWX
	 * Z Zabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXY
	 * a abcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ
	 * b bcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZa
	 * c cdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZab
	 * d defghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabc
	 * e efghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcd
	 * f fghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcde
	 * g ghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdef
	 * h hijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefg
	 * i ijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefgh
	 * j jklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghi
	 * k klmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghij
	 * l lmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk
	 * m mnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijkl
	 * n nopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklm
	 * o opqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmn
	 * p pqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmno
	 * q qrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnop
	 * r rstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopq
	 * s stuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqr
	 * t tuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrs
	 * u uvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrst
	 * v vwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstu
	 * w wxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuv
	 * x xyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvw
	 * y yz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwx
	 * z z_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxy
	 * 
	 * 
	 * If the message is "Hello World" and the key is "ThE aNsWeR iS 42",
	 * then we could use the table below.
	 * 
	 * "H" encrypted with key letter "T" is "l", "e" encrypted with letter
	 * "h" is "L", "l" encrypted with letter "E" is space, etc.
	 * 
	 *   _0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz
	 * T TUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRS
	 * h hijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefg
	 * E EFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCD
	 * _ _0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz
	 * a abcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ
	 * N NOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLM
	 * s stuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqr
	 * W WXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUV
	 * e efghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcd
	 * R RSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQ
	 * _ _0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz
	 * i ijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefgh
	 * S STUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQR
	 * _ _0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz
	 * 4 456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123
	 * 2 23456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_01
	 * 
	 * 
	 *   _0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz
	 * R RSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQ
	 * o opqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmn
	 * Y YZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWX
	 * g ghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdef
	 * B BCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789A
	 * i ijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefgh
	 * V VWXYZabcdefghijklmnopqrstuvwxyz_0123456789ABCDEFGHIJKLMNOPQRSTU
	 * 
	 */

}
