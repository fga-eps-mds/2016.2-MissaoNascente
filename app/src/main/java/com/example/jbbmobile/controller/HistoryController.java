package com.example.jbbmobile.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Element;
import com.example.jbbmobile.model.Explorer;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HistoryController {
    private List<Element> elements;
    private static final String PREF_NAME = "HistorySave";
    private static  final  String PREF_CURRENTSAVE = "currentElement";
    private static  final  String PREF_TIMESAVE = "timeSave";
    private Context context;
    private int currentElement;
    private int beginHistory;

    public  HistoryController(Context context){
        this.elements = new ArrayList<>();
        this.context = context;

        Log.i("%%%%%%%%"," "+ currentElement +", "+ beginHistory);


        loadSave();
        initialHistoryElement();
        restartHistory();

        Log.i("%%%%%%%%"," "+ currentElement +", "+ beginHistory);
    }

    public void getElementsHistory(){
        BooksController booksController = new BooksController();
        booksController.currentPeriod();
        int idBook = booksController.getCurrentPeriod();

        loadSave();
        ElementDAO elementDAO = new ElementDAO(context);
        this.elements = elementDAO.findElementsHistory(idBook, currentElement);
    }

    public boolean sequenceElement(int idElement, Explorer explorer){
        boolean returnSequence = false;

        if(currentElement == idElement) {
            historyBonusScore(explorer);
            getElements().remove(0);
            saveBeginHistoryDate(idElement);
            autoSave();
            returnSequence = true;
        }


        for (Element element : getElements()){
            Log.i("=======",element.getIdElement()+" "+element.getNameElement()+" "+element.getHistoryMessage());
        }

        return returnSequence;
    }

    private void autoSave() {
        int currentElement = this.currentElement;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(elements.size()>0){
            currentElement = elements.get(0).getIdElement();
        }else if(this.currentElement != -1){
            currentElement = -10;
        }

        editor.putInt(PREF_CURRENTSAVE , currentElement);
        editor.apply();
    }

    public void loadSave() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        this.currentElement = sharedPreferences.getInt(PREF_CURRENTSAVE, -1);
        Log.i("===LOAD===", " " + currentElement);
    }

    public void deleteSave() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(PREF_CURRENTSAVE);
        editor.apply();
    }

    private void historyBonusScore(Explorer explorer) {
        ExplorerDAO explorerDAO = new ExplorerDAO(context);
        ExplorerController explorerController = new ExplorerController();
        int bonusHistory = 0;
        if(elements.size()>0) {
            bonusHistory = elements.get(0).getElementScore();
        }
        explorer.updateScore(bonusHistory);
        explorerDAO.updateExplorer(explorer);
        explorerController.updateExplorerScore(context, explorer.getScore(), explorer.getEmail());
    }

    private void initialHistoryElement(){
        firstHistoryElement();

        if(currentElement == -1){
            this.currentElement = this.beginHistory;
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putInt(PREF_CURRENTSAVE , currentElement);
            editor.apply();
        }
    }

    private void firstHistoryElement() {
        BooksController booksController = new BooksController();
        booksController.currentPeriod();
        int idBook = booksController.getCurrentPeriod();

        ElementDAO elementDAO = new ElementDAO(context);
        this.beginHistory = elementDAO.findFirstElementHistory(idBook);
    }

    public void saveBeginHistoryDate(int idElement) {
        if(idElement == beginHistory) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putLong(PREF_TIMESAVE , System.currentTimeMillis());
            editor.apply();
        }
    }

    public void restartHistory() {
        long initialTime;

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        initialTime = sharedPreferences.getLong(PREF_TIMESAVE, -1);
        Log.i("+++", " " + initialTime + ", " + currentElement +" " );
        if(initialTime != -1) {
            long elapsedTime;

            elapsedTime = (System.currentTimeMillis() - initialTime)/(24 * 60 * 60 * 1000);

            if(elapsedTime>=1){
                this.currentElement = this.beginHistory;

                SharedPreferences sharedPreferences1 = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.putInt(PREF_CURRENTSAVE, this.currentElement);
                editor.remove(PREF_TIMESAVE);
                editor.apply();

            }
            Log.i("+++", " " + elapsedTime);

        }
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public int getCurrentElement() {
        return currentElement;
    }

    public void setCurrentElement(int currentElement) {
        this.currentElement = currentElement;
    }
}
