package com.example.jbbmobile.controller;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Explorer;


public class RegisterController {

    private Explorer explorer;

    public RegisterController(){

    }

    public void Register (String nickname, String email, String password,String confirmPassword, Context applicationContext)throws SQLiteConstraintException{
        try {
            setExplorers(new Explorer(nickname, email, password, confirmPassword));
            ExplorerDAO explorerDAO = new ExplorerDAO(applicationContext);
            int errorRegister = -1;
            if (explorerDAO.insertExplorer(getExplorer()) == errorRegister) {
                throw new SQLiteConstraintException();
            }
        }catch (IllegalArgumentException exception){

            if((exception.getLocalizedMessage()).equals("nick")){
                throw new IllegalArgumentException("wrongNickname");
            }
            if((exception.getLocalizedMessage()).equals("password")){
                throw new IllegalArgumentException("wrongPassword");
            }
            if((exception.getLocalizedMessage()).equals("confirmPassword")){
                throw new IllegalArgumentException("wrongConfirmPassword");
            }
            if((exception.getLocalizedMessage()).equals("email")){
                throw new IllegalArgumentException("wrongEmail");
            }
        }
    }

    public void Register(String nickname, String email, Context context){
        setExplorers(new Explorer());
        getExplorer().googleExplorer(nickname, email);
        ExplorerDAO explorerDAO = new ExplorerDAO(context);
        explorerDAO.insertExplorer(getExplorer());
    }

    public Explorer getExplorer() {
        return explorer;
    }

    public void setExplorers(Explorer explorer) {
        this.explorer = explorer;
    }
}
