package gov.jbb.missaonascente.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import gov.jbb.missaonascente.R;
import gov.jbb.missaonascente.controller.LoginController;
import gov.jbb.missaonascente.model.Explorer;
import java.util.List;

public class RankingAdapter extends ArrayAdapter<Explorer>{

    public RankingAdapter(Context context, List<Explorer> explorerList) {
        super(context, 0, explorerList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        Explorer explorer = getItem(position);
        LoginController controller = new LoginController();
        controller.loadFile(getContext());

        if(position < 10){
            if(explorer.getNickname().equals(controller.getExplorer().getNickname())){
                view = LayoutInflater.from(getContext()).inflate(R.layout.current_user_ranking, parent, false);
            }else{
                view = LayoutInflater.from(getContext()).inflate(R.layout.activity_ranking_screen, parent, false);
            }
        }else{
            if(explorer.getPosition() <= 10){
                view = LayoutInflater.from(getContext()).inflate(R.layout.current_user_ranking, parent, false);
                view.setVisibility(View.GONE);
            }else{
                view = LayoutInflater.from(getContext()).inflate(R.layout.current_user_ranking, parent, false);
            }
        }

        TextView positionTextView = (TextView)view.findViewById(R.id.rankingPosition);
        positionTextView.setText(String.valueOf(explorer.getPosition()));
        int [] ids = {R.color.gold, R.color.silver, R.color.bronze};
        if(explorer.getPosition() <= 3){
            int id = ids[explorer.getPosition() - 1];
            positionTextView.setTextColor(ContextCompat.getColor(getContext(), id));

        }

        TextView rankingName = (TextView) view.findViewById(R.id.rankingName);
        rankingName.setText(explorer.getNickname());

        TextView rankingScore = (TextView) view.findViewById(R.id.rankingScore);
        rankingScore.setText(String.valueOf(explorer.getScore()));

        return view;
    }
}