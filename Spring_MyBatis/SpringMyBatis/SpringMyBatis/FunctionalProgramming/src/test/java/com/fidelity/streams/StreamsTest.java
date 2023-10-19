package com.fidelity.streams;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fidelity.model.Book;
import com.fidelity.model.DummyBookList;
import com.fidelity.model.Library;

public class StreamsTest {
	private List<Book> books;

	@BeforeEach
	public void setUp() throws Exception {
		Library lib = new Library(DummyBookList.createBooksList());
		books = lib.getBooks();
	}

	@Test
	// TODO: Write the test to find the count of all the books written by Robert Martin.
	//       Use streams to do this.
	public void testFindCountOfBooksByRobertMartin() {
		// HINT: 1. get a stream from the books list
		//       2. use a filter that selects books with author Robert Martin
		//       3. use a terminal operation to count the filtered results
		//       See slide 6-17
		fail("the find count of books by Robert Martin test is not defined");
	}
	
	@Test
	// TODO: Write the test to find all the books less than $50.
	//       Use streams to do this.
	public void testFindBooksLessThanFiftyDollars() {
		fail("the find books less than $50 test is not defined");
	}

	@Test
	// TODO: Write the test to find all the books by Martin Fowler.
	//       Use streams to do this.
	public void testFindBooksByMartinFowler() {
		// HINT: See slide 6-20
		fail("the find books by Martin Fowler test is not defined");
	}
	
	// BONUS STEPS: using the java.util.Optional class
	
	@Test
	// TODO: Write the test to find the highest priced book.
	//       Use streams to do this. 
	public void testFindHighestPricedBook() {
	// HINT: Read the Javadocs for the java.util.Stream.max method.
	//       Read the Javadocs for the java.util.Optional class.
		fail("the highest priced book test is not defined");
	}
	
	@Test
	// TODO: Write the test to find any book written by Robert Martin.
	//       Use streams to do this. You will need to use Optional<> again.
	public void testFindAnyBookByRobertMartin() {
		fail("the find any book by Robert Martin test is not defined");
	}
	
}
