package com.example.jbbmobile.controller;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;;
import android.util.Log;

import java.util.List;

import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.dao.ElementExplorerRequest;
import com.example.jbbmobile.dao.UpdateScoreRequest;
import com.example.jbbmobile.model.Element;

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
            setAction(false);
            ElementExplorerRequest elementExplorerRequest = new ElementExplorerRequest(email,idElement,userImage,catchDate);
            elementExplorerRequest.requestUpdateElements(preferenceContext, new ElementExplorerRequest.Callback() {
                @Override
                public void callbackResponse(boolean response) {
                    setResponse(true);
                    setAction(true);
                    if(!response){

                    }
                }

                @Override
                public void callbackResponse(List<Element> elements) {

                }
            });
        }catch(SQLiteConstraintException exception){
            throw exception;
        }

        return true;
    }

    public void updateElementExplorerTable(final Context context, final String email){

        setAction(false);

        final ElementExplorerRequest elementExplorerRequest = new ElementExplorerRequest(email);
        elementExplorerRequest.requestRetriveElements(context, new ElementExplorerRequest.Callback() {
            @Override
            public void callbackResponse(boolean response) {
            }

            @Override
            public void callbackResponse(List<Element> elements) {
                ElementDAO database = new ElementDAO(context);
                database.deleteAllElementsFromElementExplorer(database.getWritableDatabase());
                for(int i = 0; i < elements.size(); i++){
                    Element element = elements.get(i);
                    Log.i("Entrou aqui", "Entrou");
                    int resposta = database.insertElementExplorer(element.getIdElement(), email,
                            element.getCatchDate(), "");
                    Log.d("Date", elements.get(0).getCatchDate());
                    Log.d("idElement", String.valueOf(elements.get(0).getIdElement()));
                    Log.d("Resposta", String.valueOf(resposta));
                }

                setAction(true);
            }
        });
    }
}
