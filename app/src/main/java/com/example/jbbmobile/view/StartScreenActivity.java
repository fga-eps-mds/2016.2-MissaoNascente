package com.example.jbbmobile.view;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jbbmobile.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class StartScreenActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout normalSingUpRelativeLayout;
    private RelativeLayout androidSignUpRelativeLayout;
    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    public static int RN_SIGN_IN = 1;
    private TextView mStatusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        initViews();
        initGoogleApi();


        normalSingUpRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(StartScreenActivity.this, RegisterScreenActivity.class);
                StartScreenActivity.this.startActivity(registerIntent);

                finish();

            }
        });

        RelativeLayout normalSingInRelativeLayout = (RelativeLayout) findViewById(R.id.normalSingInRelativeLayout);

        normalSingInRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(StartScreenActivity.this, LoginScreenActivity.class);
                StartScreenActivity.this.startActivity(registerIntent);


            }
        });
    }

    private void initViews(){
        this.normalSingUpRelativeLayout = (RelativeLayout)findViewById(R.id.normalSingUpRelativeLayout);
        this.androidSignUpRelativeLayout = (RelativeLayout)findViewById(R.id.androidRelativeLayout);
        this.mStatusTextView = (TextView)findViewById((R.id.mStatusTextView));
        androidSignUpRelativeLayout.setOnClickListener((View.OnClickListener) this);

    }

    public void onClick(View v){

        switch(v.getId()){
            case R.id.androidRelativeLayout:
                signIn();
                break;
        }
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



    private void signIn(){
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
            Toast.makeText(StartScreenActivity.this, "Connected!", Toast.LENGTH_SHORT).show();
            mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
        }
    }
}
