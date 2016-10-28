package com.example.jbbmobile.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jbbmobile.R;

import com.example.jbbmobile.controller.BooksController;
import com.example.jbbmobile.controller.LoginController;
import com.example.jbbmobile.controller.RegisterExplorerController;
import com.example.jbbmobile.controller.StartController;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.text.ParseException;

public class StartScreenActivity extends AppCompatActivity implements View.OnClickListener {

    public static int RN_SIGN_IN = 1;
    final String PREFS_NAME = "appFirstTime";

    private Button normalSingInButton;
    private Button androidSignUpButton;
    private Button createAccountButton;

    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        try {
            new StartController(this.getSharedPreferences(PREFS_NAME, MODE_PRIVATE), this.getApplicationContext());
        }catch(ParseException e){
            Log.e("Parse", "Parse invalid");
        }

        initGoogleApi();
        initViews();
        Bundle b = getIntent().getExtras();

        /* Deleting account from google API. MVC may be unclear */
        if(b != null && b.getInt("Delete") == 25){
            deleteGoogle();
        }


        LoginController loginController = new LoginController();
        loginController.loadFile(this);         // Load the file if it exists for fill the explorer attribute

        if (loginController.remainLogin()) {   // Checking if the user has been logged without sign out
            Intent registerIntent = new Intent(StartScreenActivity.this, MainScreenActivity.class);
            StartScreenActivity.this.startActivity(registerIntent);
            finish();
        }
    }

    private void initViews(){
        this.normalSingInButton = (Button)findViewById(R.id.normalSignIn);
        this.androidSignUpButton = (Button)findViewById(R.id.googleSignIn);
        this.createAccountButton = (Button)findViewById(R.id.createAccount);

        createAccountButton.setOnClickListener((View.OnClickListener) this);
        androidSignUpButton.setOnClickListener((View.OnClickListener) this);
        normalSingInButton.setOnClickListener((View.OnClickListener) this);
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.googleSignIn:
                googleSignIn();
                break;
            case R.id.normalSignIn:
                normalSignIn();
                break;
            case R.id.createAccount:
                createAccount();
                break;
        }
    }

    private void createAccount(){
        Intent createAccountIntent = new Intent(StartScreenActivity.this, RegisterScreenActivity.class);
        StartScreenActivity.this.startActivity(createAccountIntent);
        finish();
    }

    private void normalSignIn(){
        Intent registerIntent = new Intent(StartScreenActivity.this, LoginScreenActivity.class);
        StartScreenActivity.this.startActivity(registerIntent);
        finish();
    }

    /* Google API for LoginController. MVC may be unclear */

    private void initGoogleApi(){
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(StartScreenActivity.this,R.string.noConnection, Toast.LENGTH_SHORT).show();
                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void googleSignIn(){

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent,RN_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RN_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount acct = result.getSignInAccount();
            RegisterExplorerController registerExplorerController = new RegisterExplorerController();
            registerExplorerController.register("Placeholder", acct.getEmail(), this.getApplicationContext());
            LoginController loginController = new LoginController();
            try {
                loginController.realizeLogin(acct.getEmail(), this.getApplicationContext());
            } catch (IOException e) {
                Toast.makeText(StartScreenActivity.this,R.string.impossibleConnection, Toast.LENGTH_SHORT).show();
            }

            loginController.loadFile(this.getApplicationContext());

            new BooksController(this.getSharedPreferences( "mainScreenFirstTime", 0), this.getApplicationContext());
            Intent mainScreenIntent = new Intent(StartScreenActivity.this, MainScreenActivity.class);
            StartScreenActivity.this.startActivity(mainScreenIntent);
            finish();
        }else{
            Toast.makeText(StartScreenActivity.this,R.string.noConnection, Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteGoogle(){
        mGoogleApiClient.clearDefaultAccountAndReconnect();
    }


    /* Starting Background Activities. MVC may be unclear */
}
