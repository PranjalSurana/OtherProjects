package com.fidelity.string;

public class StringExamples {

	public static void main(String[] args) {
		stringMethods();
	}

	public static void stringMethods() {
		String s = new String("hello world");
		int size = s.length();    // 11
		char firstChar = s.charAt(0);   // 'h'
		String firstThreeChars = s.substring(0, 3);   // "hel" 
		
		System.out.println("String: " + s);
		System.out.println("Length: " + size);
		System.out.println("First char: " + firstChar);
		System.out.println("First 3 chars: " + firstThreeChars);
	}

}
