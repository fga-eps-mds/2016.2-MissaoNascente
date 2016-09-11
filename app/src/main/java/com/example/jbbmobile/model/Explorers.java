package com.example.jbbmobile.model;

import android.content.Context;
import android.widget.Toast;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.Register;
import com.example.jbbmobile.view.RegisterScreenActivity;

import java.net.PasswordAuthentication;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ronyell on 09/09/16.
 */
public class Explorers {
    private String nickname;
    private String email;
    private String password;
    private String confirmPassword;
    private int energy;
    private int[] gatheredElement;
    private int[] gatheredAchievement;

    public Explorers(){
    }

    public Explorers(String email, String password){
        setEmail(email);
        setPassword(password);
    }

    public  Explorers(String nickname, String email, String password,String confirmPassword){
        setNickname(nickname);
        setEmail(email);
        setPassword(password,confirmPassword);


    }

    public String toString(){
        return getNickname()+"\n" + getEmail()+ "\n";
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {

            if (validateNickname(nickname)) {
                this.nickname = nickname;
                //return true;
            }
            else{
               Toast.makeText(RegisterScreenActivity.registerScreenContext,"ERRO NO SET",Toast.LENGTH_SHORT).show();
                //throw new IllegalArgumentException();
                //return false;
            }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(validateEmail(email)){
            this.email = email;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;

    }

    public void setPassword(String password,String confirmPassword) {
        if(validatePassword(password,confirmPassword)){
            this.password = password;
            Toast.makeText(RegisterScreenActivity.registerScreenContext,"SENHA CERTA",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(RegisterScreenActivity.registerScreenContext,"ERRO NA SENHA",Toast.LENGTH_SHORT).show();
        }
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int[] getGatheredElement() {
        return gatheredElement;
    }

    public void setGatheredElement(int[] gatheredElement) {
        this.gatheredElement = gatheredElement;
    }

    public int[] getGatheredAchievement() {
        return gatheredAchievement;
    }

    public void setGatheredAchievement(int[] gatheredAchievement) {
        this.gatheredAchievement = gatheredAchievement;
    }


    ///***************** VALIDACAO DOS DADOS *********************

    //=============================================================
    public boolean validateNickname(String nickname){
            if(nickname.length()>5 && nickname.length()<13){
                return true;
            }else{
                return false;
            }
    }
    //================================================================
    public boolean validatePassword(String password,String confirmPassword){
            if(password.length()>6 && password.equals(confirmPassword)){
                    return true;
                }else{
                    return false;
            }
    }
    //=================================================================
    public boolean validateEmail(String email) {
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        }else {
            return false;
        }
    }
    //====================================================================

}