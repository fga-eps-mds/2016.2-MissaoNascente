package com.example.jbbmobile.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.jbbmobile.R;
import com.example.jbbmobile.dao.BookDAO;
import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.model.Books;
import com.example.jbbmobile.model.Elements;
import com.example.jbbmobile.model.Explorers;

import java.util.List;

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
        getBook(0).setElements(elementDAO.findElementsBook(getElements().getIdBook()));
        getElements().setIdBook(1);
        getBook(1).setElements(elementDAO.findElementsBook(getElements().getIdBook()));
        getElements().setIdBook(2);
        getBook(2).setElements(elementDAO.findElementsBook(getElements().getIdBook()));
        Log.i("Elemento 1, livro 0:", getBook(0).getElements().get(0).getNameElement());
    }

    public String[] getElementsName(Context context, int idBook){
        getAllBooksData(context);
        getElementsFromDatabase(context);
        String[] names = new String[getBook(idBook).getElements().size()];
        for(int i=0;i<getBook(idBook).getElements().size();i++){
            names[i] = new String();
            names[i] = getBook(idBook).getElements().get(i).getNameElement();
        }
        return names;
    }

    public int[] getElementsId(Context context, int idBook){
        getAllBooksData(context);
        getElementsFromDatabase(context);
        int[] idElements = new int[getBook(idBook).getElements().size()];
        for(int i=0;i<getBook(idBook).getElements().size();i++){
            idElements[i] = getBook(idBook).getElements().get(i).getIdElement();
        }
        return idElements;
    }

    public int[] getElementsImage(Context context, int idBook){
        getAllBooksData(context);
        getElementsFromDatabase(context);
        int[] elementsImage = new int[getBook(idBook).getElements().size()];
        for(int i=0;i<getBook(idBook).getElements().size();i++){
            elementsImage[i] = context.getResources().getIdentifier(getBook(idBook).getElements().get(i).getDefaultImage(),"drawable",context.getPackageName());
        }
        return elementsImage;
    }

    public Books findBook(int idbook, Context context){
        Books book = new Books();
        book.setIdBook(idbook);
        BookDAO bookDAO = new BookDAO(context);

        book = bookDAO.findBook(book);
        return book;
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