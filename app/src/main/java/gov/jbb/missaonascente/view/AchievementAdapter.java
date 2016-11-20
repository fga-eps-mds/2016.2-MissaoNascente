package gov.jbb.missaonascente.view;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;

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
        }else{
            String descriptionText = achievement.getDescriptionAchievement();
            viewHolder.description.setText(descriptionText);
            viewHolder.description.setTextColor(ContextCompat.getColor(context,R.color.colorPrimaryText));

            String nameText = "??????????";

            viewHolder.name.setText(nameText);
            viewHolder.name.setTextColor(ContextCompat.getColor(context,R.color.colorPrimaryText));

            viewHolder.relativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.white));

            viewHolder.achievementImage.setImageResource(R.mipmap.locked_achievement);


        }

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


        public AchievementsViewHolder (View view){
            super(view);

            achievementImage = (ImageView) view.findViewById(R.id.achievementImage);
            name = (TextView) view.findViewById(R.id.achievementName);
            description = (TextView) view.findViewById(R.id.achievementDescription);
            relativeLayout =  (RelativeLayout) view.findViewById(R.id.achievementBackground);
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
