package com.example.jbbmobile.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.BooksController;

public class AlmanacScreenActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton orangeBook;
    private ImageButton greenBook;
    private ImageButton blueBook;
    private GridView gridView;
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

        orangeBook=(ImageButton) findViewById(R.id.orangeBook);
        orangeBook.setOnClickListener(this);

        greenBook=(ImageButton) findViewById(R.id.greenBook);
        greenBook.setOnClickListener(this);

        blueBook=(ImageButton) findViewById(R.id.blueBook);
        blueBook.setOnClickListener(this);

    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent mainScreenIntent = new Intent(AlmanacScreenActivity.this, MainScreenActivity.class);
        AlmanacScreenActivity.this.startActivity(mainScreenIntent);
        finish();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.orangeBook:
                gridView.setAdapter(new CustomAdapter(this, booksController, 0));
                setDefaultBooks();
                orangeBook.setImageResource(R.drawable.book_icon_open_orange);
                break;
            case R.id.greenBook:
                gridView.setAdapter(new CustomAdapter(this, booksController, 1));
                setDefaultBooks();
                greenBook.setImageResource(R.drawable.book_icon_open_green);
                break;
            case R.id.blueBook:
                gridView.setAdapter(new CustomAdapter(this, booksController, 2));
                setDefaultBooks();
                blueBook.setImageResource(R.drawable.book_icon_open_blue);
                break;
        }
    }

    public void setDefaultBooks(){
        blueBook.setImageResource(R.drawable.book_blue);
        orangeBook.setImageResource(R.drawable.book_orange);
        greenBook.setImageResource(R.drawable.book_green);

    }
}
