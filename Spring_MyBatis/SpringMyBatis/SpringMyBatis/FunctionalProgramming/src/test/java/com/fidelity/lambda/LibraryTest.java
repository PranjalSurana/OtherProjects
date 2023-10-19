package com.fidelity.lambda;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fidelity.model.Book;
import com.fidelity.model.DummyBookList;
import com.fidelity.model.Library;

public class LibraryTest {
	private Library lib;
	
	// TODO: Complete the TODOs in Library.java
	
	@BeforeEach
	public void setUp() throws Exception {
		lib = new Library(DummyBookList.createBooksList());
	}
	
	@Test
	public void testSortByPrice() {
		List<Book> expectedBooks = List.of(
			new Book("0201485672", "Martin Fowler", 20.94, "Refactoring"),
			new Book("0137081073", "Robert Martin", 31.13, "The Clean Coder"),
			new Book("0134494164", "Robert Martin", 33.13, "Clean Architecture"),
			new Book("0132350882", "Robert Martin", 33.98, "Clean Code"),
			new Book("0201633612", "Gamma and 3 other guys", 34.95, "Design Patterns"),
			new Book("0321193687", "Martin Fowler", 37.56, "UML Distilled"),
			new Book("0321127420", "Martin Fowler", 54.03, "Patterns of Enterprise Application Architecture")
		); 

		lib.sortByPrice();
		
		assertEquals(lib.getBooks(), expectedBooks);
	}

	// TODO: Define a test for sort by title
	@Test
	public void testSortByTitle() {
		lib.sortByTitle();
		assertEquals("Clean Architecture", lib.getBooks().get(0).getTitle());
	}


}
