package com.example.jbbmobile.model;



import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Explorer {
    private String nickname;
    private String email;
    private String password;
    private String confirmPassword;
    private int energy;
    private int[] gatheredElement;
    private int[] gatheredAchievement;

    public Explorer(){
    }

    public Explorer(String email, String password){
        setEmail(email);
        setPassword(password);
    }

    public Explorer(String email){
        setEmail(email);
    }

    public Explorer(String email, String nickname, String password){
        setNickname(nickname);
        setEmail(email);
        setPassword(password);
    }

    public Explorer(String nickname, String email, String password, String confirmPassword) throws UnsupportedEncodingException, NoSuchAlgorithmException {
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
            String aux = email.toLowerCase();
            this.email = aux;
        }else{
            throw new IllegalArgumentException("email");
        }
    }

    public String getPassword() {
        Log.i ("Get password", password);
        return password;

    }

    public void setPassword(String password) {
        this.password = password;

    }

    public String cryptographyPassword (String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Log.i("test1", password);
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algorithm.digest(password.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }
        String senhahex = hexString.toString();
        Log.i("==================", senhahex);
        password = senhahex;
        Log.i("test2", password);
        return password;
    }

    public void setPassword(String password,String confirmPassword) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if(validatePassword(password)){
            if(validateEqualsPasswords(password,confirmPassword)){
                this.password = cryptographyPassword(password);
                Log.i ("'test3", password);
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


    ///***************** DATA VALIDATION *********************

    //=============================================================
    public boolean validateNickname(String nickname){
        if(nickname.length()>2 && nickname.length()<11){
            String expression = "[a-zA-Z0-9]+";
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
        Log.i ("PASSWORD 1", password);
        Log.i ("PASSWORD 2", confirmPassword);
        if(password.equals(confirmPassword)){
            return true;
        }else{
            Log.i ("False", password);
            return false;
        }
    }
    //=================================================================
    public boolean validateEmail(String email) {
        if (email.length() > 3) {
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