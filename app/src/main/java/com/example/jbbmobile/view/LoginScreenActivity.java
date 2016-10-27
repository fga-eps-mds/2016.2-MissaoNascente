package com.example.jbbmobile.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.LoginController;
import com.example.jbbmobile.controller.MainController;

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
    protected void onPause() {
        super.onPause();
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(progressDialog != null){
            progressDialog.dismiss();
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
        Button loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(this);
    }

    private void doLogin(){
        loginController = new LoginController();

        try {
            if(new MainController().checkIfUserHasInternet(this)){

                loginController.deleteFile(this);
                loginController.doLogin(edtEmail.getText().toString().toLowerCase(),
                        edtPassword.getText().toString(),
                        LoginScreenActivity.this);

                progressDialog = new ProgressDialog(this){
                    @Override
                    public void onBackPressed() {
                        dismiss();
                    }
                };
                progressDialog.setTitle(R.string.loading);
                if(progressDialog.isShowing()){
                   progressDialog.dismiss();
                }

                progressDialog.show();


                new LoginWebService().execute();
            }else{
                connectionError();
            }

        }catch (IllegalArgumentException e){
            messageLoginError();
        }
    }

    private void messageLoginError(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.errorMessage);
        alert.setMessage(R.string.emailOrPasswordWrong);
        alert.setPositiveButton(R.string.OKMessage, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginScreenActivity.this.recreate();
            }
        });
        alert.show();
    }

    private void connectionError(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.OKMessage);
        alert.setMessage(R.string.noInternetConnection);
        alert.setPositiveButton(R.string.OKMessage, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginScreenActivity.this.recreate();
            }
        });
        alert.show();
    }

    private class LoginWebService extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Void... params) {
            if(Looper.myLooper() == null){
                Looper.prepare();
            }
            while(!loginController.isAction());
            return loginController.isResponse();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                Intent registerIntent = new Intent(LoginScreenActivity.this, MainScreenActivity.class);
                LoginScreenActivity.this.startActivity(registerIntent);
                finish();
            }else{
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                messageLoginError();
            }
        }
    }
}
