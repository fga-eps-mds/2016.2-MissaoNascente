package gov.jbb.missaonascente.view;


import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import gov.jbb.missaonascente.R;
import gov.jbb.missaonascente.model.Achievement;


public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.AchievementsViewHolder>{
    private  Context context;
    private  List<Achievement> achievements;
    private  AchievementOnClickListener achievementOnClickListener;

    @Override
    public void onBindViewHolder(final AchievementsViewHolder viewHolder, final int position){
        Achievement achievement = achievements.get(position);

        String descriptionText = achievement.getDescriptionAchievement();
        viewHolder.description.setText(descriptionText);

        String nameText = achievement.getNameAchievement();

        viewHolder.name.setText(nameText);

        if(achievementOnClickListener != null){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    achievementOnClickListener.onClickAchievement(viewHolder.itemView, position);
                }
            } );
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

    public interface AchievementOnClickListener{
        public void onClickAchievement(View view, int position);
    }



    public class AchievementsViewHolder extends RecyclerView.ViewHolder{
        private ImageView achievementImage;
        private TextView name;
        private TextView description;


        public AchievementsViewHolder (View view){
            super(view);

            achievementImage = (ImageView) view.findViewById(R.id.achievementImage);
            name = (TextView) view.findViewById(R.id.achievementName);
            description = (TextView) view.findViewById(R.id.achievementDescription);
        }

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }
}
