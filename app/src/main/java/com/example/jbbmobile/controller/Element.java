package com.example.jbbmobile.controller;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jbbmobile.R;
import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.model.Books;
import com.example.jbbmobile.model.Elements;

import java.util.ArrayList;
import java.util.List;

public class Element {
    private Elements element;

    public Element(){

    }

    public Elements findElementByID(int idElement, Context context){
        setElement(new Elements());
        getElement().setIdElement(idElement);

        ElementDAO elementDAO = new ElementDAO(context);
        setElement(elementDAO.findElement(getElement()));
        return getElement();
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
        ElementDAO elementDao = new ElementDAO(context);
        elementDao.createTablesIfTheyDoesntExist(elementDao.getWritableDatabase());

        List <String> description = new ArrayList<String>();
        description.add("Espécie comum no Cerrado. A casca de se tronco tem aspecto poroso, sendo extremamente macio, apresenta folhas coriáceas que caem no " +
                    "período da seca, florescem entre março e maio, suas flores apresentam tons brancos e rosadas e são polinizadas por abelhas.");
        description.add("Os frutos secos aparecem de julho a setembro, não são comestíveis.");
        description.add("Os frutos começam a abrir no final de agosto e as sementes aladas podem ser vistas, sua forma alada facilita a dispersão pelo vento.");
        description.add("As flores são grandes e com e stames compridos");
        setElement(new Elements(0, 0, 100, "btn_google_dark_normal", "Pau-Santo", "null", 3, 0, description));
        elementDao.insertInformation(getElement());
        elementDao.insertDescription(getElement());
        elementDao.insertElement(getElement());

        description = new ArrayList<String>();
        description.add("IpÊ - Nome Científico");
        description.add("Natural do Cerrado");
        description.add("Os frutos são ...");
        description.add("As flores são ...");
        setElement(new Elements(1, 0, 100, "btn_google_dark_normal", "Ipê", "Ipê", 3, 2, description));
        elementDao.createTablesIfTheyDoesntExist(elementDao.getWritableDatabase());
        elementDao.insertInformation(getElement());
        elementDao.insertDescription(getElement());
        elementDao.insertElement(getElement());

        description = new ArrayList<String>();
        description.add("Caqui - Caryocar brasiliense");
        description.add("Natural do Cerrado");
        description.add("Os frutos são drupáceos, oleaginosos e aromáticos");
        description.add("As flores são grandes e com estames compridos");
        setElement(new Elements(2, 0, 100, "btn_google_dark_normal", "Caqui", "pequi", 1, 1, description));
        elementDao.createTablesIfTheyDoesntExist(elementDao.getWritableDatabase());
        elementDao.insertInformation(getElement());
        elementDao.insertDescription(getElement());
        elementDao.insertElement(getElement());

        description = new ArrayList<String>();
        description.add("Salgueiro - Nome Científico");
        description.add("Natural do Cerrado");
        description.add("Os frutos são ...");
        description.add("As flores são ...");
        setElement(new Elements(3, 0, 100, "btn_google_dark_normal", "Salgueiro", "pequi", 1, 4, description));
        elementDao.createTablesIfTheyDoesntExist(elementDao.getWritableDatabase());
        elementDao.insertInformation(getElement());
        elementDao.insertDescription(getElement());
        elementDao.insertElement(getElement());

        description = new ArrayList<String>();
        description.add("Beterraba - Nome Científico");
        description.add("Natural do Cerrado");
        description.add("Os frutos são ...");
        description.add("As flores são ...");
        setElement(new Elements(4, 0, 100, "btn_google_dark_normal", "Beterraba", "pequi", 2, 5, description));
        elementDao.createTablesIfTheyDoesntExist(elementDao.getWritableDatabase());
        elementDao.insertInformation(getElement());
        elementDao.insertDescription(getElement());
        elementDao.insertElement(getElement());

        description = new ArrayList<String>();
        description.add("Uva - Nome Científico");
        description.add("Natural do Cerrado");
        description.add("Os frutos são ...");
        description.add("As flores são ...");
        setElement(new Elements(5, 0, 100, "btn_google_dark_normal", "Uva", "pequi", 2, 6, description));
        elementDao.createTablesIfTheyDoesntExist(elementDao.getWritableDatabase());
        elementDao.insertInformation(getElement());
        elementDao.insertDescription(getElement());
        elementDao.insertElement(getElement());

        description = new ArrayList<String>();
        description.add("Caqui - Caryocar brasiliense");
        description.add("Natural do Cerrado");
        description.add("Os frutos são drupáceos, oleaginosos e aromáticos");
        description.add("As flores são grandes e com estames compridos");
        setElement(new Elements(45, 0, 100, "btn_google_dark_normal", "Caqui", "pequi", 4, 1, description));
        elementDao.createTablesIfTheyDoesntExist(elementDao.getWritableDatabase());
        elementDao.insertElement(getElement());

        description = new ArrayList<String>();
        description.add("Caqui - Caryocar brasiliense");
        description.add("Natural do Cerrado");
        description.add("Os frutos são drupáceos, oleaginosos e aromáticos");
        description.add("As flores são grandes e com estames compridos");
        setElement(new Elements(33, 0, 100, "btn_google_dark_normal", "Caqui", "pequi", 5, 1, description));
        elementDao.createTablesIfTheyDoesntExist(elementDao.getWritableDatabase());
        elementDao.insertElement(getElement());

    }
}
