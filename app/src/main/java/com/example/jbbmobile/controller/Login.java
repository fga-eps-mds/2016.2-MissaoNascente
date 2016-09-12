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
            saveFile(explorer.getEmail(),explorer.getPassword(),explorer.getNickname(),context);
            return true;
        }
        return false;
    }



    private void saveFile(String email,String password,String nickname, Context context) throws IOException {
        FileOutputStream fileOut = context.openFileOutput("Explorer", MODE_PRIVATE);
        fileOut.write(email.getBytes());
        fileOut.write("\n".getBytes());
        fileOut.write(password.getBytes());
        fileOut.write("\n".getBytes());
        fileOut.write(nickname.getBytes());
        fileOut.close();

    }

    public void deleteFile(Context context){
        context.deleteFile("Explorer");
    }

    public void loadFile(Context context) {
        String email="",password="",nickname="";
        int c;
        try {
            FileInputStream fileIn = context.openFileInput("Explorer");
            c=fileIn.read();

            while ((char)c!= '\n'){
                email+=(char)c;
                c=fileIn.read();
            }
            c=fileIn.read();
            while ((char)c!= '\n'){
                password+=(char)c;
                c=fileIn.read();
            }
            c=fileIn.read();
            while (c!= -1){
                nickname+=(char)c;
                c=fileIn.read();
            }

            fileIn.close();

            getExplorer().setEmail(email);
            getExplorer().setPassword(password);
            getExplorer().setNickname(nickname);

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
