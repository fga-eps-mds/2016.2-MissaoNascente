package com.example.jbbmobile;

import com.example.jbbmobile.controller.Element;
import com.example.jbbmobile.model.Books;
import com.example.jbbmobile.model.Elements;
import com.example.jbbmobile.model.Explorers;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by hugo on 19/09/16.
 */
public class BooksTest {

    private Books book;

    @Test
    public void testIfBookIsCreated () throws Exception {
        List<Elements> elements = new ArrayList<Elements>();
        elements.add(new Elements(1));
        book = new Books(1, "Summer", elements);
        assertEquals(1,book.getIdBook());
    }

    @Test
    public void testIfBookIsCreatedWithExplorer () throws Exception {
        List<Elements> elements = new ArrayList<Elements>();
        elements.add(new Elements(2));
        Explorers explorer = new Explorers();
        book = new Books(5, "Winter", elements, explorer);
        assertEquals("Winter",book.getNameBook());
    }
}