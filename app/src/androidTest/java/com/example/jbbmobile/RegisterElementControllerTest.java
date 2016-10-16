package com.example.jbbmobile;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import com.example.jbbmobile.controller.ElementsController;
import com.example.jbbmobile.controller.RegisterElementController;
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

    public RegisterElementControllerTest(){
        mainScreen = new ActivityTestRule<>(MainScreenActivity.class);
        mainScreen.launchActivity(new Intent());
        context = mainScreen.getActivity();
        registerElementController = new RegisterElementController();

        ElementsController elementsController = new ElementsController();

        try {
            element = elementsController.findElementByQrCode(1, context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        registerElementController.setElement(element);
    }

    @Test
    public void testIfValidStorageDirectoryCreatesImage() throws IOException{
        File storageDirectory = mainScreen.getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photoFile = registerElementController.createImageFile(storageDirectory);
        assertTrue(photoFile.getAbsolutePath().contains("USER_ELEMENT_ID_"));
    }

    @Test(expected = IOException.class)
    public void testIfInvalidStorageDirectoryThrowsIOException() throws IOException{
        File storageDirectory = null;
        File photoFile = registerElementController.createImageFile(storageDirectory);
    }

    @Test
    public void testIfPhotoAlreadyTakenReceivesSamePath() throws IOException{
        registerElementController.getElement().setUserImage("/image.jpg");
        File storageDirectory = mainScreen.getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photoFile = registerElementController.createImageFile(storageDirectory);

        assertEquals(photoFile.getAbsolutePath(), "/image.jpg");
    }

    @Test
    public void testIfImagePathIsUpdated() throws Exception {
        registerElementController.setCurrentPhotoPath("/new_image.jpg");
        registerElementController.updateElementImage(context);

        assertEquals(registerElementController.getElement().getUserImage(), "/new_image.jpg");
    }
}

















