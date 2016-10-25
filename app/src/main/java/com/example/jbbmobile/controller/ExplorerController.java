package com.example.jbbmobile.controller;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;;
import android.util.Log;
import java.util.Date;

import com.example.jbbmobile.dao.ElementExplorerRequest;
import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.dao.UpdateScoreRequest;

public class ExplorerController {
    private boolean action = false;
    private boolean response;

    public ExplorerController(){
    }

    public boolean isAction() {
        return action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public boolean updateExplorerScore( final Context preferenceContext,int score,String email) {
        try{
            Log.i("****Explorer: ",""+score);
            UpdateScoreRequest updateScoreRequest = new UpdateScoreRequest(score,email);
            updateScoreRequest.request(preferenceContext, new UpdateScoreRequest.Callback() {
                @Override
                public void callbackResponse(boolean response) {
                    setResponse(response);
                    setAction(true);
                    if(!response){

                    }
                }
            });
        }catch(SQLiteConstraintException exception){
            throw exception;
        }

        return true;
    }

    public boolean insertExplorerElement(final Context preferenceContext, String email, int idElement, String userImage, String catchDate) {
        try{

            ElementExplorerRequest elementExplorerRequest = new ElementExplorerRequest(email,idElement,userImage,catchDate);
            elementExplorerRequest.request(preferenceContext, new ElementExplorerRequest.Callback() {
                @Override
                public void callbackResponse(boolean response) {
                    setResponse(response);
                    setAction(true);
                    if(!response){

                    }
                }
            });
        }catch(SQLiteConstraintException exception){
            throw exception;
        }

        return true;
    }
}
