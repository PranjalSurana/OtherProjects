package com.fidelity.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fidelity.business.Book;
import com.fidelity.integration.BookService;

public class BookStoreTest {
	// Mockito will create a mock BookService
	@Mock
	private BookService mockBookService;

	// Mockito will construct a BookStore and inject the mock BookService into it 
	@InjectMocks
	private BookStore bookStore;
	
	// Note: Mockito uses the ByteBuddy library to generate mocks. In the Eclipse
	// debugger, ByteBuddy makes it appear as if the mock BookService is a "real" 
	// BookService. But when you step into a method call, it will be obvious you 
	// are executing mock code.

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllBooks_ServiceReturnsMultipleBooks() throws Exception {
		List<Book> expectedBooks = List.of(
			new Book("Birnam Wood", "Eleanor Catton", "", 10),
			new Book("What Napoleon Could Not Do", "DK Nnuro", "", 20)
		);
		when(mockBookService.queryForAllBooks())
			.thenReturn(expectedBooks);

		List<Book> actualBooks = bookStore.getAllBooks();
		
		assertEquals(expectedBooks, actualBooks);
	}

	@Test
	void testGetAllBooks_ServiceReturnsEmptyList() throws Exception {
		when(mockBookService.queryForAllBooks())
			.thenReturn(List.of());

		List<Book> actualBooks = bookStore.getAllBooks();
		
		assertTrue(actualBooks.isEmpty());
	}

	@Test
	void testGetAllBooks_ServiceReturnsNull() throws Exception {
		when(mockBookService.queryForAllBooks())
			.thenReturn(null);

		List<Book> actualBooks = bookStore.getAllBooks();
		
		assertTrue(actualBooks.isEmpty());
	}

	@Test
	void testGetAllBooks_ServiceThrowsException() throws Exception {
		when(mockBookService.queryForAllBooks())
			.thenThrow(new IOException());

		assertThrows(IllegalStateException.class, () -> {
			bookStore.getAllBooks();
		});
	}

	@Disabled
	@Test
	void testGetBooksByTitle() {
		fail("Not yet implemented");
	}

}
