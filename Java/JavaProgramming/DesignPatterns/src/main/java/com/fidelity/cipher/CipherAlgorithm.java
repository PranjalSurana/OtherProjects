package com.fidelity.cipher;

public interface CipherAlgorithm {
	String encrypt(String message);
	String decrypt(String message);
	void setKey(String key);
}
