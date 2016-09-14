package com.example.jbbmobile.model;

import java.util.List;

/**
 * Created by ronyell on 14/09/16.
 */
public class Book {
    private int idBook;
    private String nameBook;
    private List elements;

    public Book(){

    }

    public Book(int idBook, String nameBook, List elements){
        setIdBook(idBook);
        setNameBook(nameBook);
        setElements(elements);
    }
    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public List getElements() {
        return elements;
    }

    public void setElements(List elements) {
        this.elements = elements;
    }
}
