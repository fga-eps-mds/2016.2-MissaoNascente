package gov.jbb.missaonascente.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import gov.jbb.missaonascente.R;
import gov.jbb.missaonascente.controller.LoginController;
import gov.jbb.missaonascente.controller.RegisterElementController;
import gov.jbb.missaonascente.model.Element;

import java.io.File;

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
    private TextView scoreText;
    private TextView energyText;
    private RegisterElementController registerElementController;
    private int ANIMATION_TIME_SCORE = 2000;
    private int ANIMATION_OFFSET_SCORE = 3000;
    private int ANIMATION_TIME_ENERGY = 2000;
    private int ANIMATION_OFFSET_ENERGY = 3000;
    File photo;


    public RegisterElementFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register_element, container, false);

        closeButton = (ImageButton) view.findViewById(R.id.close_button);
        closeButton.setOnClickListener(onCloseButtonClick());

        cameraButton = (ImageButton) view.findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(onCameraButtonClick());

        showElementButton = (ImageButton) view.findViewById(R.id.show_element_button);
        showElementButton.setOnClickListener(onShowElementButtonClick());

        elementImage = (ImageView) view.findViewById(R.id.element_image);
        nameText = (TextView) view.findViewById(R.id.name_text);
        scoreText = (TextView) view.findViewById(R.id.scoreText);
        energyText = (TextView) view.findViewById(R.id.energyText);

        return view;
    }

    public void showElement(Element element, boolean showScoreInFirstRegister){
        registerElementController.setElement(element);

        String imagePath = registerElementController.findImagePathByAssociation();

        if(imagePath.equals(EMPTY_STRING)){
            String path = element.getDefaultImage();
            int resID = getResources().getIdentifier(path, "drawable", getActivity().getPackageName());
            elementImage.setImageResource(resID);
            Log.i("----------",resID+"--------------------------------");

        }else{
            Bitmap bitmap = RegisterElementController.loadImageFromStorage(imagePath, getContext());
            elementImage.setImageBitmap(bitmap);
        }

        nameText.setText(element.getNameElement());

        if (showScoreInFirstRegister){
            scoreText.setText("+" + Integer.toString(element.getElementScore()));
            animationForScore();
        }
    }

    private void animationForScore(){
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new AccelerateInterpolator());
        fadeIn.setDuration(ANIMATION_TIME_SCORE);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateDecelerateInterpolator());
        fadeOut.setStartOffset(ANIMATION_OFFSET_SCORE);
        fadeOut.setDuration(ANIMATION_TIME_SCORE);

        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        animation.setRepeatCount(1);
        scoreText.setAnimation(animation);

    }

    private View.OnClickListener onCloseButtonClick () {
       return new ImageButton.OnClickListener() {
           @Override
           public void onClick(View v) {
               removeFragment();
           }
       };
    }

    private View.OnClickListener onShowElementButtonClick () {
        return new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ElementScreenActivity.class);
                intent.putExtra(getString(R.string.idElement), registerElementController.getElement().getIdElement());

                startActivity(intent);

                removeFragment();
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
        Log.d("ResultPhoto", "resultPhoto");
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bitmap image = null;
            try {
                image = registerElementController.updateElementImage(getActivity(), photo.getAbsolutePath());
                photo.delete();
                elementImage.setImageBitmap(image);
            } catch (Exception e) {
                photo.delete();
                Toast.makeText(getActivity(), R.string.try_the_picture_later, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Log.d("Vamos tirar foto", "foto");
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        try {
            File storageDirectory = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            photo = registerElementController.createTemporaryFile("picture", ".jpg", storageDirectory);
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), R.string.try_photo_later, Toast.LENGTH_SHORT).show();
            return;
        }

        registerElementController.setCurrentPhotoPath(photo.getAbsolutePath());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    public RegisterElementController getController() {
        return registerElementController;
    }

    public void createRegisterElementController(LoginController loginController){
        registerElementController = new RegisterElementController(loginController);
    }

    private void removeFragment(){
        getActivity().findViewById(R.id.readQrCodeButton).setVisibility(View.VISIBLE);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.remove(this).commitNow();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        showElementButton.setImageDrawable(null);
    }
}