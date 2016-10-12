package com.example.jbbmobile.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.LoginController;
import com.example.jbbmobile.controller.RegisterController;

import java.sql.SQLDataException;

public class LoginProgressBar extends AppCompatActivity {

    private static final int TIMER_RUNTIME = 10000;
    private boolean barActive;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_progress_bar);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);

        final Thread timerThread = new Thread(){
            @Override
            public void run() {
                barActive = true;
                int waited = 0;
                try {
                    while (barActive && (waited < TIMER_RUNTIME)) {
                        sleep(200);
                        if(barActive){
                            waited += 200;
                            updateProgressBar(waited);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    onContinue();
                }
            }
        };

        timerThread.start();
    }

    private void updateProgressBar(int waited){
        if(progressBar != null){
            final int progress = progressBar.getMax() * waited / TIMER_RUNTIME;
            progressBar.setProgress(progress);
        }
    }

    private void onContinue(){
        try {
            new LoginController().loadFile(this);
            new RegisterController().checkIfHasError(this);
            Intent registerIntent = new Intent(LoginProgressBar.this, MainScreenActivity.class);
            LoginProgressBar.this.startActivity(registerIntent);
            finish();
        }catch (SQLDataException sql){
            Intent loginIntent = new Intent(LoginProgressBar.this, LoginScreenActivity.class);
            loginIntent.putExtra("wrongPassword", 35);
            LoginProgressBar.this.startActivity(loginIntent);
        } catch (Exception e) {
            Intent registerIntent = new Intent(LoginProgressBar.this, RegisterScreenActivity.class);
            registerIntent.putExtra("registerError", 45);
            LoginProgressBar.this.startActivity(registerIntent);
        }

    }
}
