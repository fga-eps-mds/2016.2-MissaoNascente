package com.example.jbbmobile.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.Login;
import com.example.jbbmobile.controller.Preference;
import com.example.jbbmobile.controller.Register;
import com.example.jbbmobile.model.Explorers;

import java.io.IOException;
import java.util.List;

public class MainScreenActivity extends AppCompatActivity  {

    private ListView explorersList;
    private TextView textViewNickname;
    private Login login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        final Context contextMainScreen = getApplicationContext();





        Button preferenceButton = (Button) findViewById(R.id.preferenceButton);

        preferenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainScreenActivity.this, PreferenceScreenActivity.class);
                MainScreenActivity.this.startActivity(registerIntent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        textViewNickname = (TextView) findViewById(R.id.titleID);
        login = new Login();
        login.loadFile(this);
        if(login.getExplorer().getNickname().equals("Placeholder")){
            Log.i("Teste testoso:", login.getExplorer().getNickname());
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            final EditText input = new EditText(this);
            alert.setTitle("NICKNAME");
            alert.setCancelable(false);
            alert.setMessage("Enter your new Nickname");
            alert.setView(input);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String newNickname = input.getText().toString();
                    Preference preferenceController = new Preference();
                    preferenceController.updateNickname(newNickname, login.getExplorer().getEmail(), MainScreenActivity.this.getApplicationContext());
                    login.deleteFile(MainScreenActivity.this);
                    try {
                        new Login().realizeLogin(login.getExplorer().getEmail(), MainScreenActivity.this);
                    } catch (IOException e) {
                        Toast.makeText(MainScreenActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                    MainScreenActivity.this.recreate();
                }
            });
            alert.show();
        }else{

            textViewNickname.setText("");
            textViewNickname.setText("Welcome "+login.getExplorer().toString());
        }


    }
}


