package com.example.jbbmobile.view;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;


import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.LoginController;
import com.example.jbbmobile.model.Explorer;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class LoginScreenActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtPassword;
    private EditText edtEmail;

    private Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        initViews();
        Bundle b = getIntent().getExtras();

        if(b != null && b.getInt("wrongPassword") == 35) {
            messageLoginErro();
        }
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
        LoginController loginController = new LoginController();

        try {
            loginController.doLogin(edtEmail.getText().toString().toLowerCase(), edtPassword.getText().toString(), LoginScreenActivity.this.getApplicationContext());
            Intent progressBarIntent = new Intent(LoginScreenActivity.this, LoginProgressBar.class);
            LoginScreenActivity.this.startActivity(progressBarIntent);
        }catch (IllegalArgumentException e){
            messageLoginErro();
        }
    }
// Half-working method from US04.
    /*private void doLogin(){
        LoginController loginController = new LoginController();

        try {
            if (loginController.doLogin(edtEmail.getText().toString().toLowerCase(), edtPassword.getText().toString(), LoginScreenActivity.this.getApplicationContext())) {
                Intent registerIntent = new Intent(LoginScreenActivity.this, MainScreenActivity.class);
                LoginScreenActivity.this.startActivity(registerIntent);
                finish();
            } else {
                messageLoginErro();

            }
        }catch (IllegalArgumentException e){
            messageLoginErro();
        }
    }*/




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
