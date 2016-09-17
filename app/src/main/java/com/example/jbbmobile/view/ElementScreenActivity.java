package com.example.jbbmobile.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.Book;
import com.example.jbbmobile.controller.Element;
import com.example.jbbmobile.dao.BookDAO;
import com.example.jbbmobile.model.Elements;

public class ElementScreenActivity extends AppCompatActivity {

    private ImageView elementImage;
    private TextView elementDescription;
    private TextView bookName;
    private TextView elementDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_screen);
        initViews();
        Book book = new Book();
        book.getAllBooksData(ElementScreenActivity.this.getApplicationContext());
        book.getElementsFromDatabase(ElementScreenActivity.this.getBaseContext());
        int resID = getResources().getIdentifier(book.getBook(1).getElements().get(0).getDefaultImage(), "drawable", getPackageName());
        elementImage.setImageResource(resID);
        this.elementDescription.setText(book.getBook(1).getElements().get(0).getNameElement());
        this.bookName.setText(book.getBook(1).getNameBook());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mainScreenActivity = new Intent(ElementScreenActivity.this, MainScreenActivity.class);
        ElementScreenActivity.this.startActivity(mainScreenActivity);
    }

    private void initViews(){
        this.elementImage = (ImageView) findViewById(R.id.elementImage);
        this.elementDescription = (TextView) findViewById(R.id.elementsDescription);
        this.elementDate = (TextView) findViewById(R.id.catchDate);
        this.bookName = (TextView)findViewById(R.id.nameBook);
    }

}
