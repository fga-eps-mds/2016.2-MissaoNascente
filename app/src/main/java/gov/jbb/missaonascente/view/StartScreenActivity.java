package gov.jbb.missaonascente.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import gov.jbb.missaonascente.R;
import gov.jbb.missaonascente.controller.LoginController;
import gov.jbb.missaonascente.controller.StartController;

import java.text.ParseException;

public class StartScreenActivity extends AppCompatActivity implements View.OnClickListener {
    final String PREFS_NAME = "appFirstTime";

    private Button normalSingInButton;
    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        try {
            new StartController(this.getSharedPreferences(PREFS_NAME, MODE_PRIVATE), this.getApplicationContext());
        }catch(ParseException e){
            Log.e("Parse", "Parse invalid");
        }

        initViews();

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
        this.createAccountButton = (Button)findViewById(R.id.createAccount);

        createAccountButton.setOnClickListener(this);
        normalSingInButton.setOnClickListener(this);
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.normalSignIn:
                normalSignIn();
                break;
            case R.id.createAccount:
                createAccount();
                break;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
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
}
