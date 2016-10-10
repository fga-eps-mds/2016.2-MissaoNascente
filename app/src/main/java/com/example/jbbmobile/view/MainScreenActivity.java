package com.example.jbbmobile.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;

import static android.R.attr.data;

public class MainScreenActivity extends AppCompatActivity  implements View.OnClickListener{

    private ListView explorersList;
    private TextView textViewNickname;
    private LoginController loginController;
    final String PREFS_NAME = "mainScreenFirstTime";
    private ImageButton menuMoreButton;
    private ImageButton almanacButton;
    private ImageView readQrCodeButton;
    private MainController mainController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

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

        if(result != null){
            if(result.getContents() == null){
                mainController.setCode(null);
            }
            else {
                mainController.setCode(result.getContents());
                Toast.makeText(this, "leitura: " + result.getContents(), Toast.LENGTH_SHORT).show();
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

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
}