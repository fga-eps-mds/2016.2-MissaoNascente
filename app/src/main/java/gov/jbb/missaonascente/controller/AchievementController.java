package gov.jbb.missaonascente.controller;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import java.util.List;

import gov.jbb.missaonascente.dao.AchievementDAO;
import gov.jbb.missaonascente.dao.AchievementExplorerRequest;
import gov.jbb.missaonascente.dao.AchievementRequest;
import gov.jbb.missaonascente.model.Achievement;

public class AchievementController {
    private boolean action = false;
    private boolean response;

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

    //Relation table methods
    public boolean insertAchievementExplorer(final Context preferenceContext, String email, int idAchievement) {
        try{
            setAction(false);
            AchievementExplorerRequest achievementExplorerRequest = new AchievementExplorerRequest(email, idAchievement);
            achievementExplorerRequest.requestUpdateAchievements(preferenceContext, new AchievementExplorerRequest.Callback() {
                @Override
                public void callbackResponse(boolean response) {
                    setResponse(true);
                    setAction(true);
                }

                @Override
                public void callbackResponse(List<Achievement> achievements) {}
            });
        }catch(SQLiteConstraintException exception){
            exception.printStackTrace();
        }

        return true;
    }

    public void updateAchievementExplorerTable(final Context context, final String email){

        setAction(false);

        final AchievementExplorerRequest achievementExplorerRequest = new AchievementExplorerRequest(email);

        achievementExplorerRequest.requestRetrieveAchievements(context, new AchievementExplorerRequest.Callback() {
            @Override
            public void callbackResponse(boolean response) {
            }

            @Override
            public void callbackResponse(List<Achievement> achievements) {
                AchievementDAO database = new AchievementDAO(context);
                database.deleteAllAchievementsFromAchievementExplorer(database.getWritableDatabase());

                for(Achievement achievement : achievements){
                    int resposta = database.insertAchievementExplorer(achievement.getIdAchievement(), email);
                    Log.d("Resposta", String.valueOf(resposta));
                }

                setAction(true);
            }
        });
    }

    public void sendAchievementsExplorerTable(final Context context, final String email){
        AchievementExplorerRequest achievementExplorerRequest = new AchievementExplorerRequest(email);
        achievementExplorerRequest.sendAchievementsExplorer(context);
    }
    //End of relation table methods

    public boolean isAction() {
        return action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }
}
