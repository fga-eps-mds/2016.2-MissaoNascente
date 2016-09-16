package com.example.jbbmobile.view;

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
        Element element = new Element(ElementScreenActivity.this.getApplicationContext());
        Book book = new Book(1, 1, getApplicationContext(), getResources());
        int resID = getResources().getIdentifier(book.getBook().getElementses().get(0).getDefaultImage(), "drawable", getPackageName());
        elementImage.setImageResource(resID);
        this.elementDescription.setText(book.getBook().getElementses().get(0).getNameElement());
        this.bookName.setText(book.getBook().getNameBook());
    }



    private void initViews(){
        this.elementImage = (ImageView) findViewById(R.id.elementImage);
        this.elementDescription = (TextView) findViewById(R.id.elementsDescription);
        this.elementDate = (TextView) findViewById(R.id.catchDate);
        this.bookName = (TextView)findViewById(R.id.nameBook);
    }
}
