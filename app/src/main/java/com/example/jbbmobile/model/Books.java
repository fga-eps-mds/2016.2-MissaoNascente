package com.example.jbbmobile.model;

import java.util.List;

public class Books {
    private int idBook;
    private String nameBook;
    private List<Elements> elements;
    private Explorers explorer;

    public Books(){

    }

    public Books(int idBook, String nameBook, List elements){

        setIdBook(idBook);
        setNameBook(nameBook);
        setElements(elements);
    }

    public Books(int idBook, String nameBook, List elements, Explorers explorer){

        setIdBook(idBook);
        setNameBook(nameBook);
        setElements(elements);
        setExplorer(explorer);
    }

    public Books(int idBook){
        setIdBook(idBook);
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

    public List<Elements> getElements() {
        return elements;
    }

    public void setElements(List<Elements> elements) {
        this.elements = elements;
    }

    public Explorers getExplorer() {
        return explorer;
    }

    public void setExplorer(Explorers explorer) {
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