package com.example.jbbmobile.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.Book;
import com.example.jbbmobile.controller.Element;
import com.example.jbbmobile.dao.BookDAO;
import com.example.jbbmobile.model.Elements;

public class ElementScreenActivity extends AppCompatActivity {
    private ImageView elementImage;
    private TextView elementsName;
    private TextView elementsDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_screen);
        initViews();
        Intent elementIntent = getIntent();
        int idElement = elementIntent.getIntExtra("idElement", 0);
        Element element = new Element();

        Elements touchedElement;
        touchedElement = element.findElementByID(idElement, this.getApplicationContext());
        /*Book book = new Book();
        book.getAllBooksData(ElementScreenActivity.this.getApplicationContext());
        book.getElementsFromDatabase(ElementScreenActivity.this.getBaseContext());*/

        int resID = getResources().getIdentifier(touchedElement.getDefaultImage(), "drawable", getPackageName());
        elementImage.setImageResource(resID);
        this.elementsName.setText(touchedElement.getNameElement());
        this.elementsDescription.setText(touchedElement.getDescriptionString());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mainScreenActivity = new Intent(ElementScreenActivity.this, MainScreenActivity.class);
        ElementScreenActivity.this.startActivity(mainScreenActivity);
    }

    private void initViews(){
        this.elementImage = (ImageView) findViewById(R.id.elementImage);
        this.elementsName = (TextView) findViewById(R.id.elementsName);
        this.elementsDescription = (TextView)findViewById(R.id.elementsDescription);
    }

}
