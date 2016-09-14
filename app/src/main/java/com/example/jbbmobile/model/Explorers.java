package com.example.jbbmobile.model;


import android.util.Log;

import java.util.IllegalFormatException;
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

    public Explorers(String email){
        setEmail(email);
    }


    public  Explorers(String email,String nickname, String password){
        setNickname(nickname);
        setEmail(email);
        setPassword(password);

    }



    public  Explorers(String nickname, String email, String password,String confirmPassword){
        setNickname(nickname);
        setEmail(email);
        setPassword(password,confirmPassword);
    }

    public void googleExplorer(String nickname, String email){
        setNickname(nickname);
        setEmail(email);
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
            throw new IllegalArgumentException("nick");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(validateEmail(email)){
            this.email = email;
        }else{
            throw new IllegalArgumentException("email");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;

    }

    public void setPassword(String password,String confirmPassword) {
        if(validatePassword(password)){
            if(validateEqualsPasswords(password,confirmPassword)){
                this.password = password;
            }else{
                throw new IllegalArgumentException("confirmPassword");
            }
        }else{
            throw new IllegalArgumentException("password");
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
        if(nickname.length()>2 && nickname.length()<12){
            String expression = "[A-Z][a-zA-Z]*";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(nickname);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }

        }else{
            return false;
        }
    }
    //================================================================
    public boolean validatePassword(String password){
        if(password.length()>5 && password.length()<13){
            String expression = "^[^\\W_]{6,12}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(password);
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }

        }else{
            return false;
        }
    }
    public boolean validateEqualsPasswords(String password,String confirmPassword){
        if(password.equals(confirmPassword)){
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