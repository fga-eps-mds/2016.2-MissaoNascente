package com.example.jbbmobile.model;

import java.util.List;

public class Book {
    private int idBook;
    private String nameBook;
    private List<Element> elements;

    public Book(){}

    public Book(int idBook, String nameBook, List<Element> elements){
        setIdBook(idBook);
        setNameBook(nameBook);
        setElements(elements);
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        if(validateIdBook(idBook)){
            this.idBook = idBook;
        }else {
           throw new IllegalArgumentException();
        }
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        if(validateNameBook(nameBook)){
            this.nameBook = nameBook;
        }else {
            throw new IllegalArgumentException();
        }
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public boolean validateIdBook(int idBook){
        return (idBook > 0) && (idBook < 4);
    }

    private boolean validateNameBook(String nameBook){
        return nameBook != null;
    }
}