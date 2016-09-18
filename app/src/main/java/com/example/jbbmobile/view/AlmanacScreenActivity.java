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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AlmanacScreenActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText date;
    private GridView gridView;
    private ImageButton imageButton1;
    private ImageButton imageButton2;
    private ImageButton imageButton3;
    // private TextView textView;


    int[] list = new int[]{R.drawable.btn_google_dark_normal,R.drawable.btn_google_dark_normal,R.drawable.btn_google_dark_normal,
            R.drawable.btn_google_dark_normal,R.drawable.btn_google_dark_normal,R.drawable.btn_google_dark_normal,
            R.drawable.btn_google_dark_normal,R.drawable.btn_google_dark_normal,R.drawable.btn_google_dark_normal};

    int[] list2 = new int[]{R.drawable.btn_google_dark_normal,R.drawable.btn_google_dark_normal,R.drawable.btn_google_dark_normal,
            R.drawable.btn_google_dark_normal,R.drawable.btn_google_dark_normal,R.drawable.btn_google_dark_normal,
            R.drawable.btn_google_dark_normal,R.drawable.btn_google_dark_normal,R.drawable.btn_google_dark_normal};

    int[] list3 = new int[]{R.drawable.pequi,R.drawable.pequi,R.drawable.pequi,
            R.drawable.pequi,R.drawable.pequi,R.drawable.pequi,
            R.drawable.pequi,R.drawable.pequi,R.drawable.pequi};


    final String[] web={"Imagem1","Imagem2","Imagem3",
            "Imagem4","Imagem5","Imagem6",
            "Imagem7","Imagem8","Imagem9"
    };
    final String[] web2={"Imagem1.2","Imagem2.2","Imagem3.2",
            "Imagem4.2","Imagem5.2","Imagem6.2",
            "Imagem7.2","Imagem8.2","Imagem9.2"
    };
    final String[] web3={"Imagem1.3","Imagem2.3","Imagem3.3",
            "Imagem4.3","Imagem5.3","Imagem6.3",
            "Imagem7.3","Imagem8.3","Imagem9.3"
    };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almanac_screen);
        final Context contextAlmanacScreen = getApplicationContext();
        initViews();


        switch (currentPeriod()){
            case 1:
                gridView.setAdapter(new CustomAdapter(this,web,list));
                Toast.makeText(getBaseContext(),"PERIODO 1 ATIVO", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                gridView.setAdapter(new CustomAdapter(this,web2,list));
                break;
            case 3:
                gridView.setAdapter(new CustomAdapter(this,web3,list));
                break;

        }
        //gridView.setAdapter(new CustomAdapter(this,web,list));

       /* gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), "Voce clicou em: " +(position+1), Toast.LENGTH_SHORT).show();
            }
        });*/
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
        /// TODA A LOGICA PARA PEGAR A DATA TODA, OU SÓ DIA E MES ETC...
        ///OBS: NAO FICARA AQUI.PROVAVELMENTE VAI FICAR NA CONTROLLER BOOK
        // dataSystem = (TextView) findViewById(R.id.catchDate);
        long date = System.currentTimeMillis();
        //SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        //String dateString = sdf.format(date);
        //dataSystem.setText(dateString);
        SimpleDateFormat mes = new SimpleDateFormat("MM");
        String month = mes.format(date);
        //SimpleDateFormat dia = new SimpleDateFormat("d");
        //String day = dia.format(date);
        //int dayNumber = Integer.valueOf(dia.format(date));
        //int monthNumber = Integer.valueOf(mes.format(date));
        //if ( monthNumber == 9 ){
        //  Toast.makeText(getApplicationContext(),"ESTACAO 1", Toast.LENGTH_SHORT).show();
        //}
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
                Toast.makeText(getBaseContext(),"LIVRO 1", Toast.LENGTH_SHORT).show();
                gridView.setAdapter(new CustomAdapter(this,web,list));
                break;

            case R.id.imageButton2:
                Toast.makeText(getBaseContext(),"LIVRO 2", Toast.LENGTH_SHORT).show();
                gridView.setAdapter(new CustomAdapter(this,web2,list2));
                break;

            case R.id.imageButton3:
                Toast.makeText(getBaseContext(),"LIVRO 3", Toast.LENGTH_SHORT).show();
                gridView.setAdapter(new CustomAdapter(this,web3,list3));
                break;
        }
    }
}
