package gov.jbb.missaonascente.model;

import gov.jbb.missaonascente.model.Book;
import gov.jbb.missaonascente.model.Element;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BookTest {

    private Book book;

    @Test
    public void testIfBookIsCreated () throws Exception {
        book = new Book(1, "Summer");
        assertEquals(1,book.getIdBook());
    }

    @Test
    public void testIfBookIsCreatedWithList () throws Exception {
        book = new Book(3, "Winter");
        assertEquals("Winter",book.getNameBook());
    }

    @Test
    public void testIfBookNameNotNull () throws Exception {
        book = new Book(3, "Winter");
        assert book.getNameBook() != null;
    }

    @Test
    public void testIfBookNameIsNull () throws Exception {
        try {
            book = new Book(2, null);
        } catch (IllegalArgumentException nameBookNull) {
           assertEquals(nameBookNull.getMessage(), "nameBook");
        }
    }

    @Test
    public void testIfIdBookIsUpperThanLimit () throws Exception {
        boolean invalid = false;

        try {
            book = new Book(5, "Winter");
        } catch (IllegalArgumentException idBook) {
            invalid = idBook.getMessage().equals("idBook");
        } catch (Exception idBook) {
            idBook.printStackTrace();
        }

        assertTrue(invalid);
    }

    @Test
    public void testIfIdBookIsLowerThanLimit () throws Exception {
        boolean invalid = false;

        try {
            book = new Book(-1, "Winter");
        } catch (IllegalArgumentException idBook) {
            invalid = idBook.getMessage().equals("idBook");
        } catch (Exception idBook) {
            idBook.printStackTrace();
        }

        assertTrue(invalid);
    }

    @Test
    public void testBookConstructor () throws Exception {
        List<Element> elements = new ArrayList<>();
        Book book = new Book(3, "Winter", elements);
        assertEquals(book.getElements(), elements);
        assertEquals(book.getIdBook(), 3);
        assertEquals(book.getNameBook(), "Winter");
    }
}
