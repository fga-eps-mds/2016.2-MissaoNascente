package com.example.jbbmobile.model;

import java.util.Map;

public class Question {

    private int idQuestion;
    private String description;
    private Map<String, String> alternatives;
    private String correctAnswer;
    private int alternativeQuantity;

    public Question(){

    }

    public Question(int idQuestion, String description, Map<String, String> alternatives, String correctAnswer, int alternativeQuantity) {
        setIdQuestion(idQuestion);
        setDescription(description);
        setAlternatives(alternatives);
        setCorrectAnswer(correctAnswer);
        setAlternativeQuantity(alternativeQuantity);
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQUestion) {
        if(validateId(idQUestion))
            this.idQuestion = idQUestion;
        else
            throw new IllegalArgumentException("negative Question id");
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

    public Map<String, String> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(Map<String, String> alternatives) {
        if(validateAlternatives(alternatives))
            this.alternatives = alternatives;
        else
            throw new IllegalArgumentException("null alternatives");
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

    public int getAlternativeQuantity() {
        return alternativeQuantity;
    }

    public void setAlternativeQuantity(int alternativeQuantity) {
        this.alternativeQuantity = alternativeQuantity;
    }

    private boolean validateId(int idQUestion){
        return idQUestion >= 0;
    }

    private boolean validateDescription(String description){
        return description != "";
    }

    private boolean validateCorrectAnswer(String correctAnswer){
        return correctAnswer != "";
    }

    private boolean validateAlternatives(Map<String, String> alternatives){
        boolean isBlankAlternative;
        String blankAlternative = "";

        if (alternatives == null)
            return false;
        else {
            isBlankAlternative = false;

            for (Map.Entry<String, String> alternative : alternatives.entrySet()) {
                if(alternative.getValue() == "") {
                    isBlankAlternative = true;
                    blankAlternative = alternative.getKey();
                    break;
                }
            }

            if (isBlankAlternative)
                throw new IllegalArgumentException("blank '" + blankAlternative + "' alternative");
            else
                return true;
        }
    }

}
