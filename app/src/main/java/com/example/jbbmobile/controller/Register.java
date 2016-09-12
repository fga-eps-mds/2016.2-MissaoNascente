package com.example.jbbmobile.controller;

import android.content.Context;

import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Explorers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rogerlenke on 09/09/16.
 */
public class Register {

    private Explorers explorer;

    public Register(){

    }

    public boolean Register (String nickname, String email, String password,String confirmPassword, Context applicationContext){
        setExplorers(new Explorers(nickname, email, password,confirmPassword));
        ExplorerDAO explorerDAO = new ExplorerDAO(applicationContext);
        if (explorerDAO.insertExplorer(getExplorer()) == -1) {
            return false;
        }
        return true;
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
