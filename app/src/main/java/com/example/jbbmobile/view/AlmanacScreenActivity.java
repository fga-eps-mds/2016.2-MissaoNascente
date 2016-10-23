package com.example.jbbmobile.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.BooksController;

public class AlmanacScreenActivity extends AppCompatActivity implements View.OnClickListener{

    private GridView gridView;
    private ImageButton imageButton1;
    private ImageButton imageButton2;
    private ImageButton imageButton3;
    private BooksController booksController;

    protected void onCreate(Bundle savedInstanceState) {
        int currentPeriod;

        booksController = new BooksController(this);
        booksController.currentPeriod();

        currentPeriod = booksController.getCurrentPeriod();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almanac_screen);
        initViews();

        switch (currentPeriod){
            case 1:
                gridView.setAdapter(new CustomAdapter(this,booksController, 0));
                break;
            case 2:
                gridView.setAdapter(new CustomAdapter(this,booksController, 1));
                break;
            case 3:
                gridView.setAdapter(new CustomAdapter(this,booksController, 2));
                break;
    }


}
    private void initViews(){
        gridView = (GridView) findViewById(R.id.gridView);

        imageButton1=(ImageButton) findViewById(R.id.imageButton1);
        imageButton1.setOnClickListener(this);

        imageButton2=(ImageButton) findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(this);

        imageButton3=(ImageButton) findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(this);

    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent mainScreenIntent = new Intent(AlmanacScreenActivity.this, MainScreenActivity.class);
        AlmanacScreenActivity.this.startActivity(mainScreenIntent);
        finish();
    }

    public void onClick(View v) {
        switch(v.getId()){
            case R.id.imageButton1:
                gridView.setAdapter(new CustomAdapter(this,booksController, 0));
                break;
            case R.id.imageButton2:
                gridView.setAdapter(new CustomAdapter(this,booksController, 1));
                break;
            case R.id.imageButton3:
                gridView.setAdapter(new CustomAdapter(this,booksController, 2));
                break;
        }
    }
}
