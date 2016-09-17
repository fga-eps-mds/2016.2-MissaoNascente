package com.example.jbbmobile.controller;

import android.content.Context;

import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;

import com.example.jbbmobile.R;

import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.model.Books;
import com.example.jbbmobile.model.Elements;

public class Book {
    private Elements elements;
    private Books book;

    public Book(int elementId, Context context){
        ElementDAO elementDAO = new ElementDAO(context);
        setElements(elementDAO.findElement(new Elements(elementId)));

        setBook(new Books(getElements().getIdBook()));
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
