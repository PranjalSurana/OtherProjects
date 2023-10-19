package com.fidelity.encryption;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.stream.Stream;

import com.fidelity.cipher.CaesarKeyAlgorithmImpl;
import com.fidelity.cipher.CipherAlgorithm;
import com.fidelity.cipher.SimpleCaesarAlgorithmImpl;

public class FileEncrypter {
	private CipherAlgorithm ca;

	public FileEncrypter(String algorithm, String key) {
		super();
		switch (algorithm) {
		case "SimpleCaesar" :
			ca = new SimpleCaesarAlgorithmImpl();
			break;
		case "CaesarKey" :
			ca = new CaesarKeyAlgorithmImpl();
			break;
		default:
			throw new IllegalArgumentException("Cipher algorithm must be SimpleCaesar or CaesarKey");	
		}
		ca.setKey(key);
	}

	/*
	 * Don't worry about what these methods are actually doing, this is just a fancy way of
	 * reducing duplicate code by passing a function reference into a common method.
	 */
	public void encryptFile(String inFile, String outFile) throws IOException {
		convertFile(inFile, outFile, ca::encrypt);
	}
	
	public void decryptFile(String inFile, String outFile) throws IOException {
		convertFile(inFile, outFile, ca::decrypt);
	}
	
	private void convertFile(String inFile, String outFile, Function<String, String> operation) throws IOException {
		try (Stream<String> stream = Files.lines(Paths.get(inFile));
			PrintWriter pw = new PrintWriter(Files.newBufferedWriter(Paths.get(outFile)))) {
			stream.map(operation)
				.forEach(pw::println);
		}
	}
	
}
