package gov.jbb.missaonascente.model;


public class Achievement {
    public static final int PLATINUM = (1<<3);
    public static final int GOLD = (1<<2);
    public static final int SILVER = (1<<1);
    public static final int BRONZE = (1<<0);
    private int idAchievement;
    private String nameAchievement;
    private String descriptionAchievement;
    private int quantity;
    private int keys;
    private boolean isExplorer;
    private int type;



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

    public void setIsExplorer(boolean explorer) {
        isExplorer = explorer;
    }

    public void setExplorer(boolean explorer) {
        isExplorer = explorer;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
