package com.example.jbbmobile.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.Book;
import com.example.jbbmobile.controller.Element;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AlmanacScreenActivity extends AppCompatActivity {
    private EditText date;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almanac_screen);
        final Context contextAlmanacScreen = getApplicationContext();
        initViews();
    }

    private void initViews(){
        date = (EditText) findViewById(R.id.dateSystem);
    }
    protected void onStart() {
        super.onStart();
        date.setText(systemDate());
    }

    /// TODA A LOGICA PARA PEGAR A DATA TODA, OU SÓ DIA E MES ETC...
    ///OBS: NAO FICARA AQUI.PROVAVELMENTE VAI FICAR NA C
    public String systemDate(){
        // dataSystem = (TextView) findViewById(R.id.catchDate);
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        String dateString = sdf.format(date);
        //dataSystem.setText(dateString);
        //SimpleDateFormat mes = new SimpleDateFormat("MM");
        //String month = mes.format(date);
        //SimpleDateFormat dia = new SimpleDateFormat("d");
        //String day = dia.format(date);
        //int dayNumber = Integer.valueOf(dia.format(date));
        //int monthNumber = Integer.valueOf(mes.format(date));
        //if ( monthNumber == 9 ){
        //  Toast.makeText(getApplicationContext(),"ESTACAO 1", Toast.LENGTH_SHORT).show();
        //}
        return dateString;
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent mainScreenIntent = new Intent(AlmanacScreenActivity.this, MainScreenActivity.class);
        AlmanacScreenActivity.this.startActivity(mainScreenIntent);
        finish();
    }
}