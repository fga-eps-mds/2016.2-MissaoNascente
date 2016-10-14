package com.example.jbbmobile.controller;

import android.util.Log;

import java.io.File;
import java.io.IOException;

public class RegisterElementController {
    private String currentPhotoPath;

    public File createImageFile(File storageDirectory, int idElement) throws IOException {
        String imageFileName = "ELEMENT_ID_" + Integer.toString(idElement);
        File image = File.createTempFile(imageFileName, ".j pg", storageDirectory);

        currentPhotoPath = "file:" + image.getAbsolutePath();

        if (image == null)
            Log.d("It's NULL","NULL");

        return image;
    }

    public String getCurrentPhotoPath() {
        return currentPhotoPath;
    }

    public void setCurrentPhotoPath(String currentPhotoPath) {
        this.currentPhotoPath = currentPhotoPath;
    }
}