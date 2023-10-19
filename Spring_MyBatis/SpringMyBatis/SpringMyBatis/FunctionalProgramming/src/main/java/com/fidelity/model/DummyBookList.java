package com.fidelity.model;

import java.util.ArrayList;
import java.util.List;

public class DummyBookList {
	
	// Create dummy data
	public static List<Book> createBooksList() {
		List<Book> books = new ArrayList<>();
		books.add(new Book("0201633612", "Gamma and 3 other guys", 34.95, "Design Patterns"));
		books.add(new Book("0132350882", "Robert Martin", 33.98, "Clean Code"));
		books.add(new Book("0137081073", "Robert Martin", 31.13, "The Clean Coder"));
		books.add(new Book("0134494164", "Robert Martin", 33.13, "Clean Architecture"));
		books.add(new Book("0321127420", "Martin Fowler", 54.03, "Patterns of Enterprise Application Architecture"));
		books.add(new Book("0201485672", "Martin Fowler", 20.94, "Refactoring"));
		books.add(new Book("0321193687", "Martin Fowler", 37.56, "UML Distilled"));
		
		return books;
	}
}
