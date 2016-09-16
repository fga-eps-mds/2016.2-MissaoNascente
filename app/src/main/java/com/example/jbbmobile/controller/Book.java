package com.example.jbbmobile.controller;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;

import com.example.jbbmobile.R;
import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.model.Books;
import com.example.jbbmobile.model.Elements;

/**
 * Created by roger on 15/09/16.
 */
public class Book {

    private Elements elements;
    private Books book;



    public Book(int bookId, int elementId, Context context, Resources resources){
        setBook(new Books(bookId));
        getBook().setNameBook(R.string.en_bookName_Fall);
        setElements(new Elements(elementId));
        getElements().setIdBook(getBook().getIdBook());

        ElementDAO elementDAO = new ElementDAO(context);
        getBook().setElementses(elementDAO.findElementsBook(getElements()));

    }

    public Elements getElements() {
        return elements;
    }

    public void setElements(Elements elements) {
        this.elements = elements;
    }

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }
}
