package com.fidelity.restcontroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fidelity.business.Book;
import com.fidelity.business.BookList;
import com.fidelity.integration.LibraryDao;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;

@RestController
@RequestMapping("/library")
public class LibraryController {
	@Autowired
	private LibraryDao dao;
	
	public LibraryController() {
	}

	// GET http://localhost:8080/library/books
	@GetMapping("/books")
	public ResponseEntity<BookList> getBooks() {
		BookList books;
		ResponseEntity<BookList> response;
		try {
			books = new BookList(dao.getBooks());
		}
		catch(Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (books != null) {
			response = ResponseEntity.status(HttpStatus.OK).body(books);
		}
		else {
			response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return response;
	}

	// GET http://localhost:8080/library/978-0060512804
	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable("id") String id) {
		Book book = null;
		ResponseEntity<Book> response;
		try {
			book = dao.getBookByIsbn(id);
		}
		catch(IllegalArgumentException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		if (book != null) {
			response = ResponseEntity.status(HttpStatus.OK).body(book);
		}
		else {
			response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return response;
	}

	@PostMapping("/addbook")
	public ResponseEntity<Book> addNewBook(@RequestBody Book book) {
		ResponseEntity<Book> response;
		if (book != null) {
			try {
				dao.insertBook(book);
			}
			catch(IllegalArgumentException ex) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response = ResponseEntity.status(HttpStatus.OK).body(book);
		}
		else {
			response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return response;
	}

}