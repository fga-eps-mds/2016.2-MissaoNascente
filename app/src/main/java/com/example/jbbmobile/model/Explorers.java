package com.example.jbbmobile.model;

import com.example.jbbmobile.R;

import java.net.PasswordAuthentication;
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

    public Explorers(String nickname, String email, String password){
        setNickname(nickname);
        setEmail(email);
        setPassword(password);

    }

    public String toString(){
        return getNickname()+"\n" + getEmail()+ "\n";
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
            if(validateNickname(nickname)){
                this.nickname = nickname;
            }else{

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
        if(validatePassword(password)){
            this.password = password;
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
    public boolean validatePassword(String password){
            if(password.length()>6){
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