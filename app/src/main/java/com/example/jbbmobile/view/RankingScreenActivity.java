package com.example.jbbmobile.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jbbmobile.R;
import com.example.jbbmobile.model.Explorer;

import java.util.ArrayList;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RankingScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_screen);
        setContentView(R.layout.ranking_adapter);

        final List<Explorer> explorers = new ArrayList<>();
        explorers.add(new Explorer(1, "Explorador1"));
        explorers.add(new Explorer(50, "Explorador2"));
        explorers.add(new Explorer(3, "Explorador3"));
        explorers.add(new Explorer(32, "Explorador4"));
        explorers.add(new Explorer(5, "Explorador5"));
        explorers.add(new Explorer(42, "Explorador6"));
        explorers.add(new Explorer(15, "Explorador7"));
        explorers.add(new Explorer(8, "Explorador8"));
        explorers.add(new Explorer(23, "Explorador9"));
        explorers.add(new Explorer(10, "Explorador10"));

        Collections.sort(explorers, new Comparator<Explorer>() {
            @Override
            public int compare(Explorer o1, Explorer o2) {
                return o2.getScore() - o1.getScore();
            }
        });

        RankingAdapter adapter = new RankingAdapter(this, explorers);
        ListView rankingListView = (ListView)findViewById(R.id.ranking);
        rankingListView.setAdapter(adapter);
    }
}
