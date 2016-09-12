package com.example.jbbmobile.controller;

import android.content.Context;
import android.util.Log;

import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Explorers;


/**
 * Created by roger on 11/09/16.
 */
public class Preference {
    private ExplorerDAO dao;
    private Explorers explorer;

    public void updateNickname(String newNickname, String email, Context preferenceContext){
        setDao(new ExplorerDAO(preferenceContext));
        /* Create an explorer, so we can search his register by email */
        setExplorer(new Explorers());
        getExplorer().setEmail(email);
        setExplorer(getDao().findExplorer(getExplorer()));
        /* Now that we found the explorer that will be update, lets change the nickname */
        getExplorer().setNickname(newNickname);
        /* Send the updated object to update */
        getDao().updateExplorer(getExplorer());

    }

    public void deleteExplorer(String password, String email, Context context){
        Explorers tempExplorer = new Explorers();
        tempExplorer.setPassword(password);
        setDao(new ExplorerDAO(context));
        setExplorer(new Explorers());
        getExplorer().setEmail(email);
        setExplorer(getDao().findExplorer(getExplorer()));

        if(getExplorer().setPassword(password, getExplorer().getPassword())){
            getDao().deleteExplorer(getExplorer());
        }else{
            //Error
        }

    }

    public Explorers getExplorer() {
        return explorer;
    }

    public void setExplorer(Explorers explorer) {
        this.explorer = explorer;
    }

    public ExplorerDAO getDao() {
        return dao;
    }

    public void setDao(ExplorerDAO dao) {
        this.dao = dao;
    }
}
