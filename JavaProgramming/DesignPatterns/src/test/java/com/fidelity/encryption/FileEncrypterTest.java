package com.fidelity.encryption;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * Note that it is not necessary for this test class to contain all the tests of the cipher
 * algorithm since it is *not* testing whether the algorithm gives the right results, only
 * whether the file is correctly encrypted or decrypted.
 */
class FileEncrypterTest {

	private FileEncrypter fe;

	private File testInFile;
	private File testOutFile;

	/*
	 * A word about temporary files. There are clearly situations in which this approach could leave
	 * temporary files in existence. In JUnit 4, there was a Rule that provided a more water-tight
	 * method for managing temporary files, but that has not yet been ported to JUnit 5. You could 
	 * create a JUnit Extension (the replacement for Rules) that does the same, but I decided that
	 * was overkill for this test.
	 */
	@BeforeEach
	void setUp() throws IOException {
		testInFile = File.createTempFile("tst", ".txt");	
		testOutFile = File.createTempFile("tst", ".txt");	
		// If the test fails badly, this should cause the files to be deleted eventually
		testInFile.deleteOnExit();
		testOutFile.deleteOnExit();
	}

	@AfterEach
	void tearDown() {
		// Note that while deleteOnExit() is a backup, we don't want to rely on it
		testInFile.delete();
		testOutFile.delete();
	}
	
	private void createAndEncryptFile(String algorithm, String key, String expected) throws IOException {
		fe = new FileEncrypter(algorithm, key);		

		FileWriter w = new FileWriter(testInFile);
		w.write("Hello World");
		w.close();

		fe.encryptFile(testInFile.getAbsolutePath(), testOutFile.getAbsolutePath());

		List<String> lines = Files.readAllLines(testOutFile.toPath());

		assertEquals(1, lines.size());
		assertEquals(expected, lines.get(0));
	}

	private void createAndDecryptFile(String algorithm, String key, String input) throws IOException {
		fe = new FileEncrypter(algorithm, key);		

		FileWriter w = new FileWriter(testInFile);
		w.write(input);
		w.close();

		fe.decryptFile(testInFile.getAbsolutePath(), testOutFile.getAbsolutePath());

		List<String> lines = Files.readAllLines(testOutFile.toPath());

		assertEquals(1, lines.size());
		assertEquals("Hello World", lines.get(0));
	}

	@Test
	void testEncryptFileCaesarKey() throws IOException {
		createAndEncryptFile("CaesarKey", "ThE aNsWeR iS 42", "vPHHDTcDAHQ");
	}

	@Test
	void testDecryptFileCaesarKey() throws IOException {
		createAndDecryptFile("CaesarKey", "ThE aNsWeR iS 42", "vPHHDTcDAHQ");
	}

	@Test
	void testEncryptFileSimpleCaesar() throws IOException {
		createAndEncryptFile("SimpleCaesar", "1", "Ifmmp0Xpsme");
	}

	@Test
	void testDecryptFileSimpleCaesar() throws IOException {
		createAndDecryptFile("SimpleCaesar", "1", "Ifmmp0Xpsme");
	}
	
	@Test
	void testInvalidCipherAlgorithm() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			fe = new FileEncrypter("Vigenere", "le chiffre indechiffrable");
		});
		assertEquals("Cipher algorithm must be SimpleCaesar or CaesarKey", e.getMessage());
	}

}
