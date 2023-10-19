package com.fidelity.integration;

import java.util.List;

import com.fidelity.business.Book;
import org.springframework.stereotype.Component;

public interface BookDao {

	List<Book> queryForAllBooks();

//	List<Book> queryBooksByTitle(String title);

}