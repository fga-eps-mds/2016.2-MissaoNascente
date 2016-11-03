package com.example.jbbmobile.model;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class Professor {
    private ArrayList <String> dialogs;
    private ArrayList <Drawable> drawables;

    public Professor(){
        setDialogs(new ArrayList<String>());
        setDrawables(new ArrayList<Drawable>());
    }

    public Professor(ArrayList <String> dialogs, ArrayList<Drawable> drawables){
        setDialogs(dialogs);
        setDrawables(drawables);
    }

    public ArrayList<Drawable> getDrawables() {
        return drawables;
    }

    public void setDrawables(ArrayList<Drawable> drawables) {
        this.drawables = drawables;
    }

    public ArrayList<String> getDialogs() {
        return dialogs;
    }

    public void setDialogs(ArrayList<String> dialogs) {
        this.dialogs = dialogs;
    }
}
