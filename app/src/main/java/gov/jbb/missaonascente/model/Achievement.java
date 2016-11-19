package gov.jbb.missaonascente.model;


public class Achievement {
    private int idAchievement;
    private String nameAchievement;
    private String descriptionAchievement;
    private int quantity;
    private int keys;
    private boolean isExplorer;

    public Achievement(){

    }

    public Achievement(int idAchievement){
        setIdAchievement(idAchievement);
    }

    public Achievement(int idAchievement, String nameAchievement, String descriptionAchievement, int quantity, int keys){
        setIdAchievement(idAchievement);
        setNameAchievement(nameAchievement);
        setDescriptionAchievement(descriptionAchievement);
        setQuantity(quantity);
        setKeys(keys);
    }

    public int getIdAchievement() {
        return idAchievement;
    }

    public void setIdAchievement(int idAchievement) {
        this.idAchievement = idAchievement;
    }

    public String getNameAchievement() {
        return nameAchievement;
    }

    public void setNameAchievement(String nameAchievement) {
        this.nameAchievement = nameAchievement;
    }

    public String getDescriptionAchievement() {
        return descriptionAchievement;
    }

    public void setDescriptionAchievement(String descriptionAchievement) {
        this.descriptionAchievement = descriptionAchievement;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getKeys() {
        return keys;
    }

    public void setKeys(int keys) {
        this.keys = keys;
    }

    public boolean isExplorer() {
        return isExplorer;
    }

    public void setExplorer(boolean explorer) {
        isExplorer = explorer;
    }
}
