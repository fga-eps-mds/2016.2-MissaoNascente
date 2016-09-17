package com.example.jbbmobile.view;



import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jbbmobile.R;

import com.example.jbbmobile.controller.Book;
import com.example.jbbmobile.controller.Element;
import com.example.jbbmobile.controller.Login;
import com.example.jbbmobile.controller.Register;
import com.example.jbbmobile.controller.Start;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;

public class StartScreenActivity extends AppCompatActivity implements View.OnClickListener {


    private RelativeLayout normalSingInRelativeLayout;
    private RelativeLayout androidSignUpRelativeLayout;
    private RelativeLayout createAccountRelativeLayout;
    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    public static int RN_SIGN_IN = 1;
    private TextView mStatusTextView;
    final String PREFS_NAME = "appFirstTime";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        new Start(this.getSharedPreferences(PREFS_NAME, 0), this.getApplicationContext());

        initGoogleApi();
        initViews();
        Bundle b = getIntent().getExtras();

        /* Deleting account from google API. MVC may be unclear */
        if(b != null && b.getInt("Delete") == 25){
            deleteGoogle();
        }


        Login login = new Login();
        login.loadFile(this);         // Load the file if it exists for fill the explorer attribute
        if (login.remainLogin()) {   // Checking if the user has been logged without sign out
            Intent registerIntent = new Intent(StartScreenActivity.this, MainScreenActivity.class);
            StartScreenActivity.this.startActivity(registerIntent);
            finish();
        }
    }

    private void initViews(){
        this.normalSingInRelativeLayout = (RelativeLayout)findViewById(R.id.normalSignInRelativeLayout);
        this.androidSignUpRelativeLayout = (RelativeLayout)findViewById(R.id.androidRelativeLayout);
        this.mStatusTextView = (TextView)findViewById((R.id.mStatusTextView));
        this.createAccountRelativeLayout = (RelativeLayout)findViewById(R.id.createAccountRelativeLayout);

        createAccountRelativeLayout.setOnClickListener((View.OnClickListener) this);
        androidSignUpRelativeLayout.setOnClickListener((View.OnClickListener) this);
        normalSingInRelativeLayout.setOnClickListener((View.OnClickListener) this);
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.androidRelativeLayout:
                googleSignIn();
                break;
            case R.id.normalSignInRelativeLayout:
                normalSignIn();
                break;
            case R.id.createAccountRelativeLayout:
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

    /* Google API for Login. MVC may be unclear */



    private void initGoogleApi(){
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(StartScreenActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
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
            Register register = new Register();
            register.Register("Placeholder", acct.getEmail(), this.getApplicationContext());
            Login login = new Login();
            try {
                login.realizeLogin(acct.getEmail(), this.getApplicationContext());
            } catch (IOException e) {
                Toast.makeText(StartScreenActivity.this, "Impossible to connect", Toast.LENGTH_SHORT).show();
            }

            Intent mainScreenIntent = new Intent(StartScreenActivity.this, MainScreenActivity.class);
            StartScreenActivity.this.startActivity(mainScreenIntent);
            finish();
        }
    }

    private void deleteGoogle(){
        mGoogleApiClient.clearDefaultAccountAndReconnect();
    }


    /* Starting Background Activities. MVC may be unclear */

}
