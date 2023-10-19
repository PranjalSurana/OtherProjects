package com.fidelity.application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import com.fidelity.encryption.FileEncrypter;

/*
 * This shows how these classes may be used in an application. It is intended to show that there
 * is no more leakage using the Factory Method than there was using the string parameter.
 */
public class Driver {

	public static void main(String[] args) throws IOException {
		Driver app = new Driver();
		app.processFile();
	}
	
	private FileEncrypter getFileEncrypter(String key) {
		return new FileEncrypter("CaesarKey", key);
	}

	private void processFile() throws IOException {
		FileEncrypter fe = getFileEncrypter("ThE aNsWeR iS 42");
		File startFile = File.createTempFile("driver", ".txt");
		File encryptedFile = File.createTempFile("driver", ".txt");
		File decryptedFile = File.createTempFile("driver", ".txt");

		FileWriter w = new FileWriter(startFile);
		w.write("Hello World");
		w.close();

		System.out.println("Original message: Hello World");

		fe.encryptFile(startFile.getAbsolutePath(), encryptedFile.getAbsolutePath());

		List<String> lines = Files.readAllLines(encryptedFile.toPath());
		System.out.println("Contents of encrypted file:");
		lines.forEach(System.out::println);
		System.out.println("***");

		// Presumably do something with the encrypted file


		// Then, later, or elsewhere ...
		fe.decryptFile(encryptedFile.getAbsolutePath(), decryptedFile.getAbsolutePath());

		lines = Files.readAllLines(decryptedFile.toPath());
		System.out.println("Contents of decrypted file:");
		lines.forEach(System.out::println);
		System.out.println("***");

		startFile.delete();
		encryptedFile.delete();
		decryptedFile.delete();
	}

}
