package com.example.jbbmobile.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.LoginController;
import com.example.jbbmobile.controller.PreferenceController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLDataException;


public class PreferenceScreenActivity extends AppCompatActivity implements View.OnClickListener{

    private RelativeLayout editNickname;
    private RelativeLayout deleteAccount;
    private TextView nicknameShow;
    private TextView emailShow;
    private LoginController loginController;
    private final int DELETE = 25;
    private RelativeLayout signOut;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_screen);

        context = this;

        initViews();
        this.loginController = new LoginController();
        try {
            this.loginController.loadFile(this.getApplicationContext());
        } catch (SQLDataException e) {
            e.printStackTrace();
        }
        this.nicknameShow.setText("Nickname: "+ loginController.getExplorer().getNickname());
        this.emailShow.setText("Email: "+ loginController.getExplorer().getEmail());
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
            case R.id.signOutButton:
                signOut();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mainScreenIntent = new Intent(PreferenceScreenActivity.this, MainScreenActivity.class);
        PreferenceScreenActivity.this.startActivity(mainScreenIntent);
        finish();
    }

    private void initViews(){
        this.editNickname = (RelativeLayout)findViewById(R.id.editNicknameButton);
        this.deleteAccount = (RelativeLayout)findViewById(R.id.deleteAccount);
        this.nicknameShow = (TextView) findViewById(R.id.nicknameShow);
        this.emailShow = (TextView)findViewById(R.id.emailShow);
        this.editNickname.setOnClickListener((View.OnClickListener) this);
        this.deleteAccount.setOnClickListener((View.OnClickListener) this);
        this.signOut = (RelativeLayout)findViewById(R.id.signOutButton);
        this.signOut.setOnClickListener((View.OnClickListener) this);

    }

    private void deleteAccount() {
        try{
            loginController.checkIfGoogleHasGooglePassword();
            normalDelete();
        }catch(NullPointerException i){
            googleDelete();
        }
    }


    private void signOut() {
        loginController.deleteFile(this);
        loginController.deleteUser(this);
        getSharedPreferences("mainScreenFirstTime",0).edit().putBoolean("mainScreenFirstTime",true).commit();
        Intent startScreenIntet = new Intent(PreferenceScreenActivity.this, StartScreenActivity.class);
        PreferenceScreenActivity.this.startActivity(startScreenIntet);
        finish();
    }


    private void normalDelete(){
        System.out.println("Normal ++++++++++++++++");
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input.setSelection(input.getText().length());
        alert.setTitle("Delete Account");
        alert.setMessage("Enter your password");
        alert.setView(input);
        Log.i ("INPUTTT", input.getText().toString());

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    Log.i ("INPUTTT", input.getText().toString());
                    PreferenceController preferenceController = new PreferenceController();
                    preferenceController.deleteExplorer(input.getText().toString(), loginController.getExplorer().getEmail(), PreferenceScreenActivity.this.getApplicationContext());
                    loginController.deleteFile(PreferenceScreenActivity.this);
                    getSharedPreferences("mainScreenFirstTime",0).edit().putBoolean("mainScreenFirstTime",true).commit();
                    Intent startScreenIntet = new Intent(PreferenceScreenActivity.this, StartScreenActivity.class);
                    PreferenceScreenActivity.this.startActivity(startScreenIntet);
                    finish();
                }catch(IllegalArgumentException i){
                    if(i.getLocalizedMessage().equals("confirmPassword")){
                        passwordWrongError();
                    }else{
                        passwordError();
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.show();
    }

    /* Deleting account from google API. MVC may be unclear */
    private void googleDelete(){
        System.out.println("GOOGLE ++++++++++++++++");
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete Account");
        alert.setMessage("Are you sure?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PreferenceController preferenceController = new PreferenceController();
                preferenceController.deleteExplorer(loginController.getExplorer().getEmail(), PreferenceScreenActivity.this.getApplicationContext());
                loginController.deleteFile(PreferenceScreenActivity.this);
                Intent startScreenIntet = new Intent(PreferenceScreenActivity.this, StartScreenActivity.class);
                Bundle b = new Bundle();
                b.putInt("Delete", DELETE);
                getSharedPreferences("mainScreenFirstTime",0).edit().putBoolean("mainScreenFirstTime",true).commit();
                getIntent().putExtras(b);
                PreferenceScreenActivity.this.startActivity(startScreenIntet);
                finish();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
        input.setMaxLines(1);
        alert.setView(input);
        input.setInputType(96);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newNickname = input.getText().toString();
                PreferenceController preferenceController = new PreferenceController();

                try{
                    preferenceController.updateNickname(newNickname, loginController.getExplorer().getEmail(), PreferenceScreenActivity.this.getApplicationContext());
                    loginController.deleteFile(PreferenceScreenActivity.this);
                    new LoginController().realizeLogin(loginController.getExplorer().getEmail(), PreferenceScreenActivity.this.getApplicationContext());
                    PreferenceScreenActivity.this.recreate();

                } catch(IllegalArgumentException i){
                    invalidNicknameError();
                } catch(SQLiteConstraintException e){
                    existentNickname();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.show();
    }

    private void existentNickname(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("ERROR");
        alert.setMessage("This nickname already exists!");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }

    private void passwordError(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("ERROR");
        alert.setMessage("Invalid password!");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }

    private void passwordWrongError(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("ERROR");
        alert.setMessage("Wrong password!");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }

    private void invalidNicknameError(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("ERROR");
        alert.setMessage("Invalid nickname!");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }
}
