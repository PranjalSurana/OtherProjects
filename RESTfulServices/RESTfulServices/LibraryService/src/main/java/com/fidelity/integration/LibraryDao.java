package com.fidelity.integration;

import java.util.List;

import com.fidelity.business.Book;
import com.fidelity.restcontroller.DatabaseRequestResult;

public interface LibraryDao {

	List<Book> getBooks();

	Book getBookByIsbn(String isbn);

	void insertBook(Book book);
}