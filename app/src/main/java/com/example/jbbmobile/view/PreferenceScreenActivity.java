package com.example.jbbmobile.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.View.OnClickListener;
import com.example.jbbmobile.R;

import java.security.PublicKey;

public class PreferenceScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_screen);


    }

    public void deleteAccount(View v) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Toast.makeText(RegisterScreenActivity.registerScreenContext,"CONTA DELETADA",Toast.LENGTH_SHORT).show();
                        Intent registerIntent = new Intent(PreferenceScreenActivity.this, StartScreenActivity.class);
                        PreferenceScreenActivity.this.startActivity(registerIntent);
                        finish();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //botão NÃO clicado
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseja excluir o registro?").setPositiveButton("SIM", dialogClickListener)
                .setNegativeButton("NÃO", dialogClickListener).show();
    }

    public void editAccount(View v){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Toast.makeText(RegisterScreenActivity.registerScreenContext,"CONTA EDITADA",Toast.LENGTH_SHORT).show();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //botão NÃO clicado
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseja editar o registro?").setPositiveButton("SIM", dialogClickListener)
                .setNegativeButton("NÃO", dialogClickListener).show();

    }



}
