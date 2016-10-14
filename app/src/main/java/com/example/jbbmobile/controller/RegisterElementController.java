package com.example.jbbmobile.controller;

import android.content.Context;
import android.util.Log;

import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.model.Element;

import java.io.File;
import java.io.IOException;

public class RegisterElementController {
    private String currentPhotoPath;
    private Element element;

    private static final String EMPTY_STRING = "";

    private final String TAG = "RegisterElement";

    public File createImageFile(File storageDirectory) throws IOException {
        File image;

        if(element.getUserImage().equals(EMPTY_STRING)){
            String imageFileName = "USER_ELEMENT_ID_" + Integer.toString(element.getIdElement());
            image = File.createTempFile(imageFileName, ".jpg", storageDirectory);

            Log.d(TAG, "[" + imageFileName+ "]" + "[" + Integer.toString(element.getIdElement()) + "]");
        }else{
            image = new File(element.getUserImage());
        }

        currentPhotoPath = image.getAbsolutePath();

        Log.d(TAG, "[" + currentPhotoPath + "]");

        return image;
    }

    public String getCurrentPhotoPath() {
        return currentPhotoPath;
    }

    public void setCurrentPhotoPath(String currentPhotoPath) {
        this.currentPhotoPath = currentPhotoPath;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public void updateElementImage(Context context) {
        element.setUserImage(currentPhotoPath);

        ElementDAO elementDAO = new ElementDAO(context);
        elementDAO.updateElement(element);

        Log.d(TAG, "[" + elementDAO.findElementFromElementTable(element.getIdElement()).getUserImage() + "]" );
    }
}