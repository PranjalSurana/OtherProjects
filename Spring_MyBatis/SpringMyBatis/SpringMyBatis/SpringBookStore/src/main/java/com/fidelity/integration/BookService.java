package com.fidelity.integration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fidelity.business.Book;

//@Transactional

@Component  // identifies this as a class that Spring will manage
public class BookService {
	private final List<Book> books;
	
	public BookService() {
		books = new ArrayList<>();
		books.add(new Book("Cryptonomicon", "Neal Stephenson", "", 1));		
		books.add(new Book("Clean Code", "Robert Martin", "", 2));		
		books.add(new Book("UML Distilled", "Martin Fowler", "", 3));
		books.add(new Book("Design Patterns", "Gamma, Helm, Johnson, Vlissides", "", 4));

		System.out.println("Completed the BookService constructor");
	}

	private boolean startsWithIgnoreCase(String str, String prefix) {
		return str.regionMatches(true, 0, prefix, 0, prefix.length());
	}

	// TODO: add a breakpoint here
	public List<Book> queryForAllBooks() throws IOException {
		return queryBooksByTitle("");
	}

	public List<Book> queryBooksByTitle(String title) throws IOException {
		return books.stream()
				.filter(book -> startsWithIgnoreCase(book.getTitle(), title))
				.collect(Collectors.toList());
	}

	public void addBook(Book book) {
		books.add(book);
	}
}
