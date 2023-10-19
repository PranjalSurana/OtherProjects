package com.fidelity.collections;

import java.util.List;

public class LoopExample {
	public static void processWords(List<String> words) {
	    for (int i = words.size() - 1; i >= 0; i--) {
	        String w = words.get(i);
	        // process each item, w, in turn
	        System.out.println(w);
	    }
	}

}
