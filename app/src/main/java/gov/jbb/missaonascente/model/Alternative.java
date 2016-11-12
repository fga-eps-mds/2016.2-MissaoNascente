package gov.jbb.missaonascente.model;

public class Alternative {
    private int idAlternative;
    private String alternativeLetter;
    private String alternativeDescription;
    private int idQuestion;

    public Alternative(int idAlternative, String alternativeLetter, String alternativeDescription,
                       int idQuestion) {
        setIdAlternative(idAlternative);
        setAlternativeDescription(alternativeDescription);
        setIdQuestion(idQuestion);
        setAlternativeLetter(alternativeLetter);
    }

    public Alternative() {}

    public int getIdAlternative() {
        return idAlternative;
    }

    public void setIdAlternative(int idAlternative) {
        this.idAlternative = idAlternative;
    }

    public String getAlternativeLetter() {
        return alternativeLetter;
    }

    private void setAlternativeLetter(String alternativeLetter) {
        this.alternativeLetter = alternativeLetter;
    }

    public String getAlternativeDescription() {
        return alternativeDescription;
    }

    private void setAlternativeDescription(String alternativeDescription) {
        this.alternativeDescription = alternativeDescription;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    private void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }
}