package com.example.jbbmobile;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.example.jbbmobile.controller.BooksController;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BooksControllerTest {
    private BooksController booksController;
    private Context context;

    @Before
    public void setup(){
        Context context = InstrumentationRegistry.getTargetContext();
        this.context = context;
        booksController = new BooksController();
        booksController.insertBooks(this.context);
    }

    @Test
    public void testIfCurrentPeriodIsValid(){
        booksController.currentPeriod();
        assertEquals(3, booksController.getCurrentPeriod());
    }

    @Test
    public void testIfAccessBooksIsSuccessful(){
        booksController.findExplorerLogged(context);
        String nameBook,nameCurrentBook;
        nameBook = "Period 1";
        nameCurrentBook = booksController.getBook(0).getNameBook();
        assertEquals(nameBook,nameCurrentBook);
    }

    @Test
    public void testIfAccessBooksIsNotSuccessful(){
        String nameBook,nameCurrentBook;
        nameBook = "Period 0";
        nameCurrentBook = booksController.getBook(0).getNameBook();
        assertNotEquals(nameBook,nameCurrentBook);
    }


}
