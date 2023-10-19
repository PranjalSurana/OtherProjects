package com.fidelity.cipher;

import com.fidelity.cipher.tools.AbstractSubstitutionCipher;

public class CaesarKeyAlgorithmImpl extends AbstractSubstitutionCipher {

	@Override
	protected String createKeytext(String key) {
		// Not much point using StringBuilder since we have to check the contents of the String each time
		String keytext = "";
		for (int i = 0; i < key.length(); ++i) {
			char ch = key.charAt(i);
			if (keytext.indexOf(ch) == -1) {
				keytext += ch;
			}
		}
		for (int i = PLAINTEXT.length() - 1; i >= 0; --i) {
			char ch = PLAINTEXT.charAt(i);
			if (keytext.indexOf(ch) == -1) {
				keytext += ch;
			}
		}
		// length should be 63
		if (keytext.length() != 63) {
			throw new IllegalArgumentException("Key may only contain upper & lower roman alphabet, numbers and space");
		}

		return keytext;
	}

}
