package com.fidelity.services;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fidelity.business.Book;
import com.fidelity.integration.BookService;

/*
 * REVIEW: Angular supports Dependency Injection (DI). To inject a dependency:
 * 1. In the class that will be injected, add the @Injectable decorator:
 * 
 *       @Injectable(...)
 *       class BookService { ... }
 *       
 * 2. In the class with the dependency, add a BookService constructor parameter.
 *    Angular will create a BookService object and inject into the bookService field.
 * 
 *       class BookPageComponent {
 *         constructor(private bookService: BookService) { }
 * 
 * In Java applications, the Spring framework adds support for DI. 
 * See the example below.
 */

@Component  // identifies this as a class that Spring will manage
public class BookStore {

	// this is the dependency that needs to be satisfied
    private BookService service;  
    
    // The @Autowired annotation tells Spring to construct a BookService object 
    // and inject it into the bookService parameter.

    @Autowired
    public BookStore(BookService bookService) {
        service = bookService;

		System.out.println("Completed the BookStore constructor");
    }
    
    public List<Book> getAllBooks() {
        try {
            List<Book> books = service.queryForAllBooks();
            return books != null ? books : new ArrayList<>();
        }
        catch (Exception ex) {
            throw new IllegalStateException("Problem getting all books", ex);
        }
    }

    public List<Book> getBooksByTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title may not be empty");
        }
        try {
            List<Book> books = service.queryBooksByTitle(title);
            return books != null ? books : new ArrayList<>();
        }
        catch (Exception ex) {
            String msg = "Problem getting books with titles matching \"" + title + "\"";
            throw new IllegalStateException(msg, ex);
        }
    }

    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("book can't be null");
        }
        service.addBook(book);
    }
    
}
