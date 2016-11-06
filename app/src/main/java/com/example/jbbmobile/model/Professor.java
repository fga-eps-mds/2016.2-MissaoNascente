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
        assert drawables != null;
        return drawables;
    }

    public void setDrawables(ArrayList<Drawable> drawables) {
        assert drawables != null;
        this.drawables = drawables;
    }

    public ArrayList<String> getDialogs() {
        assert dialogs != null;
        return dialogs;
    }

    public void setDialogs(ArrayList<String> dialogs) {
        assert dialogs != null;
        this.dialogs = dialogs;
    }
}
