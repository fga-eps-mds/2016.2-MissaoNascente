package com.example.jbbmobile.controller;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.jbbmobile.dao.BookDAO;
import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.model.Books;
import com.example.jbbmobile.model.Elements;
import com.example.jbbmobile.model.Explorers;

public class Book {
    private Elements elements;
    private Books[] book;
    private Explorers explorer;

    public Book(Context context, Explorers explorer){
        /* Instantiating three books in the books vector */
        setElements(new Elements());
        book = new Books[]{new Books(), new Books(), new Books()};
        /* Specifing book names (must change to strings.xml) */
        getBook(0).setNameBook("Fall");
        getBook(1).setNameBook("Winter");
        getBook(2).setNameBook("Summer");
        /* Specifing book ids */
        getBook(0).setIdBook(0);
        getBook(1).setIdBook(1);
        getBook(2).setIdBook(2);
        /* Specifing book user */
        getBook(0).setExplorer(explorer);
        getBook(1).setExplorer(explorer);
        getBook(2).setExplorer(explorer);
        /* Inserting books in the book database */
        BookDAO bookDAO = new BookDAO(context);
        bookDAO.createTable(bookDAO.getWritableDatabase());
        bookDAO.insertBook(getBook(0));
        bookDAO.insertBook(getBook(1));
        bookDAO.insertBook(getBook(2));
    }

    public Book() {

    }

    public Book(SharedPreferences settings, Context context, Explorers explorer) {
        if (settings.getBoolean("mainScreenFirstTime", true)) {
            new Book(context, explorer);
            settings.edit().putBoolean("mainScreenFirstTime", false).commit();
        }
    }

    public void getAllBooksData(Context context){
        /* Initialize three books*/
        book = new Books[]{new Books(), new Books(), new Books()};
        BookDAO bookDAO = new BookDAO(context);
        book[0].setIdBook(0); book[1].setIdBook(1); book[2].setIdBook(2);
        /* Get book data from database */
        book[0] = bookDAO.findBook(book[0]);
        book[1] = bookDAO.findBook(book[1]);
        book[2] = bookDAO.findBook(book[2]);
    }

    public void getElementsFromDatabase(Context context) {
        /* Initialize one element */
        setElements(new Elements());
        /* Initialize database */
        ElementDAO elementDAO = new ElementDAO(context);
        /* Get all elements from one book and sets them in List<Elements> */
        getElements().setIdBook(0);
        getBook(0).setElements(elementDAO.findElementsBook(getElements()));
        getElements().setIdBook(1);
        getBook(1).setElements(elementDAO.findElementsBook(getElements()));
        getElements().setIdBook(2);
        getBook(2).setElements(elementDAO.findElementsBook(getElements()));
    }

    public Book(int elementId, Context context){
        ElementDAO elementDAO = new ElementDAO(context);
        setElements(elementDAO.findElement(new Elements(elementId)));

        setBook(new Books(getElements().getIdBook()), getElements().getIdBook());
    }

    public Elements getElements() {
        return elements;
    }

    public void setElements(Elements elements) {
        this.elements = elements;
    }

    public Books getBook(int id) {
        return book[id];
    }

    public void setBook(Books book, int id) {
        this.book[id] = book;
    }

    public Explorers getExplorer() {
        return explorer;
    }

    public void setExplorer(Explorers explorer) {
        this.explorer = explorer;
    }
}