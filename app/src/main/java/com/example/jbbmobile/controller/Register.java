package com.example.jbbmobile.controller;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Explorers;

import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Register {

    private Explorers explorer;

    public Register(){

    }

    public void Register (String nickname, String email, String password,String confirmPassword, Context applicationContext)throws SQLiteConstraintException{
        try {
            setExplorers(new Explorers(nickname, email, password, confirmPassword));
            ExplorerDAO explorerDAO = new ExplorerDAO(applicationContext);
            if (explorerDAO.insertExplorer(getExplorer()) == -1) {
                throw new SQLiteConstraintException();
            }
        }catch (IllegalArgumentException e){

            if((e.getLocalizedMessage()).equals("nick")){
                throw new IllegalArgumentException("wrongNickname");
            }
            if((e.getLocalizedMessage()).equals("password")){
                throw new IllegalArgumentException("wrongPassword");
            }
            if((e.getLocalizedMessage()).equals("confirmPassword")){
                throw new IllegalArgumentException("wrongConfirmPassword");
            }
            if((e.getLocalizedMessage()).equals("email")){
                throw new IllegalArgumentException("wrongEmail");
            }
        }
    }

    public void Register(String nickname, String email, Context context){
        setExplorers(new Explorers());
        getExplorer().googleExplorer(nickname, email);
        ExplorerDAO explorerDAO = new ExplorerDAO(context);
        explorerDAO.insertExplorer(getExplorer());
    }

    public List<Explorers> getExplorersList(Context context){
        ExplorerDAO dao = new ExplorerDAO(context);
        List<Explorers> Explorers = dao.findExplorers();
        dao.close();

        return Explorers;
    }

    public Explorers getExplorer() {
        return explorer;
    }

    public void setExplorers(Explorers explorer) {
        this.explorer = explorer;

    }



}
