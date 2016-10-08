package com.example.jbbmobile.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.LoginController;
import com.example.jbbmobile.controller.MainController;
import com.example.jbbmobile.controller.PreferenceController;
import com.example.jbbmobile.model.Element;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;

import static android.R.attr.data;

public class MainScreenActivity extends FragmentActivity implements View.OnClickListener, RegisterElementFragment.OnFragmentInteractionListener {

    private ListView explorersList;
    private TextView textViewNickname;
    private LoginController loginController;
    final String PREFS_NAME = "mainScreenFirstTime";
    private ImageButton menuMoreButton;
    private ImageButton almanacButton;
    private ImageView readQrCodeButton;
    private MainController mainController;

    private RegisterElementFragment registerElementFragment;

    private static final String TAG = "MainScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        if(savedInstanceState == null) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            registerElementFragment = new RegisterElementFragment();
            fragmentTransaction.add(R.id.register_fragment, registerElementFragment, "RegisterElementFragment");
            fragmentTransaction.commit();
        }

        initViews();
        this.loginController = new LoginController();
        this.loginController.loadFile(this.getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
        textViewNickname = (TextView) findViewById(R.id.titleID);

        if (loginController.checkIfUserHasGoogleNickname()) {
            enterNickname();
        }else{
            textViewNickname.setText("");
            textViewNickname.setText("Welcome "+ loginController.getExplorer().getNickname());
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.almanacButton:
                //goToBookScreen();
                goToAlmacScreen();
                break;
            case R.id.menuMoreButton:
                goToPreferenceScreen();
                break;
            case R.id.readQrCodeButton:
                mainController = new MainController(MainScreenActivity.this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        Integer integer = new Integer(requestCode);

        if(result != null){
            if(result.getContents() == null){
                mainController.setCode(null);
            }
            else {
                Intent intent;
                int id;
                Element element;
                try {
                    element = mainController.getElementbyQRCode(result.getContents(), getContext());
                    id = element.getIdElement();
                    intent = mainController.checkIfUserHasScannedElement(result.getContents(), getContext());
                }catch(Exception e){
                    Toast.makeText(this, "QR Code inválido", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d(TAG, "Colocou visivel");
                registerElementFragment.showElement(element);
                findViewById(R.id.register_fragment).setVisibility(View.VISIBLE);
                findViewById(R.id.register_fragment).setVisibility(View.GONE);
                findViewById(R.id.register_fragment).setVisibility(View.VISIBLE);
                findViewById(R.id.register_fragment).requestLayout();
                //RegisterElementFragment registerElementFragment = (RegisterElementFragment) fragmentManager.findFragmentById(R.id.register_fragment);
                //registerElementFragment.getView().setVisibility(View.VISIBLE);
                //startActivity(intent);

                Log.d(TAG, "leitura: " + result.getContents());
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initViews(){
        this.menuMoreButton = (ImageButton)findViewById(R.id.menuMoreButton);
        this.almanacButton = (ImageButton)findViewById(R.id.almanacButton);
        this.readQrCodeButton = (ImageView)findViewById(R.id.readQrCodeButton);

        this.menuMoreButton.setOnClickListener((View.OnClickListener) this);
        this.almanacButton.setOnClickListener((View.OnClickListener) this);
        this.readQrCodeButton.setOnClickListener((View.OnClickListener) this);
    }

    private void invalidNicknameError(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("ERROR");
        alert.setMessage("Invalid nickname!");
        alert.setCancelable(false);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterNickname();
            }
        });
        alert.show();
    }

    private void enterNickname(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        alert.setTitle("NICKNAME");
        alert.setCancelable(false);
        alert.setMessage("Enter your new Nickname");
        alert.setView(input);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterNicknameOnClick(input);
            }
        });
        alert.show();
    }

    private void enterNicknameOnClick(EditText input){
        try {
            String newNickname = input.getText().toString();
            PreferenceController preferenceController = new PreferenceController();
            preferenceController.updateNickname(newNickname, loginController.getExplorer().getEmail(), MainScreenActivity.this.getApplicationContext());
            loginController.deleteFile(MainScreenActivity.this);
            loginController.loadFile(MainScreenActivity.this);
            new LoginController().realizeLogin(loginController.getExplorer().getEmail(), MainScreenActivity.this);
            MainScreenActivity.this.recreate();
        } catch (IOException e) {
            Toast.makeText(MainScreenActivity.this, "Error!", Toast.LENGTH_SHORT).show();
        }catch(IllegalArgumentException i){
            invalidNicknameError();
        }
    }


    private void goToPreferenceScreen(){
        Intent registerIntent = new Intent(MainScreenActivity.this, PreferenceScreenActivity.class);
        MainScreenActivity.this.startActivity(registerIntent);
        finish();
    }

    private void goToBookScreen(){
        Intent bookIntent = new Intent(MainScreenActivity.this, ElementScreenActivity.class);
        MainScreenActivity.this.startActivity(bookIntent);
        finish();
    }

    private void goToAlmacScreen(){
        Intent almanacIntent = new Intent(MainScreenActivity.this, AlmanacScreenActivity.class);
        MainScreenActivity.this.startActivity(almanacIntent);
        finish();
    }

    private Context getContext(){
        return this;
    }

    public void onFragmentInteraction(Uri uri) {

    }

}



