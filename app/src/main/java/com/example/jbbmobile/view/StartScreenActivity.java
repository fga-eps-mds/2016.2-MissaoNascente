package com.example.jbbmobile.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.jbbmobile.R;

public class StartScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        RelativeLayout normalSingUpRelativeLayout = (RelativeLayout) findViewById(R.id.normalSingUpRelativeLayout);

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
}
