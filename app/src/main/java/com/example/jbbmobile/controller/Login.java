package com.example.jbbmobile.controller;

import android.content.Context;
import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Explorers;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ronyell on 10/09/16.
 */
public class Login implements Serializable {
    private Explorers explorer;
    public Login(String email, String password, Context context){
        explorer=new Explorers();
        try {
            realizeLogin(email,password,context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Login(){
        explorer = new Explorers();
    }

    public boolean realizeLogin(String email, String password, Context context) throws IOException {
        ExplorerDAO db = new ExplorerDAO(context);
        Explorers explorer = db.findExplorer(new Explorers(email,password));
        db.close();

        if (explorer == null || explorer.getEmail() == null || explorer.getPassword() == null) {
            return false;
        }
        else if(explorer.getEmail().equals(email) && explorer.getPassword().equals(password)){
            getExplorer().setNickname(explorer.getNickname());
            getExplorer().setEmail(explorer.getEmail());
            getExplorer().setPassword(explorer.getPassword());
            
            return true;
        }
        return false;
    }

    public Explorers getExplorer() {
        return explorer;
    }
}
