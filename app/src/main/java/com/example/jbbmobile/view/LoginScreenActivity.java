package com.example.jbbmobile.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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

public class LoginScreenActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtPassword;
    private EditText edtEmail;
    protected ProgressDialog progressDialog;
    protected LoginController loginController;
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
        Button loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(this);
    }

    private void doLogin(){
        loginController = new LoginController();

        try {
            loginController.doLogin(edtEmail.getText().toString().toLowerCase(),
                    edtPassword.getText().toString(),
                    LoginScreenActivity.this);

            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("LOADING");
            progressDialog.show();

            LoginWebService loginWebService = new LoginWebService();
            loginWebService.execute();

        }catch (IllegalArgumentException e){
            messageLoginError();
        }
    }

    private void messageLoginError(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("ERROR");
        alert.setMessage("Email or password invalid");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        alert.show();
    }

    private class LoginWebService extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            Looper.prepare();

            while(!loginController.isAction());

            if(loginController.isResponse()){
                progressDialog.dismiss();
                Intent registerIntent = new Intent(LoginScreenActivity.this, MainScreenActivity.class);
                LoginScreenActivity.this.startActivity(registerIntent);
                finish();
            }else{
                progressDialog.dismiss();
                messageLoginError();

                Looper.loop();
            }

            return null;
        }
    }
}
