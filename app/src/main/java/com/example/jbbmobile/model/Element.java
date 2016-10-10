package com.example.jbbmobile.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public Element(){
    }

    public Element(int idElement, int qrCodeNumber, int elementScore, String defaultImage, String nameElement, String userImage, int idBook, String textDescription) {
        validateID(idElement);
        validateQRCode(qrCodeNumber);
        validateScore(elementScore);
        validateDefaultImage(defaultImage);
        validateNameElement(nameElement);
        setUserImage(userImage);
        validateIdBook(idBook);
        validateTextDescription(textDescription);
    }

    public Element(int idElement, int qrCodeNumber, int elementScore, String defaultImage, String nameElement){
        validateID(idElement);
        validateQRCode(qrCodeNumber);
        validateScore(elementScore);
        validateDefaultImage(defaultImage);
        validateNameElement(nameElement);
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
        if(idBook <= 0 || idBook > 3){
            throw new IllegalArgumentException("Invalid id book: " + idBook);
        }
        else
            setIdBook(idBook);
    }

    public void formatDate() throws ParseException {
        DateFormat formatUS = new SimpleDateFormat("yyyy-mm-dd");
        Date date = formatUS.parse(catchDate);

        DateFormat formatBR = new SimpleDateFormat("dd/mm/yyyy");
        String dateFormated = formatBR.format(date);
        setCatchDate(dateFormated);
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
}
