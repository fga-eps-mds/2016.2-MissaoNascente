package com.example.jbbmobile.controller;

import android.content.Context;
import android.util.Log;

import com.example.jbbmobile.R;
import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.model.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roger on 15/09/16.
 */
public class Element {
    private Elements element;

    public Element(Context context){
        createElement(context);
    }

    public Elements getElement() {
        return element;
    }

    public void setElement(Elements element) {
        this.element = element;
    }

    private void createElement(Context context){
        List <String> description = new ArrayList<String>();

        description.add("Pequi - Caryocar brasiliense");
        description.add("Natural do Cerrado");
        description.add("Os frutos são drupáceos, oleaginosos e aromáticos");
        description.add("As flores são grandes e com estames compridos");

        setElement(new Elements(1, 0, 100, "pequi", "Pequi", "pequi", 1, 1, description));
        ElementDAO elementDao = new ElementDAO(context);
        elementDao.createTablesIfTheyDoesntExist(elementDao.getWritableDatabase());
        elementDao.insertInformation(getElement());
        elementDao.insertDescription(getElement());
        elementDao.insertElement(getElement());
    }
}
