package com.fidelity.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fidelity.business.Book;
import com.fidelity.integration.BookDao;

@Service  // or @Component
public class BookService {
	@Autowired
	private BookDao dao;

	@Autowired
	public BookService(BookDao dao) {
		this.dao = dao;
	}

	public List<Book> queryAllBooks() {
		List<Book> books = null;

		books = dao.queryForAllBooks();

		return books;
	}

	// BONUS WORK

	public List<Book> queryBooksByTitle(String title) {
		List<Book> books = dao.queryForAllBooks();

		return books.stream()
				.filter(book -> startsWithIgnoreCase(book.getTitle(), title))
				.collect(Collectors.toList());
	}

	private boolean startsWithIgnoreCase(String str, String prefix) {
		return str.regionMatches(true, 0, prefix, 0, prefix.length());
	}

}