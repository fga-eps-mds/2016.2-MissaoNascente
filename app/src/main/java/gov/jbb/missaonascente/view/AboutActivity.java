package gov.jbb.missaonascente.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import gov.jbb.missaonascente.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mainScreenIntent = new Intent(AboutActivity.this, MainScreenActivity.class);
        AboutActivity.this.startActivity(mainScreenIntent);
        finish();
    }
}
