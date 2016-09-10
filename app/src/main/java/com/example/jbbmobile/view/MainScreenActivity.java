package com.example.jbbmobile.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.Register;
import com.example.jbbmobile.model.Explorers;

import java.util.List;

public class MainScreenActivity extends AppCompatActivity {

    private ListView explorersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        final Context contextMainScreen = getApplicationContext();
        explorersList = (ListView) findViewById(R.id.new_explorer);

        Register registerController = new Register();
        List<Explorers> Explorers = registerController.getExplorersList(contextMainScreen);
        ArrayAdapter<Explorers> adapter = new ArrayAdapter<Explorers>(this, android.R.layout.simple_expandable_list_item_1,
                Explorers);
        explorersList.setAdapter(adapter);
        Button preferenceButton = (Button) findViewById(R.id.preferenceButton);

        preferenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainScreenActivity.this, PreferenceScreenActivity.class);
                MainScreenActivity.this.startActivity(registerIntent);
            }
        });
    }


}
