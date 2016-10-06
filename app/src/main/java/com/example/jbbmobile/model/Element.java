package com.example.jbbmobile.model;

import android.util.Log;

import java.util.List;

public class Element {
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

    public Element(){
    }

    public Element(int idElement){
        setIdElement(idElement);
    }

    public Element(int idElement, int qrCodeNumber, int elementScore, String defaultImage, String nameElement, String userImage){
        validateID(idElement);
        validateQRCode(qrCodeNumber);
        validateScore(elementScore);
        validateDefaultImage(defaultImage);
        validateDefaultImage(nameElement);
        setUserImage(userImage);
    }

    public Element(int idElement, int qrCodeNumber, int elementScore, String defaultImage, String nameElement, String userImage, int idBook, int idInformation, List<String> description) {
        validateID(idElement);
        validateQRCode(qrCodeNumber);
        validateScore(elementScore);
        validateDefaultImage(defaultImage);
        validateNameElement(nameElement);
        setUserImage(userImage);
        validateIdBook(idBook);
        validateInformationId(idInformation);
        validateDescription(description);
    }

    public Element(int idElement, int qrCodeNumber, int elementScore, String defaultImage, String nameElement){
        validateID(idElement);
        validateQRCode(qrCodeNumber);
        validateScore(elementScore);
        validateDefaultImage(defaultImage);
        validateNameElement(nameElement);
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

    private void validateID(int idElement){
        if(idElement < 0 || idElement > 100)
            throw new IllegalArgumentException("Invalid element's id");
        else
            setIdElement(idElement);

    }

    private void validateQRCode(int QRCODE){
        if(QRCODE < 0)
            throw new IllegalArgumentException("Invalid QRCode");
        else
            setQrCodeNumber(QRCODE);
    }

    private void validateDefaultImage(String defaultImage){
        if(defaultImage == null)
            throw new IllegalArgumentException("Invalid element's default image");
        else
            setDefaultImage(defaultImage);
    }

    private void validateNameElement(String nameElement){
        if(nameElement == null || nameElement.length() > 80)
            throw new IllegalArgumentException("Invalid element's name");
        else
            setNameElement(nameElement);
    }

    private void validateScore(int elementScore){
        if(elementScore <= 0)
            throw new IllegalArgumentException("Invalid element's score");
        else
            setElementScore(elementScore);
    }

    private void validateInformationId(int idInformation){
        if(idInformation < 0 || idInformation > 100)
            throw new IllegalArgumentException("Invalid id Information");
        else
            setIdInformation(idInformation);
    }

    private void validateDescription(List<String> description){
        if(description == null)
            throw new IllegalArgumentException("Invalid element's description");
        else
            setDescription(description);
    }

    private void validateIdBook(int idBook){
        if(idBook <= 0 || idBook > 3){
            throw new IllegalArgumentException("Invalid id book: " + idBook);
        }
        else
            setIdBook(idBook);
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
}
