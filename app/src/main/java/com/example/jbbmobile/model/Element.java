package com.example.jbbmobile.model;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Element {
    private int idElement;
    private String nameElement;
    private String defaultImage;
    private int elementScore;
    private int qrCodeNumber;
    private String textDescription;
    private String userImage;
    private int idBook;
    private String catchDate;
    private float southCoordinate;
    private float westCoordinate;

    public Element(){
    }

    public Element(int idElement, int qrCodeNumber, int elementScore, String defaultImage, String nameElement) {
        setUserImage("");
        validateID(idElement);
        validateQRCode(qrCodeNumber);
        validateScore(elementScore);
        validateDefaultImage(defaultImage);
        validateNameElement(nameElement);
        setDate();
    }

    public Element(int idElement, int qrCodeNumber, int elementScore, String defaultImage, String nameElement, int idBook, String textDescription){
        this(idElement, qrCodeNumber, elementScore, defaultImage, nameElement);
        validateIdBook(idBook);
        validateTextDescription(textDescription);
        setDate();
    }

    public Element(int idElement, int qrCodeNumber, int elementScore, String defaultImage, String nameElement, int idBook, String textDescription,float southCoordinate, float westCoordinate){
        this(idElement, qrCodeNumber, elementScore, defaultImage, nameElement, idBook, textDescription);
        setSouthCoordinate(southCoordinate);
        setWestCoordinate(westCoordinate);
    }

    private void validateID(int idElement){
        if(idElement < 0 || idElement > 100)
            throw new IllegalArgumentException("Invalid element's id");
        else
            setIdElement(idElement);
    }

    private void validateQRCode(int qrCodeNumber){
        if(qrCodeNumber < 0)
            throw new IllegalArgumentException("Invalid QRCode");
        else
            setQrCodeNumber(qrCodeNumber);
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

    private void validateTextDescription(String textDescription){
        if(textDescription == null)
            throw new IllegalArgumentException("Invalid element's description");
        else
            setTextDescription(textDescription);
    }

    private void validateIdBook(int idBook){
        if(idBook <= 0 || idBook > 3)
            throw new IllegalArgumentException("Invalid id book: " + idBook);
        else
            setIdBook(idBook);
    }

    public void setDate(){
        DateFormat formatBR = DateFormat.getDateInstance(DateFormat.LONG, new Locale("pt", "BR"));
        Date today = Calendar.getInstance().getTime();

        catchDate = formatBR.format(today);
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

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getTextDescription() {
        return textDescription;
    }

    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }

    public String getCatchDate() {
        return catchDate;
    }

    public void setCatchDate(String catchDate) {
        this.catchDate = catchDate;
    }

    public float getSouthCoordinate() {
        return southCoordinate;
    }

    public void setSouthCoordinate(float southCoordinate) {
        this.southCoordinate = southCoordinate;
    }

    public float getWestCoordinate() {
        return westCoordinate;
    }

    public void setWestCoordinate(float westCoordinate) {
        this.westCoordinate = westCoordinate;
    }
}
