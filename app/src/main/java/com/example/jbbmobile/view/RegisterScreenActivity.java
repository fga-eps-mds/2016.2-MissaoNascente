package com.example.jbbmobile.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;


import android.content.Intent;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.Register;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtUser;
    private EditText edtPassword;
    private EditText edtEqualsPassword;
    private EditText edtEmail;
    private Resources resources;
    public static Context registerScreenContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        initViews();
        this.registerScreenContext = getApplicationContext();


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


                Register registerController  = new Register(edtUser.getText().toString(), edtEmail.getText().toString(),
                        edtPassword.getText().toString(),edtEqualsPassword.getText().toString(), registerScreenContext);

                Intent registerIntent = new Intent(RegisterScreenActivity.this, MainScreenActivity.class);
                RegisterScreenActivity.this.startActivity(registerIntent);
                finish();

        }
    }



}
