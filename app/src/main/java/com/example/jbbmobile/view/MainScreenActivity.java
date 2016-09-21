package com.example.jbbmobile.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.Book;
import com.example.jbbmobile.controller.Login;
import com.example.jbbmobile.controller.Preference;
import java.io.IOException;

public class MainScreenActivity extends AppCompatActivity  implements View.OnClickListener{

    private ListView explorersList;
    private TextView textViewNickname;
    private Login login;
    final String PREFS_NAME = "mainScreenFirstTime";
    private ImageButton menuMoreButton;
    private ImageButton almanacButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        initViews();
        this.login = new Login();
        this.login.loadFile(this.getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
        textViewNickname = (TextView) findViewById(R.id.titleID);

        if (login.checkIfUserHasGoogleNickname()) {
            enterNickname();
        }else{
            textViewNickname.setText("");
            textViewNickname.setText("Welcome "+login.getExplorer().getNickname());
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
        }
    }


    private void initViews(){
        this.menuMoreButton = (ImageButton)findViewById(R.id.menuMoreButton);
        this.almanacButton = (ImageButton)findViewById(R.id.almanacButton);

        this.menuMoreButton.setOnClickListener((View.OnClickListener) this);
        this.almanacButton.setOnClickListener((View.OnClickListener) this);
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
            Preference preferenceController = new Preference();
            preferenceController.updateNickname(newNickname, login.getExplorer().getEmail(), MainScreenActivity.this.getApplicationContext());
            login.deleteFile(MainScreenActivity.this);
            login.loadFile(MainScreenActivity.this);
            new Login().realizeLogin(login.getExplorer().getEmail(), MainScreenActivity.this);
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

