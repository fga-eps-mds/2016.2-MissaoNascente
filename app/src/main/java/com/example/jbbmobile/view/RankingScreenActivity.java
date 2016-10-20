package com.example.jbbmobile.view;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.RankingController;
import com.example.jbbmobile.model.Explorer;

import java.util.ArrayList;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RankingScreenActivity extends AppCompatActivity {
    protected RankingController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_screen);
        setContentView(R.layout.ranking_adapter);
        controller = new RankingController();
        controller.updateRanking(this);
        new RankingWebService().execute();
    }

    private class RankingWebService extends AsyncTask<Void, Void, Boolean>{
        @Override
        protected Boolean doInBackground(Void... params) {
            while (!controller.isAction());
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            RankingAdapter adapter = new RankingAdapter(RankingScreenActivity.this, controller.getExplorers());
            ListView rankingListView = (ListView)findViewById(R.id.ranking);
            rankingListView.setAdapter(adapter);
        }
    }
}
