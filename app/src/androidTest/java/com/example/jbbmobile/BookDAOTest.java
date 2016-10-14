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
    private BookDAO bookDAO;

    @Before
    public void setup(){
        Context context = InstrumentationRegistry.getTargetContext();
        bookDAO = new BookDAO(context);
        bookDAO.onUpgrade(bookDAO.getWritableDatabase(),1,1);
    }

    @Test
    public void testIfFindBookIsSuccessful() throws Exception{
        Book book = new Book(1,"Summer");
        bookDAO.insertBook(book);
        Book book1 = bookDAO.findBook(1);
        assertEquals(1,book1.getIdBook());
    }

    @Test
    public void  testIfFindBookIsNotSuccessful() throws  Exception{
        Book book = bookDAO.findBook(5);
        boolean invalid;
        invalid  = String.valueOf(book.getIdBook()).equals("5");
        assertFalse(invalid);
    }

    @Test
    public void  testIfInsertIsSuccessful() throws  Exception{
        Book book = new Book(2,"Fall");
        int valid = bookDAO.insertBook(book);
        assertNotEquals(-1,valid);
    }

    @Test
    public void  testIfInsertIsNotSuccessful() throws  Exception{
        Book book = new Book();
        int invalid = bookDAO.insertBook(book);
        assertEquals(-1,invalid);
    }

    @Test
    public void testIfInsertIsNotValid() throws Exception{
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
