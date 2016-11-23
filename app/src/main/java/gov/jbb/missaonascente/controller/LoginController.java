package gov.jbb.missaonascente.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import gov.jbb.missaonascente.dao.ExplorerDAO;
import gov.jbb.missaonascente.dao.LoginRequest;
import gov.jbb.missaonascente.model.Explorer;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import static android.content.Context.MODE_PRIVATE;

public class LoginController {
    private Explorer explorer;
    private boolean action = false;
    private boolean response;
    private static final String PREF_NAME = "MainActivityPreferences";

    public LoginController() {
        explorer = new Explorer();
    }

    //LoginController to normal register Accounts

    public boolean realizeLogin(String email, String password, Context context) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        ExplorerDAO db = new ExplorerDAO(context);
        Explorer explorer = new Explorer();
        String passwordDigest;
        passwordDigest = explorer.cryptographyPassword(password);
        explorer = db.findExplorerLogin(email,passwordDigest);
        db.close();

        if (explorer == null || explorer.getEmail() == null || explorer.getPassword() == null) {
            return false;
        }
        saveFile(explorer.getEmail(), context);
        return true;
    }

    public void doLogin(final String email, String password, final Context context){
        Explorer explorerFromLogin = new Explorer();
        explorerFromLogin.setEmail(email);

        try {
            explorerFromLogin.setPassword(password, password);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        LoginRequest loginRequest = new LoginRequest(explorerFromLogin.getEmail(), explorerFromLogin.getPassword());
        loginRequest.request(context, new LoginRequest.Callback() {
            @Override
            public void callbackResponse(boolean response) {
                setResponse(response);
                if(response){
                    saveFile(email, context);

                    /*MainController mainController = new MainController();
                    mainController.checkIfUpdateIsNeeded(context);*/

                    //----------------------------------------------------------------
                    new ExplorerController().updateElementExplorerTable(context, email);
                    new AchievementController(context).updateAchievementExplorerTable(context, email);
                }
                setAction(true);
            }
        });

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

    //LoginController to Google Accounts
    public boolean realizeLogin(String email, Context context) throws IOException {
        ExplorerDAO dataBase = new ExplorerDAO(context);
        Explorer explorer = dataBase.findExplorer(email);
        dataBase.close();

        if (explorer == null || explorer.getEmail() == null) {
            return false;
        }

        saveFile(explorer.getEmail(), context);
        return true;
    }

    private void saveFile(String email, Context context) {
        Log.d("Entra aqui?", "Sim");
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.apply();
    }

    public void deleteFile(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("email");
        editor.apply();
    }

    public void loadFile(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String email;

        Log.i("A preferencia existe", "Não");

        if ((email = sharedPreferences.getString("email", null)) != null) {
            Log.i("A preferencia existe", "Sim");
            Log.d("Email", email);
            ExplorerDAO dataBase = new ExplorerDAO(context);
            Explorer explorer = dataBase.findExplorer(email);
            dataBase.close();
            this.explorer = new Explorer(explorer.getEmail(), explorer.getNickname(), explorer.getPassword());
            this.explorer.setScore(explorer.getScore());
        }

    }


    public void checkIfGoogleHasGooglePassword() {
        try {
            getExplorer().getPassword().equals(null);
        } catch (NullPointerException exception) {
            throw exception;
        }
    }

    public boolean checkIfUserHasGoogleNickname() {
        return getExplorer().getNickname().equals("Placeholder");
    }

    public Explorer getExplorer() {
        return explorer;
    }

    public boolean remainLogin() {
        return getExplorer().getEmail() != null;
    }
}