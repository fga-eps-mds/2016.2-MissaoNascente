package com.example.jbbmobile.model;

import java.util.List;

public class Book {
    private int idBook;
    private String nameBook;
    private List<Element> elements;
    private Explorer explorer;

    public Book(){

    }

    public Book(int idBook, String nameBook, List elements){
        setIdBook(idBook);
        setNameBook(nameBook);
        setElements(elements);
    }

    public Book(int idBook, String nameBook, List elements, Explorer explorer){
        setIdBook(idBook);
        setNameBook(nameBook);
        setElements(elements);
        setExplorer(explorer);
    }
/*
    public Book(int idBook){
        setIdBook(idBook);
    }*/

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

    public Explorer getExplorer() {
        return explorer;
    }

    public void setExplorer(Explorer explorer) {
        this.explorer = explorer;
    }


    public boolean validateIdBook(int idBook){
        if(idBook >=0){
            return true;
        }else{
            return false;
        }
    }

    private boolean validateNameBook(String nameBook){
        if(nameBook!=null){
            return true;

        }else {
            return false;
        }
    }
}