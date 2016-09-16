package com.example.jbbmobile.model;


import android.content.res.Resources;

import java.util.List;

/**
 * Created by ronyell on 14/09/16.
 */
public class Books {
    private int idBook;

    private String nameBook;

    private List<Elements> elementses;

    public Books(){

    }


    public Books(int idBook, String nameBook, List elements){

        setIdBook(idBook);
        setNameBook(nameBook);
        setElementses(elements);
    }

    public Books(int idBook){
        setIdBook(idBook);
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

    public List<Elements> getElementses() {
        return elementses;
    }

    public void setElementses(List<Elements> elementses) {
        this.elementses = elementses;
    }
}
