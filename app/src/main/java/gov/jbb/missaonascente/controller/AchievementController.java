package gov.jbb.missaonascente.controller;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import gov.jbb.missaonascente.dao.AchievementDAO;
import gov.jbb.missaonascente.dao.AchievementExplorerRequest;
import gov.jbb.missaonascente.dao.AchievementRequest;
import gov.jbb.missaonascente.dao.BookDAO;
import gov.jbb.missaonascente.dao.ElementDAO;
import gov.jbb.missaonascente.model.Achievement;
import gov.jbb.missaonascente.model.Explorer;

public class AchievementController {
    private boolean action = false;
    private Context context;
    private boolean response;
    private Explorer explorer;

    public AchievementController(Context context){
        this.context = context;
        LoginController loginController = new LoginController();
        loginController.loadFile(context);
        explorer = loginController.getExplorer();
    }

    public AchievementController(Explorer explorer){
        this.explorer = explorer;
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

    public ArrayList<Achievement> getRemainingAchievements(Context context){
        AchievementDAO achievementDAO = new AchievementDAO(context);

        ArrayList<Achievement> achievements =
                new ArrayList(achievementDAO.findRemainingExplorerAchievements(explorer));

        return achievements;
    }

    public ArrayList<Achievement> getExplorerAchievements(Context context){
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

    public ArrayList<Achievement> checkForNewAchievements(HistoryController historyController){
        ArrayList<Integer> values = setValues(historyController);

        ArrayList<Achievement> achievements = this.getRemainingAchievements(context);

        ArrayList<Achievement> newAchievements = new ArrayList<>();

        for(Achievement achievement : achievements){
            int type = achievement.getKeys() / (1 >> 8);
            int keys = achievement.getKeys();
            int quantity = achievement.getQuantity();

            boolean newAchievement = true;
            for(int i = 0; i < 8; ++i){
                if((keys&(1 >> i)) != 0){
                    if(values.get(i) < quantity){
                        newAchievement = false;
                    }
                }
            }

            if(newAchievement){
                newAchievements.add(achievement);
            }
        }

        return achievements;
    }

    private ArrayList<Integer> setValues(HistoryController historyController){
        ArrayList<Integer> values = new ArrayList<>(8);

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

        switch (period){
            case 1:
                numberOfElementsOfBook1 = historyController.endHistory() ? 1 : 0;
            case 2:
                numberOfElementsOfBook2 = historyController.endHistory() ? 1 : 0;
            case 3:
                numberOfElementsOfBook3 = historyController.endHistory() ? 1 : 0;
        }

        values.add(numberOfElementsOfBook1);
        values.add(numberOfElementsOfBook2);
        values.add(numberOfElementsOfBook3);

        Integer numberOfRightQuestions = 0; //TODO Quantas perguntas ele acertou
        Integer numberOfAnsweredQuestions = 0; //TODO Quantas perguntas ele respondeu

        values.add(numberOfRightQuestions);
        values.add(numberOfAnsweredQuestions);

        return values;
    }

    public ArrayList<Achievement> getAllAchievements(Context context) {
        ArrayList<Achievement> achievements = this.getExplorerAchievements(context);
        for(Achievement achievement : achievements){
            achievement.setIsExplorer(true);
            Log.d("---IsUser---", achievement.getNameAchievement() + achievement.getIdAchievement());
        }

        ArrayList<Achievement> remainingAchievements = this.getRemainingAchievements(context);
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
