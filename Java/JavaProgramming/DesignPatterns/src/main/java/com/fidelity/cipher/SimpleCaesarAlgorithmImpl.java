package com.fidelity.cipher;

import com.fidelity.cipher.tools.AbstractSubstitutionCipher;

public class SimpleCaesarAlgorithmImpl extends AbstractSubstitutionCipher {

	@Override
	protected String createKeytext(String key) {
		int keyNum = 0;
		try {
			keyNum = Integer.parseInt(key);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Key is numeric offset in range 0 to 62");
		}
		if (keyNum < 0 || keyNum > 62) {
			throw new IllegalArgumentException("Key is numeric offset in range 0 to 62");
		}
		String keytext = PLAINTEXT.substring(keyNum) + PLAINTEXT.substring(0, keyNum);
		return keytext;
	}

}
