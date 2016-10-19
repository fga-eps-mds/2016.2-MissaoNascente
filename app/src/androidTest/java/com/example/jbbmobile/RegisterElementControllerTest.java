package com.example.jbbmobile;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.test.rule.ActivityTestRule;

import com.example.jbbmobile.controller.ElementsController;
import com.example.jbbmobile.controller.LoginController;
import com.example.jbbmobile.controller.RegisterElementController;
import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.model.Element;
import com.example.jbbmobile.view.MainScreenActivity;

import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegisterElementControllerTest {
    @Rule
    public final ActivityTestRule<MainScreenActivity> mainScreen;

    private Context context;
    private RegisterElementController registerElementController;
    private Element element;
    private LoginController loginController;

    public RegisterElementControllerTest(){
        mainScreen = new ActivityTestRule<>(MainScreenActivity.class);
        mainScreen.launchActivity(new Intent());
        context = mainScreen.getActivity();
        registerElementController = new RegisterElementController(loginController);

        ElementDAO elementDAO = new ElementDAO(context);
        element = elementDAO.findElementFromElementTable(1);

        registerElementController.setElement(element);
    }

    @Test
    public void testIfValidStorageDirectoryCreatesImage() throws IOException{
        File storageDirectory = mainScreen.getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        registerElementController.getElement().setUserImage("");
        File photoFile = registerElementController.createImageFile(storageDirectory);
        assertTrue(photoFile.getAbsolutePath().contains("USER_ELEMENT_ID_"));
    }

    @Test
    public void testIfPhotoAlreadyTakenReceivesSamePath() throws IOException{
        registerElementController.setCurrentPhotoPath("/image.jpg");
        File storageDirectory = mainScreen.getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photoFile = registerElementController.createImageFile(storageDirectory);

        assertEquals(photoFile.getAbsolutePath(), "/image.jpg");
    }

    @Test
    public void testIfImagePathIsUpdated() throws Exception {
        registerElementController.setCurrentPhotoPath("/new_image.jpg");
        registerElementController.updateElementImage();

        assertEquals(registerElementController.getCurrentPhotoPath(), "/new_image.jpg");
    }
}

















