package com.example.jbbmobile.model;

import java.util.List;

/**
 * Created by ronyell on 14/09/16.
 */
public class Element {
    private int idElement;
    private int qrCodeNumber;
    private int elementScore;
    private String defaultImage;
    private String nameElement;
    private String userImage;
    private List elementInformation;


    public Element(){


    }
    public Element(int idElement){
        setIdElement(idElement);

    }

    public Element(int idElement, int qrCodeNumber, int elementScore, String defaultImage,String nameElement, String userImage){
        setIdElement(idElement);
        setQrCodeNumber(qrCodeNumber);
        setElementScore(elementScore);
        setDefaultImage(defaultImage);
        setNameElement(nameElement);
        setUserImage(userImage);
    }

    public Element(int idElement, int qrCodeNumber, int elementScore, String defaultImage,String nameElement){
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

    public void setElementInformation(List elementInformation) {
        this.elementInformation = elementInformation;
    }

    public List getElementInformation() {
        return elementInformation;
    }
}
