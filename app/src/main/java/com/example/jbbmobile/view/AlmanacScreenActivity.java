package com.example.jbbmobile.view;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
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
    private String[] web={};
    private String[] web2={};
    private String[] web3={};
    private int[] elements = {};
    private int[] elements1 = {};
    private int[] elements2 = {};
    private int[] list = {};
    private int[] list2 = {};
    private int[] list3 = {};

    protected void onCreate(Bundle savedInstanceState) {
        int currentPeriod;
        currentPeriod = BooksController.currentPeriod;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almanac_screen);
        final Context contextAlmanacScreen = getApplicationContext();
        initViews();
        BooksController booksController = new BooksController(this);
        web = booksController.getElementsName(0);
        web2 = booksController.getElementsName(1);
        web3 = booksController.getElementsName(2);

        elements = booksController.getElementsId(0);
        elements1 = booksController.getElementsId(1);
        elements2 = booksController.getElementsId(2);

        list = booksController.getElementsImage(contextAlmanacScreen, 0);
        list2 = booksController.getElementsImage(contextAlmanacScreen, 1);
        list3 = booksController.getElementsImage(contextAlmanacScreen, 2);

        switch (currentPeriod){
            case 1:
                gridView.setAdapter(new CustomAdapter(this,web,list, 0, elements));
                break;
            case 2:
                gridView.setAdapter(new CustomAdapter(this,web2,list2, 1, elements1));
                break;
            case 3:
                gridView.setAdapter(new CustomAdapter(this,web3,list3, 2, elements2));
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

    protected void onStart() {
        super.onStart();
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
                gridView.setAdapter(new CustomAdapter(this,web,list, 0, elements));
                break;
            case R.id.imageButton2:
                gridView.setAdapter(new CustomAdapter(this,web2,list2, 1, elements1));
                break;
            case R.id.imageButton3:
                gridView.setAdapter(new CustomAdapter(this,web3,list3, 2, elements2));
                break;
        }
    }
}
