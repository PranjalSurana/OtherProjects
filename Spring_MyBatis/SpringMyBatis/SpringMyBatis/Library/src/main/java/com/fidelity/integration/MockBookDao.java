package com.fidelity.integration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.fidelity.business.Book;

@Repository("bookDao")   // or @Component
public class MockBookDao implements BookDao {
	private static List<Book> books;

	static {
		books = List.of(
				new Book("Cryptonomicon", "Neal Stephenson", "", 1),
				new Book("Clean Code", "Robert Martin", "", 2),
				new Book("UML Distilled", "Martin Fowler", "", 3),
				new Book("Design Patterns", "Gamma, Helm, Johnson, Vlissides", "", 4)
		);
	}

	@Override
	public List<Book> queryForAllBooks() {
		return new ArrayList<>(books);
	}

}
