package com.example.jbbmobile.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;


import android.content.Intent;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.Login;

public class LoginScreenActivity extends AppCompatActivity {

    private EditText edtPassword;
    private EditText edtEmail;
    private Resources resources;
    private  Context loginScreenContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        this.loginScreenContext = getApplicationContext();


        Button loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edtPassword = (EditText) findViewById(R.id.passwordEditText);
                edtEmail=(EditText) findViewById(R.id.emailEditText);
                Login login = new Login(edtEmail.getText().toString(),edtPassword.getText().toString(),loginScreenContext);

                Intent registerIntent = new Intent(LoginScreenActivity.this, MainScreenActivity.class);
                LoginScreenActivity.this.startActivity(registerIntent);

            }
        });
    }
}
