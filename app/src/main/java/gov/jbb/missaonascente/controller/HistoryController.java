package gov.jbb.missaonascente.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import gov.jbb.missaonascente.dao.BookDAO;
import gov.jbb.missaonascente.dao.ElementDAO;
import gov.jbb.missaonascente.dao.ExplorerDAO;
import gov.jbb.missaonascente.model.Element;
import gov.jbb.missaonascente.model.Explorer;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HistoryController {
    private Element element;
    private static final String PREF_NAME = "HistorySave";
    private static  final  String PREF_CURRENTSAVE = "currentElement";
    private static  final  String PREF_TIMESAVE = "timeSave";
    private Context context;
    private int currentElement;
    private ElementDAO elementDAO;
    private int idBook;

    public  HistoryController() {
    }

    public  HistoryController(Context context){
        this.element = new Element();
        this.context = context;
        this.elementDAO = new ElementDAO(context);

        BooksController booksController = new BooksController();
        booksController.currentPeriod();
        idBook = booksController.getCurrentPeriod();

        loadSave();
        initialHistoryElement();
        restartHistory();

    }

    public void getElementsHistory(){
        loadSave();
        setElement(elementDAO.findElementHistory(idBook, currentElement));
    }

    public boolean sequenceElement(int history, Explorer explorer){
        boolean returnSequence = false;

        if(currentElement == history) {
            historyBonusScore(explorer);
            saveBeginHistoryDate(history);
            autoSave();
            returnSequence = true;
        }

        return returnSequence;
    }

    private void autoSave() {
        int currentElement = this.currentElement;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(element!=null){
            currentElement = element.getHistory()+1;
            setElement(elementDAO.findElementHistory(idBook, currentElement));
        }

        editor.putInt(PREF_CURRENTSAVE , currentElement);
        editor.apply();
    }

    public void loadSave() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        this.currentElement = sharedPreferences.getInt(PREF_CURRENTSAVE, -1);
    }

    public void deleteSave(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(PREF_CURRENTSAVE);
        editor.remove(PREF_TIMESAVE);
        editor.apply();
    }

    private void historyBonusScore(Explorer explorer) {
        int bonusHistory = 0;

        ExplorerDAO explorerDAO = new ExplorerDAO(context);
        ExplorerController explorerController = new ExplorerController();

        if(element.getHistory()!=0) {
            bonusHistory = element.getElementScore();
        }

        explorer.updateScore(bonusHistory);
        explorerDAO.updateExplorer(explorer);
        explorerController.updateExplorerScore(context, explorer.getScore(), explorer.getEmail());
    }

    private void initialHistoryElement(){
        if(currentElement == -1){
            setCurrentElement(1);
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putInt(PREF_CURRENTSAVE , currentElement);
            editor.apply();
        }
    }



    private void saveBeginHistoryDate(int history) {
        if(history == 1) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putLong(PREF_TIMESAVE , System.currentTimeMillis());
            editor.apply();
        }
    }

    private void restartHistory() {
        long initialTime;

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        initialTime = sharedPreferences.getLong(PREF_TIMESAVE, -1);

        if(initialTime != -1) {
            long elapsedTime;

            elapsedTime = (System.currentTimeMillis() - initialTime)/(24 * 60 * 60 * 1000);

            if(elapsedTime>=1){
                this.currentElement = 1;

                SharedPreferences sharedPreferences1 = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.putInt(PREF_CURRENTSAVE, this.currentElement);
                editor.remove(PREF_TIMESAVE);
                editor.apply();
            }
        }
    }

    public boolean endHistory(){
        boolean endHistory = false;

        int lastElement = elementDAO.findLastElementHistory(idBook);

        if(lastElement<currentElement){
            endHistory = true;
        }

        return  endHistory;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public int getCurrentElement() {
        return currentElement;
    }

    public void setCurrentElement(int currentElement) {
        this.currentElement = currentElement;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }
}
