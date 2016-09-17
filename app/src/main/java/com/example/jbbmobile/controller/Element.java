package com.example.jbbmobile.controller;

import android.content.Context;
import android.util.Log;

import com.example.jbbmobile.R;
import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.model.Elements;

import java.util.ArrayList;
import java.util.List;

public class Element {
    private Elements element;

    public Element(){

    }

    public Element(Context context){
        createElement(context);
    }

    public Elements getElement() {
        return element;
    }

    public void setElement(Elements element) {
        this.element = element;
    }

    public void createElement(Context context){
        List <String> description = new ArrayList<String>();

        description.add("Pequi - Caryocar brasiliense");
        description.add("Natural do Cerrado");
        description.add("Os frutos são drupáceos, oleaginosos e aromáticos");
        description.add("As flores são grandes e com estames compridos");

        setElement(new Elements(0, 0, 100, "pequi", "Pequi", "pequi", 0, 0, description));
        ElementDAO elementDao = new ElementDAO(context);
        elementDao.createTablesIfTheyDoesntExist(elementDao.getWritableDatabase());
        elementDao.insertInformation(getElement());
        elementDao.insertDescription(getElement());
        elementDao.insertElement(getElement());

        description.add("Caqui - Caryocar brasiliense");
        description.add("Natural do Cerrado");
        description.add("Os frutos são drupáceos, oleaginosos e aromáticos");
        description.add("As flores são grandes e com estames compridos");

        setElement(new Elements(1, 0, 100, "pequi", "Caqui", "pequi", 1, 1, description));
        elementDao.createTablesIfTheyDoesntExist(elementDao.getWritableDatabase());
        elementDao.insertInformation(getElement());
        elementDao.insertDescription(getElement());
        elementDao.insertElement(getElement());

        setElement(new Elements(2, 0, 100, "pequi", "Sushi", "pequi", 2, 2, description));
        elementDao.createTablesIfTheyDoesntExist(elementDao.getWritableDatabase());
        elementDao.insertInformation(getElement());
        elementDao.insertDescription(getElement());
        elementDao.insertElement(getElement());
    }
}
