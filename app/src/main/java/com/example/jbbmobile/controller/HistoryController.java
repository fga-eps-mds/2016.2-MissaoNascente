package com.example.jbbmobile.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.model.Element;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HistoryController {
    private List<Element> elements;
    private static final String PREF_NAME = "HistorySave";
    private  static  final  String PREF_CURRENTSAVE = "currentElement";
    private  Context context;

    public  HistoryController(Context context){
        this.elements = new ArrayList<>();
        this.context = context;
    }

    public void getElementsHistory(){
        BooksController booksController = new BooksController();
        booksController.currentPeriod();
        int idBook = booksController.getCurrentPeriod();

        ElementDAO elementDAO = new ElementDAO(context);
        this.elements = elementDAO.findElementsHistory(idBook);
    }

    public void sequenceElement(int idElement){
        int idCurrentElement =  getElements().get(0).getIdElement();

        if(idCurrentElement == idElement){
            getElements().remove(0);
        }
        for (Element element : getElements()){
            Log.i("=======",element.getIdElement()+" "+element.getNameElement()+" "+element.getHistoryMessage());
        }
    }

    private void autoSave() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PREF_CURRENTSAVE , elements.get(0).getIdElement());
        editor.apply();
    }

    private void loadSave() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        int saveElement;
        if ((saveElement = sharedPreferences.getInt(PREF_CURRENTSAVE, 0)) != 0) {
            while(getElements().get(0).getIdBook()<= saveElement){
                getElements().remove(0);
            }
        }
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

}
