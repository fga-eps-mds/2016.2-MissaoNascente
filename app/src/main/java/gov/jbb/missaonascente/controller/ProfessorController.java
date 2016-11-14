package gov.jbb.missaonascente.controller;

import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import gov.jbb.missaonascente.R;
import gov.jbb.missaonascente.view.ProfessorFragment;

import java.util.ArrayList;

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

        professorFragment = new ProfessorFragment();
        professorFragment.setDialogs(dialogs);
        professorFragment.setDrawables(drawables);

        return professorFragment;
    }

    public ProfessorFragment createProfessorFragment(AppCompatActivity activity, ArrayList<String> dialogs, Drawable drawable){
        ArrayList<Drawable> drawables = new ArrayList<>();
        drawables.add(drawable);
        ProfessorFragment professorFragment = createProfessorFragment(activity, dialogs, drawables);

        return professorFragment;
    }
}
