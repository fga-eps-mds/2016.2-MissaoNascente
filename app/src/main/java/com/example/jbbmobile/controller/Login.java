package com.example.jbbmobile.controller;

import android.content.Context;
import android.util.Log;

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
public class Login {
    private Explorers explorer;

    public Login(){
        explorer = new Explorers();
    }

    public boolean realizeLogin(String email, String password, Context context) {
        ExplorerDAO db = new ExplorerDAO(context);
        Explorers explorer = db.findExplorer(new Explorers(email,password));
        db.close();

        if (explorer == null || explorer.getEmail() == null || explorer.getPassword() == null) {
            return false;
        }
        else if(explorer.getEmail().equals(email) && explorer.getPassword().equals(password)){
            saveFile(explorer.getEmail(),context);

            return true;
        }
        return false;
    }

    public boolean realizeLogin(String email, Context context) throws IOException{
        ExplorerDAO db = new ExplorerDAO(context);
        Explorers explorer = db.findExplorer(new Explorers(email));
        db.close();


        if (explorer == null || explorer.getEmail() == null) {
            return false;
        }
        else if(explorer.getEmail().equals(email)){
            saveFile(explorer.getEmail(),context);

            return true;
        }
        return false;
    }



    private void saveFile(String email, Context context) {

        FileOutputStream fileOut = null;
        try {

            fileOut = context.openFileOutput("Explorer", MODE_PRIVATE);

            fileOut.write(email.getBytes());
            fileOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void deleteFile(Context context){
        context.deleteFile("Explorer");
    }

    public void loadFile(Context context) {
        String email="";
        int c;
        try {
            FileInputStream fileIn = context.openFileInput("Explorer");
            c=fileIn.read();

            while (c!= -1){
                email+=(char)c;
                c=fileIn.read();
            }

            fileIn.close();

            ExplorerDAO db = new ExplorerDAO(context);
            Explorers explorer = db.findExplorer(new Explorers(email));
            db.close();


            this.explorer= new Explorers(explorer.getEmail(),explorer.getNickname(),explorer.getPassword());


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public Explorers getExplorer() {

        return explorer;

    }
}
