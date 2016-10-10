package com.example.jbbmobile;

import com.example.jbbmobile.model.Book;
import com.example.jbbmobile.model.Element;

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
    public void testIfBookIsCreatedWithExplorer () throws Exception {
        book = new Book(3, "Winter");
        assertEquals("Winter",book.getNameBook());
    }

    @Test
    public void testIfBookIsCreatedWithList () throws Exception {
        book = new Book(3, "Winter");
        assertEquals("Winter",book.getNameBook());
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