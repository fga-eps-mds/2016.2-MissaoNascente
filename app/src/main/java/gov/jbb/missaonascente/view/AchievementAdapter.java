package gov.jbb.missaonascente.view;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import gov.jbb.missaonascente.R;
import gov.jbb.missaonascente.model.Achievement;


public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.AchievementsViewHolder>{
    private  Context context;
    private  ArrayList<Achievement> achievements;

    public AchievementAdapter(Context context, ArrayList<Achievement> achievements){
        setContext(context);
        setAchievements(achievements);
    }


    @Override
    public void onBindViewHolder(final AchievementsViewHolder viewHolder, final int position){
        Achievement achievement = achievements.get(position);

        if(achievement.isExplorer()){
            String descriptionText = achievement.getDescriptionAchievement();
            viewHolder.description.setText(descriptionText);

            String nameText = achievement.getNameAchievement();

            viewHolder.name.setText(nameText);

            ArrayList<Integer> ids = getIds(achievement.getKeys());

            int backgroundColorId = ids.get(0);
            int medalImageId = ids.get(1);

            viewHolder.relativeLayout.setBackgroundColor(ContextCompat.getColor(context,backgroundColorId));
            viewHolder.cardView.setElevation(5 * context.getResources().getDisplayMetrics().density);

            viewHolder.achievementImage.setImageResource(medalImageId);

        }else{
            String descriptionText = achievement.getDescriptionAchievement();
            viewHolder.description.setText(descriptionText);
            viewHolder.description.setTextColor(ContextCompat.getColor(context,R.color.colorPrimaryText));


            String nameText = "???";

            viewHolder.name.setText(nameText);
            viewHolder.name.setTextColor(ContextCompat.getColor(context,R.color.colorPrimaryText));

            viewHolder.relativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.white));

            viewHolder.achievementImage.setImageResource(R.mipmap.locked_achievement);

            viewHolder.cardView.setElevation(0);


        }

    }

    private ArrayList<Integer> getIds(int keys){
        int type = keys&15;
        int idColor, idImage;

        switch (type){
            case Achievement.PLATINUM:
                idColor = R.color.platinum;
                idImage = R.mipmap.achievement_platinum;
                break;
            case Achievement.GOLD:
                idColor = R.color.gold;
                idImage = R.mipmap.achievement_gold;
                break;
            case Achievement.SILVER:
                idColor = R.color.silver;
                idImage = R.mipmap.achievement_silver;
                break;
            case Achievement.BRONZE:
                idColor = R.color.bronze;
                idImage = R.mipmap.achievement_bronze;
                break;
            default:
                idColor = R.color.colorAccent;
                idImage = R.mipmap.achievement_bronze;
                break;
        }

        ArrayList <Integer> ids = new ArrayList<>();
        ids.add(idColor);
        ids.add(idImage);

        return ids;
    }

    @Override
    public int getItemCount(){
        int numberOfAchievements;

        if(achievements!=null){
            numberOfAchievements = achievements.size();
        }else{
            numberOfAchievements = 0;
        }

        return numberOfAchievements;
    }

    @Override
    public AchievementsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_achievement,viewGroup,false);

        AchievementsViewHolder achievementsViewHolder = new AchievementsViewHolder(view);

        return achievementsViewHolder;
    }

    public class AchievementsViewHolder extends RecyclerView.ViewHolder{
        private ImageView achievementImage;
        private TextView name;
        private TextView description;
        private RelativeLayout relativeLayout;
        private CardView cardView;


        public AchievementsViewHolder (View view){
            super(view);

            achievementImage = (ImageView) view.findViewById(R.id.achievementImage);
            name = (TextView) view.findViewById(R.id.achievementName);
            description = (TextView) view.findViewById(R.id.achievementDescription);
            relativeLayout =  (RelativeLayout) view.findViewById(R.id.achievementBackground);
            cardView = (CardView) view.findViewById(R.id.card_view);
        }

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(ArrayList<Achievement> achievements) {
        this.achievements = achievements;
    }
}
