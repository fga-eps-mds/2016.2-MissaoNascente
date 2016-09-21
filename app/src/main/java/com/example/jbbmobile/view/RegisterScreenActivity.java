package com.example.jbbmobile.view;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
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
import com.example.jbbmobile.controller.BooksController;
import com.example.jbbmobile.controller.LoginController;
import com.example.jbbmobile.controller.RegisterController;

public class RegisterScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtUser;
    private EditText edtPassword;
    private EditText edtEqualsPassword;
    private EditText edtEmail;
    private Resources resources;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        initViews();


    }

    private void initViews() {
        resources = getResources();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        edtUser = (EditText) findViewById(R.id.nicknameEditText);
        edtUser.addTextChangedListener(textWatcher);
        // ********************
        edtPassword = (EditText) findViewById(R.id.passwordEditText);
        edtPassword.addTextChangedListener(textWatcher);
        // ********************
        edtEqualsPassword = (EditText) findViewById(R.id.passwordConfirmEditText);
        edtEqualsPassword.addTextChangedListener(textWatcher);
        // ********************
        edtEmail = (EditText) findViewById(R.id.emailEditText);
        edtEmail.addTextChangedListener(textWatcher);
        // ********************
        Button btnEnter = (Button) findViewById(R.id.registerButton);
        btnEnter.setOnClickListener((View.OnClickListener) this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.registerButton) {
            RegisterController registerController  = new RegisterController();
            try{
                registerController.Register(edtUser.getText().toString(), edtEmail.getText().toString(),
                        edtPassword.getText().toString(),edtEqualsPassword.getText().toString(), this.getApplicationContext());
                LoginController loginController = new LoginController();
                loginController.deleteFile(RegisterScreenActivity.this);

                new LoginController().realizeLogin(edtEmail.getText().toString(), edtPassword.getText().toString(), this.getApplicationContext());


                loginController.loadFile(this.getApplicationContext());
                new BooksController(this.getSharedPreferences( "mainScreenFirstTime", 0), this.getApplicationContext(), loginController.getExplorer() );

                Intent registerIntent = new Intent(RegisterScreenActivity.this, MainScreenActivity.class);
                RegisterScreenActivity.this.startActivity(registerIntent);
                finish();
            }catch(SQLiteConstraintException e){
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("ERROR");
                alert.setMessage("User already registered!");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RegisterScreenActivity.this.recreate();
                    }
                });
                alert.show();

            }catch (IllegalArgumentException e){
                if((e.getLocalizedMessage()).equals("wrongNickname")){
                    nicknameError();
                }
                if((e.getLocalizedMessage()).equals("wrongPassword")){
                    passwordError();
                }
                if((e.getLocalizedMessage()).equals("wrongConfirmPassword")){
                    passwordNotEquals();
                }
                if((e.getLocalizedMessage()).equals("wrongEmail")){
                    emailError();
                }


            }
        }
    }

    private void nicknameError(){
        edtUser.requestFocus();
        edtUser.setError(resources.getString(R.string.en_nickname_validation));
    }

    private void passwordError(){
        edtPassword.requestFocus();
        edtPassword.setError(resources.getString(R.string.en_password_validation));
    }

    private void passwordNotEquals(){
        edtEqualsPassword.requestFocus();
        edtEqualsPassword.setError(resources.getString(R.string.en_passwordConfirm_validation));
    }

    private void emailError(){
        edtEmail.requestFocus();
        edtEmail.setError(resources.getString(R.string.en_invalidEmail));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent startScreenIntent = new Intent(RegisterScreenActivity.this, StartScreenActivity.class);
        RegisterScreenActivity.this.startActivity(startScreenIntent);
        finish();
    }
}

