package com.fidelity.serial;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

class SerialExampleTest {

	@Test
	void testSerialization() throws IOException, ClassNotFoundException {
		SerialExample original = new SerialExample(42, "Mostly harmless");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(original);

		byte[] b = baos.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		ObjectInputStream ois = new ObjectInputStream(bais);
		SerialExample actual = (SerialExample) ois.readObject();

		assertEquals(original, actual);
	}

	@Test
	void testNotReallySerializable() throws IOException, ClassNotFoundException {
		NotReallySerializable original = new NotReallySerializable(42, "Mostly harmless", 1);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(original);

		byte[] b = baos.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		ObjectInputStream ois = new ObjectInputStream(bais);
		assertThrows(InvalidClassException.class, () -> {
			ois.readObject();
		});
	}

	@Test
	void testNotSerializableSubclass() throws IOException, ClassNotFoundException {
		NotSerializableSubclass original = new NotSerializableSubclass(42, "Mostly harmless", 1);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(original);

		byte[] b = baos.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		ObjectInputStream ois = new ObjectInputStream(bais);
		NotSerializableSubclass actual = (NotSerializableSubclass) ois.readObject();

		System.out.println("Original: " + original.toString());
		System.out.println("Actual: " + actual.toString());

		assertNotEquals(original, actual);
	}

	@Test
	void testSerializableSubclass() throws IOException, ClassNotFoundException {
		SerializableSubclass original = new SerializableSubclass(42, "Mostly harmless", 1);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(original);

		byte[] b = baos.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		ObjectInputStream ois = new ObjectInputStream(bais);
		SerializableSubclass actual = (SerializableSubclass) ois.readObject();

		System.out.println("Original: " + original.toString());
		System.out.println("Actual: " + actual.toString());

		assertEquals(original, actual);
	}
	
	@Test
	void testSimpleSerializableSubclass() throws IOException, ClassNotFoundException {
		SimpleSerializableSubclass original = new SimpleSerializableSubclass(42, "Mostly harmless", 1);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(original);

		byte[] b = baos.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		ObjectInputStream ois = new ObjectInputStream(bais);
		SimpleSerializableSubclass actual = (SimpleSerializableSubclass) ois.readObject();

		System.out.println("Original: " + original.toString());
		System.out.println("Actual: " + actual.toString());

		assertEquals(original, actual);
	}
}
