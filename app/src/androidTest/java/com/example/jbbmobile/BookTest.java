package com.example.jbbmobile;

import com.example.jbbmobile.model.Book;
import com.example.jbbmobile.model.Element;
import com.example.jbbmobile.model.Explorer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by hugo on 19/09/16.
 */
public class BookTest {

    private Book book;

    @Test
    public void testIfBookIsCreated () throws Exception {
        List<Element> elements = new ArrayList<Element>();
        elements.add(new Element(1));
        book = new Book(1, "Summer", elements);
        assertEquals(1,book.getIdBook());
    }

    @Test
    public void testIfBookIsCreatedWithExplorer () throws Exception {
        List<Element> elements = new ArrayList<Element>();
        elements.add(new Element(2));
        Explorer explorer = new Explorer();
        book = new Book(5, "Winter", elements, explorer);
        assertEquals("Winter",book.getNameBook());
    }

    @Test
    public void testIfBookIsCreatedWithList () throws Exception {
        List<Element> elements = new ArrayList<Element>();
        elements.add(new Element(2));
        Explorer explorer = new Explorer();
        book = new Book(5, "Winter", elements, explorer);
        assertEquals("Winter",book.getNameBook());
    }

    @Test
    public void testBookConstructor () throws Exception {
        List<Element> elements = new ArrayList<>();
        Book book = new Book(5, "Winter", elements);
        assertEquals(book.getElements(), elements);
        assertEquals(book.getIdBook(), 5);
        assertEquals(book.getNameBook(), "Winter");
    }
}