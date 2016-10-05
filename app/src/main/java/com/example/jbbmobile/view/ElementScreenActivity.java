package com.example.jbbmobile.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.ElementsController;
import com.example.jbbmobile.model.Element;

public class ElementScreenActivity extends AppCompatActivity {
    private ImageView elementImage;
    private TextView elementsName;
    private TextView elementsDescription;

    private static final String TAG = "ElementScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_screen);
        initViews();
        Intent elementIntent = getIntent();
        int qrCodeElement = Integer.parseInt(elementIntent.getStringExtra("code"));

        Integer qrCode  = new Integer(qrCodeElement);
        Log.d(TAG, "qrCodeNumber = " + qrCode.toString());



        ElementsController elementsController = new ElementsController();

        Element touchedElement;
        touchedElement = elementsController.findElementByQrCode(qrCodeElement, this.getApplicationContext());
        /*BooksController book = new BooksController();
        book.getAllBooksData(ElementScreenActivity.this.getApplicationContext());
        book.getElementsFromDatabase(ElementScreenActivity.this.getBaseContext());*/
        Log.d(TAG, "{" + touchedElement.getDefaultImage() + "}");
        int resID = getResources().getIdentifier(touchedElement.getDefaultImage(), "drawable", getPackageName());
        elementImage.setImageResource(resID);
        this.elementsName.setText(touchedElement.getNameElement());
        this.elementsDescription.setText(touchedElement.getDescriptionString());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent almanacScreenIntent = new Intent(ElementScreenActivity.this, AlmanacScreenActivity.class);
        ElementScreenActivity.this.startActivity(almanacScreenIntent);
        finish();

    }

    private void initViews(){
        this.elementImage = (ImageView) findViewById(R.id.elementImage);
        this.elementsName = (TextView) findViewById(R.id.elementsName);
        this.elementsDescription = (TextView)findViewById(R.id.elementsDescription);
    }

}
