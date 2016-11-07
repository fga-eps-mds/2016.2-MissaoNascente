package com.example.jbbmobile.controller;

import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.jbbmobile.R;
import com.example.jbbmobile.view.ProfessorFragment;

import java.util.ArrayList;

/**
 * Created by joao on 03/11/16.
 */

public class ProfessorController {
    AppCompatActivity activity;
    ProfessorFragment professorFragment;

    public ProfessorFragment createProfessorFragment(AppCompatActivity activity, ArrayList<String> dialogs, ArrayList<Drawable> drawables){
        if(dialogs.size() == 0) {
            throw new IllegalArgumentException("Dialogs Array can't be empty");
        }
        if(drawables.size() == 0){
            throw new IllegalArgumentException("Drawable Array can't be empty");
        }

        this.activity = activity;

        android.support.v4.app.FragmentManager fragmentManager = this.activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        professorFragment = new ProfessorFragment();
        professorFragment.setDialogs(dialogs);
        professorFragment.setDrawables(drawables);

        fragmentTransaction.replace(R.id.professor_fragment, professorFragment, "ProfessorFragment");
        fragmentTransaction.commitAllowingStateLoss();


        return professorFragment;
    }

    public ProfessorFragment createProfessorFragment(AppCompatActivity activity, ArrayList<String> dialogs, Drawable drawable){
        ArrayList<Drawable> drawables = new ArrayList<>();
        drawables.add(drawable);
        ProfessorFragment professorFragment = createProfessorFragment(activity, dialogs, drawables);

        return professorFragment;
    }
}
