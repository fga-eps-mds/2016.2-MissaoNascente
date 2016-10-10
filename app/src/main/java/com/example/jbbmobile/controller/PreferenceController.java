package com.example.jbbmobile.controller;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Explorer;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class PreferenceController {
    private ExplorerDAO explorerDAO;
    private Explorer explorer;

    public boolean updateNickname(String newNickname, String email, Context preferenceContext){
        setDao(new ExplorerDAO(preferenceContext));

        /* Create an explorer, so we can search his register by email */
        setExplorer(new Explorer());
        getExplorer().setEmail(email);
        setExplorer(getDao().findExplorer(getExplorer().getEmail()));

        /* Now that we found the explorer that will be update, lets change the nickname */
        getExplorer().setNickname(newNickname);

        /* Send the updated object to update */
        try{
            getDao().updateExplorer(getExplorer());
        }catch(SQLiteConstraintException exception){
            throw exception;
        }
        return true;
    }

    public void deleteExplorer(String password, String email, Context context) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Explorer tempExplorer = new Explorer();
        String passwordDigest;
        passwordDigest = tempExplorer.cryptographyPassword(password);
        tempExplorer.setPassword(passwordDigest);
        setDao(new ExplorerDAO(context));
        setExplorer(new Explorer());
        getExplorer().setEmail(email);
        setExplorer(getDao().findExplorer(email));
        if (getExplorer().validateEqualsPasswords(passwordDigest, explorer.getPassword())) {
            getDao().deleteExplorer(getExplorer());
        }else{
            throw new IllegalArgumentException("confirmPassword");
        }
    }


    public void deleteExplorer(String email, Context context){
        setDao(new ExplorerDAO(context));
        setExplorer(new Explorer());
        getExplorer().setEmail(email);
        setExplorer(getDao().findExplorer(getExplorer().getEmail()));
        getDao().deleteExplorer(getExplorer());
    }

    public Explorer getExplorer() {
        return explorer;
    }

    public void setExplorer(Explorer explorer) {
        this.explorer = explorer;
    }

    public ExplorerDAO getDao() {
        return explorerDAO;
    }

    public void setDao(ExplorerDAO explorerDAO) {
        this.explorerDAO = explorerDAO;
    }
}
