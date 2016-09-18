package com.example.jbbmobile.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.Book;
import com.example.jbbmobile.controller.Element;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AlmanacScreenActivity extends AppCompatActivity {
    private EditText date;
    private GridView gridView;
    // private TextView textView;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almanac_screen);
        final Context contextAlmanacScreen = getApplicationContext();
        initViews();

        int[] list = new int[]{R.drawable.btn_google_dark_normal,R.drawable.btn_google_dark_normal,R.drawable.btn_google_dark_normal};

        final String[] web={"Imagem1","Imagem2","Imagem3"};

        gridView.setAdapter(new CustomAdapter(this,web,list));

       /* gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), "Voce clicou em: " +(position+1), Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void initViews(){
        //date = (EditText) findViewById(R.id.dateSystem);
        gridView = (GridView) findViewById(R.id.gridView);
        // textView =(TextView) findViewById(R.id.textView);
    }

    protected void onStart() {
        super.onStart();
        //date.setText(systemDate());
    }

    public String systemDate(){
        /// TODA A LOGICA PARA PEGAR A DATA TODA, OU SÓ DIA E MES ETC...
        ///OBS: NAO FICARA AQUI.PROVAVELMENTE VAI FICAR NA CONTROLLER BOOK
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

