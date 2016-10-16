package com.example.jbbmobile.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.RegisterElementController;
import com.example.jbbmobile.model.Element;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class RegisterElementFragment extends Fragment {
    private static final String TAG = "ElementFragment";
    private static final int REQUEST_TAKE_PHOTO = 1;
    private final String EMPTY_STRING = "";

    private View view;
    private TextView nameText;
    private ImageButton closeButton;
    private ImageButton showElementButton;
    private ImageButton cameraButton;
    private ImageView elementImage;
    private RegisterElementController registerElementController = new RegisterElementController();

    public RegisterElementFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        view = inflater.inflate(R.layout.fragment_register_element, container, false);

        closeButton = (ImageButton) view.findViewById(R.id.close_button);
        closeButton.setOnClickListener(onCloseButtonClick());

        cameraButton = (ImageButton) view.findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(onCameraButtonClick());

        showElementButton = (ImageButton) view.findViewById(R.id.show_element_button);
        showElementButton.setOnClickListener(onShowElementButtonClick());

        elementImage = (ImageView) view.findViewById(R.id.element_image);
        nameText = (TextView) view.findViewById(R.id.name_text);

        return view;
    }

    public void showElement(Element element){
        registerElementController.setElement(element);

        Log.d(TAG, "Element: " + element.getUserImage() + " " + element.getIdElement());

        if(registerElementController.getCurrentPhotoPath().equals(EMPTY_STRING)){
            String imagePath = element.getDefaultImage();
            int resID = getResources().getIdentifier(imagePath, "drawable", getActivity().getPackageName());
            elementImage.setImageResource(resID);
        }else{
            elementImage.setImageURI(Uri.parse(registerElementController.getCurrentPhotoPath()));
        }


        nameText.setText(element.getNameElement());
    }

    private View.OnClickListener onCloseButtonClick () {
       return new ImageButton.OnClickListener() {
           @Override
           public void onClick(View v) {
               getActivity().findViewById(R.id.register_fragment).setVisibility(View.GONE);
               getActivity().findViewById(R.id.readQrCodeButton).setVisibility(View.VISIBLE);
           }
       };
    }

    private View.OnClickListener onShowElementButtonClick () {
        return new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ElementScreenActivity.class);
                intent.putExtra(getString(R.string.id_element), registerElementController.getElement().getIdElement());
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener onCameraButtonClick () {
        return new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            registerElementController.updateElementImage();
            elementImage.setImageURI(null);
            elementImage.setImageURI(Uri.parse(registerElementController.getCurrentPhotoPath()));
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            File storageDirectory = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            try {
                photoFile = registerElementController.createImageFile(storageDirectory);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (photoFile != null) {
                Uri photoURI = Uri.fromFile(photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public RegisterElementController getController() {
        return registerElementController;
    }
}