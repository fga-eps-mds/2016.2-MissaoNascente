package com.example.jbbmobile.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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

public class LoginScreenActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtPassword;
    private EditText edtEmail;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        initViews();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent startScreenIntent = new Intent(LoginScreenActivity.this, StartScreenActivity.class);
        LoginScreenActivity.this.startActivity(startScreenIntent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.loginButton:
                doLogin();
                break;
        }
    }

    private void initViews(){
        edtPassword = (EditText) findViewById(R.id.passwordEditText);
        edtEmail=(EditText) findViewById(R.id.emailEditText);
        this.loginButton =  (Button) findViewById(R.id.loginButton);

        this.loginButton.setOnClickListener((View.OnClickListener) this);
    }

    private void doLogin(){
        Login login = new Login();

        try {
            if (login.realizeLogin(edtEmail.getText().toString(), edtPassword.getText().toString(), LoginScreenActivity.this.getApplicationContext())) {
                Intent registerIntent = new Intent(LoginScreenActivity.this, MainScreenActivity.class);
                LoginScreenActivity.this.startActivity(registerIntent);
                finish();
            } else {
                messageLoginErro();
            }
        }catch (IllegalArgumentException e){
            messageLoginErro();
        }
    }

    private void messageLoginErro(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("ERROR");
        alert.setMessage("Email or password invalid");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        alert.show();
    }
}
