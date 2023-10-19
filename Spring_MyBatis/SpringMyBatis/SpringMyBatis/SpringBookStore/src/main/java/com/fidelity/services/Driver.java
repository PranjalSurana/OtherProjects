package com.fidelity.services;

import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fidelity.business.Book;

/*
 * TODO: 
 * 1. Set a breakpoint on the call to bookStore.getAllBooks() below.
 * 2. Debug this Driver. Step into the call to getAllBooks and note that the
 *    BookStore's bookService field references a fully-initialized BookService.
 * 3. Examine the unit tests for BookStore. Note how we can easily replace
 *    the BookStore's BookService reference with a mock object.
 * 4. Add the @Transactional interface to the BookService class.
 * 5. Debug Driver again. Note that Spring has injected a transaction proxy
 *    into the the BookStore's bookService field.
 */

public class Driver {

	public static void main(String[] args) {

		BeanFactory beanFactory = createSpringBeanFactory();

		BookStore bookStore = (BookStore) beanFactory.getBean("bookStore");

		// TODO: set a breakpoint here
		List<Book> allbooks = bookStore.getAllBooks();

		System.out.println("all books: " + allbooks);
		
	}

	private static BeanFactory createSpringBeanFactory() {
		return new ClassPathXmlApplicationContext("beans.xml");
	}
}
