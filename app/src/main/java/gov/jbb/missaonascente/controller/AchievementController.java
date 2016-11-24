package gov.jbb.missaonascente.controller;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import gov.jbb.missaonascente.dao.AchievementDAO;
import gov.jbb.missaonascente.dao.AchievementExplorerRequest;
import gov.jbb.missaonascente.dao.AchievementRequest;
import gov.jbb.missaonascente.dao.ElementDAO;
import gov.jbb.missaonascente.model.Achievement;
import gov.jbb.missaonascente.model.Explorer;

public class AchievementController {
    private boolean action = false;
    private Context context;
    private boolean response;

    public AchievementController(Context context){
        this.context = context;
    }

    public void downloadAchievementFromDatabase(Context context){
        final AchievementDAO achievementDAO = new AchievementDAO(context);
        AchievementRequest achievementRequest = new AchievementRequest();
        try{

            achievementRequest.requestRemaining(context, new AchievementRequest.Callback() {
                @Override
                public void callbackResponse(List<Achievement> achievementsList) {
                    insertAllAchievements(achievementDAO,achievementsList);
                    setAction(true);
                }
            });

        }catch (IllegalArgumentException exception){

            achievementRequest.request(context, new AchievementRequest.Callback() {
                @Override
                public void callbackResponse(List<Achievement> achievementsList) {
                    insertAllAchievements(achievementDAO,achievementsList);
                    setAction(true);
                }
            });
        }

    }

    public void insertAllAchievements(AchievementDAO achievementDAO , List<Achievement> achievementsList){
        for( Achievement achievement : achievementsList){
            try {
                achievementDAO.insertAchievement(achievement);
            }catch (SQLException exception){
                exception.printStackTrace();
            }
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
                    database.insertAchievementExplorer(achievement.getIdAchievement(), email);
                    Log.d("TESTE++++++", String.valueOf(achievement.getIdAchievement()) + email);
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

    private ArrayList<Achievement> getRemainingAchievements(Context context, Explorer explorer){
        AchievementDAO achievementDAO = new AchievementDAO(context);

        ArrayList<Achievement> achievements =
                new ArrayList(achievementDAO.findRemainingExplorerAchievements(explorer));

        return achievements;
    }

    private ArrayList<Achievement> getExplorerAchievements(Context context, Explorer explorer){
        AchievementDAO achievementDAO = new AchievementDAO(context);
        ArrayList<Achievement> achievements =
                new ArrayList(achievementDAO.findAllExplorerAchievements(explorer.getEmail()));

        return achievements;
    }

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

    public ArrayList<Achievement> checkForNewElementAchievements(HistoryController historyController, Explorer explorer){
        ArrayList<Integer> values = setElementValues(historyController, explorer);

        ArrayList<Achievement> newAchievements = achievementIterator(explorer, values, 0);

        return newAchievements;
    }

    public ArrayList<Achievement> checkForNewQuestionAchievements(Explorer explorer){
        ArrayList<Integer> values = setQuestionValues(explorer);

        ArrayList<Achievement> newAchievements = achievementIterator(explorer, values, 7);

        return newAchievements;
    }

    private ArrayList<Integer> setQuestionValues(Explorer explorer) {
        ArrayList<Integer> values = new ArrayList<>(2);

        Integer numberOfRightQuestions = explorer.getCorrectQuestion();
        Integer numberOfAnsweredQuestions = explorer.getQuestionAnswered();

        values.add(numberOfRightQuestions);
        values.add(numberOfAnsweredQuestions);

        return values;
    }

    private ArrayList<Achievement> achievementIterator(Explorer explorer, ArrayList<Integer> values, int offset){
        ArrayList<Achievement> achievements = this.getRemainingAchievements(context, explorer);

        ArrayList<Achievement> newAchievements = new ArrayList<>();

        for(Achievement achievement : achievements){
            Log.d("EITA", "achievement = " + achievement.getNameAchievement() + " " + values.size());
            int quantityKeys = (achievement.getKeys() >> 4);
            int quantity = achievement.getQuantity();

            boolean newAchievement = false;
            int size = values.size();
            for(int i = offset; i < size + offset; ++i){
                int binaryKey = (quantityKeys&(1 << i));

                Log.d("KEY", "keys = " + quantityKeys + " " + binaryKey + " " + (1 << i));
                if(binaryKey != 0){
                    Log.d("KEY", "pos = " + i + ", quantity = " + quantity + " x " + values.get(i-offset));
                    if(values.get(i - offset) < quantity){
                        newAchievement = false;
                        break;
                    }else{
                        newAchievement = true;
                    }
                }
            }

            if(newAchievement){
                Log.d("KEY", "Agora eh " + achievement.getNameAchievement());
                newAchievements.add(achievement);
                AchievementDAO achievementDAO = new AchievementDAO(context);
                achievementDAO.insertAchievementExplorer(achievement.getIdAchievement(), explorer.getEmail());

                if(MainController.checkIfUserHasInternet(context)){
                    insertAchievementExplorer(context, explorer.getEmail(), achievement.getIdAchievement());
                }
            }
        }

        return newAchievements;
    }

    private ArrayList<Integer> setElementValues(HistoryController historyController, Explorer explorer){
        ArrayList<Integer> values = new ArrayList<>(7);

        ElementDAO elementDAO = new ElementDAO(context);


        Integer numberOfElementsOfBook1 = elementDAO.findElementsExplorerBook(1, explorer.getEmail()).size();
        Integer numberOfElementsOfBook2 = elementDAO.findElementsExplorerBook(2, explorer.getEmail()).size();
        Integer numberOfElementsOfBook3 = elementDAO.findElementsExplorerBook(3, explorer.getEmail()).size();

        Integer totalNumbersOfElements = numberOfElementsOfBook1 + numberOfElementsOfBook2 + numberOfElementsOfBook3;

        values.add(numberOfElementsOfBook1);
        values.add(numberOfElementsOfBook2);
        values.add(numberOfElementsOfBook3);
        values.add(totalNumbersOfElements);

        BooksController booksController = new BooksController();
        booksController.currentPeriod();
        int period = booksController.getCurrentPeriod();

        Integer history1 = 0, history2 = 0, history3 = 0;
        switch (period){
            case 1:
                history1 = historyController.endHistory() ? 100 : 0;
            case 2:
                history2 = historyController.endHistory() ? 100 : 0;
            case 3:
                history3 = historyController.endHistory() ? 100 : 0;
        }

        values.add(history1);
        values.add(history2);
        values.add(history3);

        return values;
    }

    public ArrayList<Achievement> getAllAchievements(Context context, Explorer explorer) {
        ArrayList<Achievement> achievements = this.getExplorerAchievements(context, explorer);
        for(Achievement achievement : achievements){
            achievement.setIsExplorer(true);
            Log.d("---IsUser---", achievement.getNameAchievement() + achievement.getIdAchievement());
        }

        ArrayList<Achievement> remainingAchievements = this.getRemainingAchievements(context, explorer);
        for(Achievement achievement : remainingAchievements){
            achievement.setIsExplorer(false);
            Log.d("---IsNotUser---", achievement.getNameAchievement() + achievement.getIdAchievement());
        }

        achievements.addAll(remainingAchievements);

        Collections.sort(achievements, new Comparator<Achievement>() {
            @Override
            public int compare(Achievement a, Achievement b) {
                return a.getIdAchievement() - b.getIdAchievement();
            }
        });

        return achievements;
    }
}
