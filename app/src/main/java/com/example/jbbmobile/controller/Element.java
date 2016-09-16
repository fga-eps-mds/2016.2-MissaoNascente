package com.example.jbbmobile.controller;

import android.content.Context;

import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.model.Elements;

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
        setElement(new Elements(1, 0, 100, null, "Caqui", null));
        getElement().setIdBook(1);
        ElementDAO elementDao = new ElementDAO(context);
        elementDao.insertElement(getElement());
    }
}
