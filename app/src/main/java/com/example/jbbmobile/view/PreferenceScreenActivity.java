package com.example.jbbmobile.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.Login;
import com.example.jbbmobile.controller.Preference;



public class PreferenceScreenActivity extends AppCompatActivity implements View.OnClickListener{

    private RelativeLayout editNickname;
    private RelativeLayout deleteAccount;
    private TextView nicknameShow;
    private TextView emailShow;
    private Login login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_screen);
        initViews();

        this.login = new Login();
        this.login.loadFile(this);

        this.nicknameShow.setText("Nickname: "+ login.getExplorer().getNickname());
        this.emailShow.setText("Email: "+ login.getExplorer().getEmail());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editNicknameButton:
                editAccount();
                break;
            case R.id.deleteAccount:
                deleteAccount();
                break;
        }
    }

    private void initViews(){
        this.editNickname = (RelativeLayout)findViewById(R.id.editNicknameButton);
        this.deleteAccount = (RelativeLayout)findViewById(R.id.deleteAccount);
        this.nicknameShow = (TextView) findViewById(R.id.nicknameShow);
        this.emailShow = (TextView)findViewById(R.id.emailShow);
        this.editNickname.setOnClickListener((View.OnClickListener) this);
        this.deleteAccount.setOnClickListener((View.OnClickListener) this);


    }

    private void deleteAccount() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        alert.setTitle("Delete Account");
        alert.setMessage("Enter your password");
        alert.setView(input);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Preference preferenceController = new Preference();
                preferenceController.deleteExplorer(input.getText().toString(), login.getExplorer().getEmail(), PreferenceScreenActivity.this.getApplicationContext());
                login.deleteFile(PreferenceScreenActivity.this);
                Intent startScreenIntet = new Intent(PreferenceScreenActivity.this, StartScreenActivity.class);
                PreferenceScreenActivity.this.startActivity(startScreenIntet);
                finish();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.show();
    }

    private void editAccount(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        alert.setTitle("NICKNAME");
        alert.setMessage("Enter your new Nickname");
        alert.setView(input);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newNickname = input.getText().toString();
                Preference preferenceController = new Preference();
                preferenceController.updateNickname(newNickname, login.getExplorer().getEmail(), PreferenceScreenActivity.this.getApplicationContext());
                login.deleteFile(PreferenceScreenActivity.this);
                new Login().realizeLogin(login.getExplorer().getEmail(), login.getExplorer().getPassword(), PreferenceScreenActivity.this.getApplicationContext());
                PreferenceScreenActivity.this.recreate();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.show();


    }

}
