package gov.jbb.missaonascente.model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Explorer{
    private String nickname;
    private String email;
    private String password;
    private int energy;
    private int score;
    private int position;

    public Explorer(){
    }

    public Explorer(int score, String nickname, int position) {
        this.score = score;
        this.nickname = nickname;
        this.position = position;
    }

    public Explorer(int score, String nickname){
        this.score = score;
        this.nickname = nickname;
    }

    public Explorer(String email, String password){
        setEmail(email);
        setPassword(password);
    }

    public Explorer(String email, String nickname, String password){
        setNickname(nickname);
        setEmail(email);
        setPassword(password);
    }

    public Explorer(String nickname, String email, String password, String confirmPassword) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        setNickname(nickname);
        setEmail(email);
        setPassword(password,confirmPassword);
    }

    public void googleExplorer(String nickname, String email){
        setNickname(nickname);
        setEmail(email);
    }

    public String toString(){
        return getNickname()+"\n" + getEmail()+ "\n";
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        if(validateNickname(nickname))
            this.nickname = nickname;
        else
            throw new IllegalArgumentException("Invalid nick");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(validateEmail(email))
            this.email = email.toLowerCase();
        else
            throw new IllegalArgumentException("Invalid email");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String cryptographyPassword (String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algorithm.digest(password.getBytes("UTF-8"));

        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }

        String passwordHexadecimal = hexString.toString();

        password = passwordHexadecimal;

        return password;
    }

    public void setPassword(String password,String confirmPassword) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if(validatePassword(password)){
            if(validateEqualsPasswords(password,confirmPassword))
                this.password = cryptographyPassword(password);
            else
                throw new IllegalArgumentException("Invalid confirmPassword");
        }else{
            throw new IllegalArgumentException("Invalid password");
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void updateScore(int elementScore){
        setScore(getScore()+elementScore);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private boolean validateNickname(String nickname){
        if(nickname.length()>2 && nickname.length()<11){
            String expression = "[a-zA-Z0-9]+";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(nickname);
            if (matcher.matches())
                return true;
            else
                return false;
        }else{
            return false;
        }
    }

    private boolean validatePassword(String password){
        if(password.length()>5 && password.length()<13){
            String expression = "^[^\\W_]{6,12}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(password);
            return matcher.matches();
        }else{
            return false;
        }
    }

    public boolean validateEqualsPasswords(String password, String confirmPassword){
        return password.equals(confirmPassword);
    }

    private boolean validateEmail(String email) {
        if (email.length() > 3) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches())
                return true;
            else
                return false;
        }else {
            return false;
        }
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        if(validateEnergy(energy))
            this.energy= energy;
        else
            throw new IllegalArgumentException("Invalid Energy");
    }

    private boolean validateEnergy(int energy){
        return energy >= 0;
    }
}