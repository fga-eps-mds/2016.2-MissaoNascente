package gov.jbb.missaonascente.controller;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;

import java.util.List;

import gov.jbb.missaonascente.dao.AchievementDAO;
import gov.jbb.missaonascente.dao.AchievementRequest;
import gov.jbb.missaonascente.model.Achievement;

public class AchievementController {
    private boolean action = false;

    public AchievementController(){

    }

    public void downloadAchievementFromDatabase(Context context){
        final AchievementDAO achievementDAO = new AchievementDAO(context);
        AchievementRequest achievementRequest = new AchievementRequest();
        achievementRequest.request(context, new AchievementRequest.Callback() {
            @Override
            public void callbackResponse(List<Achievement> achievementsList) {
                insertAllAchievements(achievementDAO,achievementsList);
                setAction(true);
            }
        });

    }


    public void insertAllAchievements(AchievementDAO achievementDAO , List<Achievement> achievementsList){
        for( Achievement achievement : achievementsList){
            try {
                achievementDAO.insertAchievement(achievement);
            }catch (SQLException exception){
                exception.printStackTrace();
            }
            Log.i("==============", achievement.getNameAchievement()+" ============= ");
        }
    }

    public boolean isAction() {
        return action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }
}
