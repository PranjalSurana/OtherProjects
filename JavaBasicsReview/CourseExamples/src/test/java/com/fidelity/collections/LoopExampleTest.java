package com.fidelity.collections;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class LoopExampleTest {

	@Test
	void test() {
		List<String> words = new ArrayList<>();
		words.add("Hello");
		words.add("World");
		LoopExample.processWords(words);
		// no assertions, not really a test: just a driver
	}

}
