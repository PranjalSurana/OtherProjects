package com.fidelity.services;

import com.fidelity.business.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:library-beans.xml")
class BookServiceTest {

    @Autowired
    private BookService service;

    private Book crypto = new Book("Cryptonomicon", "Neal Stephenson", "", 1);
    private Book cleanCode = new Book("Clean Code", "Robert Martin", "", 2);
    private Book uml = new Book("UML Distilled", "Martin Fowler", "", 3);
    private Book designPatterns = new Book("Design Patterns", "Gamma, Helm, Johnson, Vlissides", "", 4);

    @Test
    void testCreateBookService() {
        assertNotNull(service);
    }

    @Test
    void testQueryAllBooks() {
        List<Book> expectedBooks = List.of(crypto, cleanCode, uml, designPatterns);

        List<Book> actualBooks = service.queryAllBooks();

        // if the actual book list is short, you can verify the contents
        // of the list just by calling assertEquals() with the expected list:
        assertEquals(expectedBooks, actualBooks);

        // if the actual book list is very long, verify the length of the
        // actual list and a few books:
        int actualSize = actualBooks.size();
        assertEquals(expectedBooks.size(), actualSize);
        assertEquals(crypto, actualBooks.get(0));
        assertEquals(designPatterns, actualBooks.get(actualSize - 1));
    }

}