package com.example.jbbmobile.controller;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.jbbmobile.R;
import com.example.jbbmobile.view.ProfessorFragment;
import com.example.jbbmobile.view.RegisterElementFragment;

import java.util.ArrayList;

/**
 * Created by joao on 03/11/16.
 */

public class ProfessorController {
    ProfessorFragment professorFragment;

    public ProfessorFragment createProfessorFragment(AppCompatActivity activity, ArrayList<String> dialogs, ArrayList<Drawable> drawables){
        android.support.v4.app.FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        professorFragment = new ProfessorFragment();
        professorFragment.setDialogs(dialogs);
        professorFragment.setDrawables(drawables);

        fragmentTransaction.add(R.id.professor_fragment, professorFragment, "ProfessorFragment");
        fragmentTransaction.commit();

        return professorFragment;
    }
}
