package com.example.jbbmobile.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.MainController;
import com.example.jbbmobile.controller.RankingController;

public class RankingScreenActivity extends AppCompatActivity {
    protected RankingController controller;
    protected MainController mainController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_screen);
        setContentView(R.layout.ranking_adapter);
        controller = new RankingController();
        mainController = new MainController();
        controller.updateRanking(this);

        if(mainController.checkIfUserHasInternet(this)) {
            new RankingWebService().execute();
        }else{
            Toast.makeText(this,R.string.rankingNoInternet, Toast.LENGTH_SHORT).show();

        }
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
