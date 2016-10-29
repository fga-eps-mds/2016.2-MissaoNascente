package jbbmobile.example.com.elementparser;

import java.util.Map;

public class Question {

    private int id;
    private String description;
    private Map<String, String> alternatives;
    private String correctAnswer;
    private int alternativeQuantity;

    public Question(int id, String description, Map<String, String> alternatives, String correctAnswer, int alternativeQuantity) {
        setId(id);
        setDescription(description);
        setAlternatives(alternatives);
        setCorrectAnswer(correctAnswer);
        setAlternativeQuantity(alternativeQuantity);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(validateId(id))
            this.id = id;
        else
            throw new IllegalArgumentException("negative id");
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

    private boolean validateId(int id){
        return id >= 0;
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
