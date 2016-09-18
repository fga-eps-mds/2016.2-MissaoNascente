package com.example.jbbmobile.model;

import android.util.Log;

import java.util.List;

public class Elements {
    private int idElement;
    private int qrCodeNumber;
    private int elementScore;
    private String defaultImage;
    private String nameElement;
    private String userImage;
    private int idBook;
    private int idInformation;
    private List<String> description;
    private String descriptionString;

    public Elements(){


    }
    public Elements(int idElement){
        setIdElement(idElement);

    }

    public Elements(int idElement, int qrCodeNumber, int elementScore, String defaultImage, String nameElement, String userImage){
        setIdElement(idElement);
        setQrCodeNumber(qrCodeNumber);
        setElementScore(elementScore);
        setDefaultImage(defaultImage);
        setNameElement(nameElement);
        setUserImage(userImage);
    }

    public Elements(int idElement, int qrCodeNumber, int elementScore, String defaultImage, String nameElement, String userImage, int idBook, int idInformation, List<String> description) {
        setIdElement(idElement);
        setQrCodeNumber(qrCodeNumber);
        setElementScore(elementScore);
        setDefaultImage(defaultImage);
        setNameElement(nameElement);
        setUserImage(userImage);
        setIdBook(idBook);
        setIdInformation(idInformation);
        setDescription(description);
    }

    public Elements(int idElement, int qrCodeNumber, int elementScore, String defaultImage, String nameElement){
        setIdElement(idElement);
        setQrCodeNumber(qrCodeNumber);
        setElementScore(elementScore);
        setDefaultImage(defaultImage);
        setNameElement(nameElement);
    }
    public int getIdElement() {
        return idElement;
    }

    public void setIdElement(int idElement) {
        this.idElement = idElement;
    }

    public int getQrCodeNumber() {
        return qrCodeNumber;
    }

    public void setQrCodeNumber(int qrCodeNumber) {
        this.qrCodeNumber = qrCodeNumber;
    }

    public int getElementScore() {
        return elementScore;
    }

    public void setElementScore(int elementScore) {
        this.elementScore = elementScore;
    }

    public String getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public String getNameElement() {
        return nameElement;
    }

    public void setNameElement(String nameElement) {
        this.nameElement = nameElement;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public int getIdInformation() {
        return idInformation;
    }

    public void setIdInformation(int idInformation) {
        this.idInformation = idInformation;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    private void concatenateStringDescription(){
        String description = "";
        for (String s : getDescription()) {
            description += s + " ";
        }
        setDescriptionString(description);
        Log.i("Teste", description);
    }

    public String getDescriptionString() {
        concatenateStringDescription();
        return descriptionString;
    }

    public void setDescriptionString(String descriptionString) {
        this.descriptionString = descriptionString;
    }
}
