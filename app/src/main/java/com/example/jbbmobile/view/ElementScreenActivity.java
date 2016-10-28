package com.example.jbbmobile.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.ElementsController;
import com.example.jbbmobile.controller.LoginController;
import com.example.jbbmobile.model.Element;

public class ElementScreenActivity extends AppCompatActivity {
    private ImageView elementImage;
    private TextView elementsName;
    private TextView elementsDescription;
    private TextView catchDate;

    private static final String TAG = "ElementScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_screen);
        initViews();
        Intent elementIntent = getIntent();

        LoginController loginController = new LoginController();
            loginController.loadFile(this);
        ElementsController elementsController = new ElementsController();
        int idElement = elementIntent.getIntExtra("idElement", 0);


        Element touchedElement;
        touchedElement = elementsController.findElementByID(idElement, loginController.getExplorer().getEmail(),this.getApplicationContext());

        int resID = getResources().getIdentifier(touchedElement.getDefaultImage(), "drawable", getPackageName());
        elementImage.setImageResource(resID);
        this.elementsName.setText(touchedElement.getNameElement());
        this.elementsDescription.setText(touchedElement.getTextDescription());
        String catchDate =  this.catchDate.getText() + ": " + touchedElement.getCatchDate();
        this.catchDate.setText(catchDate);
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
        this.catchDate = (TextView) findViewById(R.id.catchDate);
    }
}
