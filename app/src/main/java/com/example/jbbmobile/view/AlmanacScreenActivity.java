package com.example.jbbmobile.view;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.Book;
import com.example.jbbmobile.controller.Element;
import com.example.jbbmobile.model.Explorers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlmanacScreenActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText date;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almanac_screen);
        final Context contextAlmanacScreen = getApplicationContext();
        initViews();
        Book book = new Book();
        web = book.getElementsName(contextAlmanacScreen,0);
        web2 = book.getElementsName(contextAlmanacScreen,1);
        web3 = book.getElementsName(contextAlmanacScreen,2);

        elements = book.getElementsId(contextAlmanacScreen,0);
        elements1 = book.getElementsId(contextAlmanacScreen,1);
        elements2 = book.getElementsId(contextAlmanacScreen,2);

        list = book.getElementsImage(contextAlmanacScreen, 0);
        list2 = book.getElementsImage(contextAlmanacScreen, 1);
        list3 = book.getElementsImage(contextAlmanacScreen, 2);

        switch (currentPeriod()){
            case 1:
                gridView.setAdapter(new CustomAdapter(this,web,list, 0, elements));
                break;
            case 2:
                gridView.setAdapter(new CustomAdapter(this,web2,list, 1, elements1));
                break;
            case 3:
                gridView.setAdapter(new CustomAdapter(this,web3,list, 2, elements2));
                break;
        }
    }

    public int currentPeriod(){
        int month;
        month=Integer.valueOf(systemDate());
        if(month<10){
            return 1;
        }else{
            return 2;
        }
    }

    private void initViews(){
        //date = (EditText) findViewById(R.id.dateSystem);
        // textView =(TextView) findViewById(R.id.textView);
        gridView = (GridView) findViewById(R.id.gridView);

        imageButton1=(ImageButton) findViewById(R.id.imageButton1);
        imageButton1.setOnClickListener((View.OnClickListener) this);

        imageButton2=(ImageButton) findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener((View.OnClickListener) this);

        imageButton3=(ImageButton) findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener((View.OnClickListener) this);

    }

    protected void onStart() {
        super.onStart();
        //date.setText(systemDate());
    }

    public String systemDate(){
        long date = System.currentTimeMillis();
        SimpleDateFormat mes = new SimpleDateFormat("MM");
        String month = mes.format(date);
        return month;
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
