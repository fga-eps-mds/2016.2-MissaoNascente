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

    public Register(String nickname, String email, String password, Context applicationContext){
        setExplorers(new Explorers(nickname, email, password));

        ExplorerDAO explorerDAO = new ExplorerDAO(applicationContext);
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
