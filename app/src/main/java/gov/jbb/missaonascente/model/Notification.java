package gov.jbb.missaonascente.model;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.IllegalFormatException;

public class Notification {

    private String title;
    private String description;
    private String date;
    private int idNotification;

    public Notification(){

    }

    public Notification( int idNotification, String title, String description, String date){
        setTitle(title);
        setDescription(description);
        setDate(date);
        setIdNotification(idNotification);
    }

    public boolean validateTitle(String title){
        if (title.length() > 0 && title.length() <= 80){
            return true;
        }else{
            return false;
        }
    }

    public boolean validateDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        try {
            format.parse(date);
        } catch(ParseException e) {
            return false;
        }
        return true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (validateTitle(title)) {
            this.title = title;
        } else {
            throw new IllegalArgumentException("Invalid title");
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        if (validateDate(date)) {
            this.date = date;
        } else {
            throw new IllegalArgumentException("Invalid date");
        }
    }

    public int getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(int idNotification) {
        this.idNotification = idNotification;
    }

    public String toString(){
        return idNotification+" "+title+" "+description+" "+date;
    }
}

