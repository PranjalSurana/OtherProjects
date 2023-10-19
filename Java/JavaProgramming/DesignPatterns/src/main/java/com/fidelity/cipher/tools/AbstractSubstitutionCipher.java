package com.fidelity.cipher.tools;

import java.util.Objects;

import com.fidelity.cipher.CipherAlgorithm;

public abstract class AbstractSubstitutionCipher implements CipherAlgorithm {

	protected static final String PLAINTEXT = " 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	protected String keytext;

	public AbstractSubstitutionCipher() {
		super();
	}

	@Override
	public String encrypt(String message) {
		checkKey();
		return convert(message, PLAINTEXT, keytext);
	}

	@Override
	public String decrypt(String message) {
		checkKey();
		return convert(message, keytext, PLAINTEXT);
	}

	private String convert(String message, String fromDomain, String toDomain) {
	    StringBuilder builder = new StringBuilder();
	    for (int i = 0; i < message.length(); ++i) {
	        char ch = message.charAt(i);
	        int index = fromDomain.indexOf(ch);
	        if (index >= 0) {
	            char ench = toDomain.charAt(index);
	            builder.append(ench);
	        }
	    }
	    return builder.toString();
	}

	private void checkKey() {
		if (Objects.isNull(keytext)) {
			throw new IllegalStateException("Key not set");
		}
	}

	@Override
	public void setKey(String key) {
		keytext = createKeytext(key);
	}
	
	protected abstract String createKeytext(String key);
}