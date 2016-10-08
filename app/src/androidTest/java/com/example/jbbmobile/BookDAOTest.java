package com.example.jbbmobile;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import com.example.jbbmobile.dao.BookDAO;
import com.example.jbbmobile.model.Book;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class BookDAOTest {
    Context context;
    BookDAO bookDAO;

    @Before
    public void setup(){
        context = InstrumentationRegistry.getTargetContext();
        bookDAO = new BookDAO(context);
        bookDAO.createTableBook(bookDAO.getWritableDatabase());
    }

    @Test
    public void testIfFindBookIsSuccessful() throws Exception{
        Book book = bookDAO.findBook(1);
        assertEquals(1,book.getIdBook());
    }

    @Test
    public void  testIfFindBookIsNotSuccessful() throws  Exception{
        Book book = bookDAO.findBook(5);
        boolean invalid;
        invalid  = String.valueOf(book.getIdBook()).equals("5");
        assertFalse(invalid);
    }

    @Test
    public void  testIfInsertNotIsSuccessful() throws  Exception{
        Book book = new Book(3,"Winter");
        int invalid =bookDAO.insertBook(book);
        assertEquals(-1,invalid);
    }

    @Test
    public void testIfInsertNotIsValid() throws Exception{
        boolean invalid = false;
        try {
        Book book = new Book(9,"Spring");
        bookDAO.insertBook(book);
        }catch (IllegalArgumentException invalidId){
            invalid = invalidId.getMessage().equals("idBook");
        }
        assertTrue(invalid);
    }
    @After
    public void closeDataBase(){
        bookDAO.close();
    }
}
