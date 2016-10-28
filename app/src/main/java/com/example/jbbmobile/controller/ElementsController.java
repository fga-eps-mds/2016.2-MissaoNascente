package com.example.jbbmobile.controller;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import com.example.jbbmobile.R;
import com.example.jbbmobile.dao.DownloadElementsRequest;
import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.model.Element;

import java.util.List;

public class ElementsController {
    private Element element;
    private boolean action = false;
    private boolean response;

    public ElementsController(){}

    public Element findElementByID(int idElement, String email, Context context){
        setElement(new Element());
        getElement().setIdElement(idElement);

        ElementDAO elementDAO = new ElementDAO(context);

        try {
            setElement(elementDAO.findElementFromRelationTable(idElement, email));
        }catch(IllegalArgumentException ex){
            ex.printStackTrace();
        }

        return getElement();
    }

    private Element getElement() {
        return element;
    }

    private void setElement(Element element) {
        this.element = element;
    }

    public Element associateElementByQrCode(String code, Context context) throws SQLException,IllegalArgumentException{
        ElementDAO elementDAO = new ElementDAO(context);
        int currentBookPeriod, currentBook;
        int qrCodeNumber = Integer.parseInt(code);
        Element element;

        element = elementDAO.findElementByQrCode(qrCodeNumber);
        element.setDate();
        String catchCurrentDate = element.getCatchDate();
        currentBook = element.getIdBook();

        LoginController loginController = new LoginController();
        loginController.loadFile(context);
        String emailExplorer = loginController.getExplorer().getEmail();

        BooksController booksController = new BooksController();
        booksController.currentPeriod();

        currentBookPeriod = booksController.getCurrentPeriod();

        if(currentBook == currentBookPeriod )
            elementDAO.insertElementExplorer(emailExplorer, catchCurrentDate, qrCodeNumber,null);
        else
            throw new IllegalArgumentException(String.valueOf(R.string.invalidPeriod));

        return element;
    }

    public void downloadElementsFromDatabase(final Context context){
        final ElementDAO elementDao = new ElementDAO(context);
        DownloadElementsRequest downloadElementsRequest = new DownloadElementsRequest();

        try {
            downloadElementsRequest.requestSpecificElements(context, new DownloadElementsRequest.Callback() {
                @Override
                public void callbackResponse(List<Element> elements) {
                    for (int i = 0; i < elements.size(); i++) {
                        int result = new ElementDAO(context).deleteElement(elements.get(i));
                        elementDao.insertElement(elements.get(i));
                    }
                }
            });
        }catch(IllegalArgumentException e){
            downloadElementsRequest.request(context, new DownloadElementsRequest.Callback() {
                @Override
                public void callbackResponse(List<Element> elements) {
                    new ElementDAO(context).deleteAllElements();
                    for(int i = 0 ; i < elements.size() ; i++){
                        elementDao.insertElement(elements.get(i));
                    }
                    setAction(true);
                }
            });
        }
    }

    public boolean isAction() {
        return action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

}
