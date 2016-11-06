package com.example.jbbmobile;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.example.jbbmobile.controller.LoginController;
import com.example.jbbmobile.controller.ProfessorController;
import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Explorer;
import com.example.jbbmobile.view.MainScreenActivity;
import com.example.jbbmobile.view.ProfessorFragment;

import org.junit.Rule;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class ProfessorControllerTest {
    @Rule
    public final ActivityTestRule<MainScreenActivity> mainScreen;
    private Context context;
    private ProfessorController professorController;
    private ProfessorFragment professorFragment;
    private final String USER_EMAIL = "user@user.com";
    private final String USER_PASSWORD = "000000";
    private final String USER_NICKNAME = "User";
    private ExplorerDAO explorerDAO;
    private LoginController loginController;

    public ProfessorControllerTest() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Context contextTest = InstrumentationRegistry.getTargetContext();

        mainScreen = new ActivityTestRule<>(MainScreenActivity.class);
        loginController = new LoginController();
        professorController = new ProfessorController();
        Explorer explorer = new Explorer(USER_NICKNAME, USER_EMAIL, USER_PASSWORD, USER_PASSWORD);

        context = mainScreen.getActivity();
        explorerDAO = new ExplorerDAO(contextTest);
        explorerDAO.onUpgrade(explorerDAO.getWritableDatabase(), 1,1);
        explorerDAO.insertExplorer(explorer);
        loginController.realizeLogin(USER_EMAIL, USER_PASSWORD, contextTest);
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
