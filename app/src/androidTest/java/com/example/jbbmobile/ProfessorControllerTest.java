package com.example.jbbmobile;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.test.rule.ActivityTestRule;

import com.example.jbbmobile.controller.ProfessorController;
import com.example.jbbmobile.view.MainScreenActivity;
import com.example.jbbmobile.view.ProfessorFragment;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class ProfessorControllerTest {
    @Rule
    public final ActivityTestRule<MainScreenActivity> mainScreen;
    private Context context;
    private ProfessorController professorController;
    private ProfessorFragment professorFragment;

    public ProfessorControllerTest(){
        mainScreen = new ActivityTestRule<>(MainScreenActivity.class);
        context = mainScreen.getActivity();
        professorController = new ProfessorController();
    }

    @Test
    public void testIfProfessorIsCreatedWithValidParameters(){
        ArrayList<String> dialogs = new ArrayList<>();
        dialogs.add("First dialog");

        ArrayList<Drawable> drawables = new ArrayList<>();
        drawables.add(new ColorDrawable(Color.BLUE));

        professorFragment = professorController.createProfessorFragment(mainScreen.getActivity(),
                dialogs, drawables);

        assertNotNull(professorFragment);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIfProfessorIsNotCreatedWithEmptyDialogs(){
        ArrayList<String> dialogs = new ArrayList<>();

        ArrayList<Drawable> drawables = new ArrayList<>();
        drawables.add(new ColorDrawable(Color.BLUE));

        professorFragment = professorController.createProfessorFragment(mainScreen.getActivity(),
                dialogs, drawables);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIfProfessorIsNotCreatedWithEmptyDrawables(){
        ArrayList<String> dialogs = new ArrayList<>();
        dialogs.add("First dialog");

        ArrayList<Drawable> drawables = new ArrayList<>();

        professorFragment = professorController.createProfessorFragment(mainScreen.getActivity(),
                dialogs, drawables);
    }

    @Test
    public void testIfProfessorIsCreatedWithOneDrawable(){
        ArrayList<String> dialogs = new ArrayList<>();
        dialogs.add("First dialog");

        Drawable drawable = new ColorDrawable(Color.BLUE);

        professorFragment = professorController.createProfessorFragment(mainScreen.getActivity(),
                dialogs, drawable);

        assertNotNull(professorFragment);
    }
}
