package com.example.jbbmobile.controller;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Explorer;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class RegisterController {

    private Explorer explorer;

    public RegisterController(){

    }

    public void Register (String nickname, String email, String password,String confirmPassword, Context applicationContext)throws SQLiteConstraintException{
        try {
            setExplorers(new Explorer(nickname, email, password, confirmPassword));
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
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void Register(String nickname, String email, Context context){
        setExplorers(new Explorer());
        getExplorer().googleExplorer(nickname, email);
        ExplorerDAO explorerDAO = new ExplorerDAO(context);
        explorerDAO.insertExplorer(getExplorer());
    }

    public List<Explorer> getExplorersList(Context context){
        ExplorerDAO dao = new ExplorerDAO(context);
        List<Explorer> Explorers = dao.findExplorers();
        dao.close();

        return Explorers;
    }

    public Explorer getExplorer() {
        return explorer;
    }

    public void setExplorers(Explorer explorer) {
        this.explorer = explorer;

    }



}
