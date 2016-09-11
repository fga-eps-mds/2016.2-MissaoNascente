package com.example.jbbmobile.model;

/**
 * Created by ronyell on 09/09/16.
 */
public class Explorers {
    private String nickname;
    private String email;
    private String password;
    private int energy;
    private int[] gatheredElement;
    private int[] gatheredAchievement;

    public Explorers(){
    }

    public Explorers(String email, String password){
        setEmail(email);
        setPassword(password);
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
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
