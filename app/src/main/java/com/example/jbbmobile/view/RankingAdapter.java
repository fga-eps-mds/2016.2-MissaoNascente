package com.example.jbbmobile.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jbbmobile.R;
import com.example.jbbmobile.model.Explorer;

import java.util.List;

public class RankingAdapter extends ArrayAdapter<Explorer>{


    public RankingAdapter(Context context, List<Explorer> explorerList) {
        super(context, 0, explorerList);


    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;


        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.activity_ranking_screen, parent, false);
        }
        Explorer explorer = getItem(position);

        TextView positionTextView = (TextView)view.findViewById(R.id.rankingPosition);
        positionTextView.setText(String.valueOf(explorer.increment()));

        TextView rankingName = (TextView) view.findViewById(R.id.rankingName);
        rankingName.setText(explorer.getNickname());

        TextView rankingScore = (TextView) view.findViewById(R.id.rankingScore);
        rankingScore.setText(String.valueOf(explorer.getScore()));

        return view;
    }

}
