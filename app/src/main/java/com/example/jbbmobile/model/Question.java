package com.example.jbbmobile.model;

import java.util.List;

public class Question {

    private int idQuestion;
    private String description;
    private String correctAnswer;
    private int alternativeQuantity;
    private List<Alternative> alternativeList;
    private int version;

    public Question(){}

    public Question(int idQuestion, String description, String correctAnswer, int alternativeQuantity) {
        setIdQuestion(idQuestion);
        setDescription(description);
        setCorrectAnswer(correctAnswer);
        setAlternativeQuantity(alternativeQuantity);
    }

    public Question(int idQuestion, String description, String correctAnswer, int alternativeQuantity, int version) {
        setIdQuestion(idQuestion);
        setDescription(description);
        setCorrectAnswer(correctAnswer);
        setAlternativeQuantity(alternativeQuantity);
        setVersion(version);
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        if(validateId(idQuestion))
            this.idQuestion = idQuestion;
        else
            throw new IllegalArgumentException("negative Question id");
    }

    private boolean validateId(int idQuestion){
        return idQuestion > 0;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(validateDescription(description))
            this.description = description;
        else
            throw new IllegalArgumentException("blank description");
    }

    private boolean validateDescription(String description){
        return description != "";
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        if(validateCorrectAnswer(correctAnswer))
            this.correctAnswer = correctAnswer;
        else
            throw new IllegalArgumentException("blank correct answer");
    }

    private boolean validateCorrectAnswer(String correctAnswer){
        return correctAnswer != "";
    }

    public int getAlternativeQuantity() {
        return alternativeQuantity;
    }

    public void setAlternativeQuantity(int alternativeQuantity) {
        if(validateAlternativeQuantity(alternativeQuantity)){
            this.alternativeQuantity = alternativeQuantity;
        } else{
            throw new IllegalArgumentException("invalid alternative quantity");
        }
    }

    private boolean validateAlternativeQuantity(int alternativeQuantity){
        return alternativeQuantity > 1;
    }

    public List<Alternative> getAlternativeList() {
        return alternativeList;
    }

    public void setAlternativeList(List<Alternative> alternativeList) {
        if (validateAlternativeList(alternativeList)) {
            this.alternativeList = alternativeList;
        }else{
            throw new IllegalArgumentException("insufficient number of alternatives");
        }
    }

    private boolean validateAlternativeList(List<Alternative> alternativeList){
        return alternativeList.size() > 1;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}