package gov.jbb.missaonascente.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.preference.PreferenceManager;
import android.util.Log;

import gov.jbb.missaonascente.dao.ExplorerDAO;
import gov.jbb.missaonascente.dao.RegisterRequest;
import gov.jbb.missaonascente.model.Explorer;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static android.content.Context.MODE_PRIVATE;

public class RegisterExplorerController {

    private Explorer explorer;
    private boolean response;
    private boolean action = false;
    public final String EXPLORER_REGISTER = "ifIsFirstExplorerLogin";
    public final String FIRST_EXPLORER_LOGIN = "firstExplorerLogin";

    public RegisterExplorerController(){}

    public void register(String nickname, String email, String password, String confirmPassword, final Context context)throws SQLiteConstraintException{
        try {
            setAction(false);
            setExplorers(new Explorer(nickname, email, password, confirmPassword));
            ExplorerDAO explorerDAO = new ExplorerDAO(context);

            int errorRegister = -1;

            if (explorerDAO.insertExplorer(getExplorer()) == errorRegister) {
                throw new SQLiteConstraintException();
            }

            RegisterRequest registerRequest = new RegisterRequest(getExplorer().getNickname(),
                    getExplorer().getPassword(),
                    getExplorer().getEmail());

            registerRequest.request(context, new RegisterRequest.Callback() {
                @Override
                public void callbackResponse(boolean success) {
                    setResponse(success);
                    if(!success){
                        Log.d("Deleting all explorers", "Delete");
                        ExplorerDAO database = new ExplorerDAO(context);
                        database.deleteExplorer(explorer);
                    }else{
                        SharedPreferences preferencesRegister = context.getSharedPreferences(FIRST_EXPLORER_LOGIN, 0);
                        SharedPreferences.Editor editor = preferencesRegister.edit();
                        editor.putBoolean(EXPLORER_REGISTER, true);
                        editor.apply();
                    }
                    setAction(true);
                }
            });


        }catch (IllegalArgumentException exception){

            if((exception.getLocalizedMessage()).equals("Invalid nick")){
                throw new IllegalArgumentException("wrongNickname");
            }
            if((exception.getLocalizedMessage()).equals("Invalid password")){
                throw new IllegalArgumentException("wrongPassword");
            }
            if((exception.getLocalizedMessage()).equals("Invalid confirmPassword")){
                throw new IllegalArgumentException("wrongConfirmPassword");
            }
            if((exception.getLocalizedMessage()).equals("Invalid email")){
                throw new IllegalArgumentException("wrongEmail");
            }
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void updateExplorerSharedPreference(Context context){
        SharedPreferences preferencesRegister = context.getSharedPreferences(FIRST_EXPLORER_LOGIN, 0);
        SharedPreferences.Editor editor = preferencesRegister.edit();
        editor.putBoolean(EXPLORER_REGISTER, false);
        editor.apply();
    }

    public void register(String nickname, String email, Context context) {
        setExplorers(new Explorer());
        getExplorer().googleExplorer(nickname, email);
        ExplorerDAO explorerDAO = new ExplorerDAO(context);

        try {
            explorerDAO.insertExplorer(getExplorer());
        } catch (SQLiteConstraintException exception){
            exception.getMessage();
        }
    }

    public boolean isAction() {
        return action;
    }

    private void setAction(boolean action) {
        this.action = action;
    }

    private void setResponse(boolean response) {
        this.response = response;
    }

    public boolean isResponse() {
        return response;
    }

    public Explorer getExplorer() {
        return explorer;
    }

    private void setExplorers(Explorer explorer) {
        this.explorer = explorer;
    }
}
