package com.example.jbbmobile.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.Login;
import com.example.jbbmobile.controller.Register;
import com.example.jbbmobile.model.Explorers;

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




        Register registerController = new Register();
        Button preferenceButton = (Button) findViewById(R.id.preferenceButton);

        preferenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainScreenActivity.this, PreferenceScreenActivity.class);
                MainScreenActivity.this.startActivity(registerIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        textViewNickname = (TextView) findViewById(R.id.titleID);
        login = new Login();
        login.loadFile(this);
        textViewNickname.setText("");
        textViewNickname.setText("Welcome, "+" "+login.getExplorer().toString());
    }
}


