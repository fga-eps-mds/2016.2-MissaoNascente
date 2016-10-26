package com.example.jbbmobile;

import android.util.Log;

import com.example.jbbmobile.model.Element;

import org.junit.Test;

import static org.junit.Assert.*;

public class ElementTest {

    private Element element;

    @Test
    public void testIfElementIsCreated() throws Exception{
        element = new Element(0, 1, 100, "ponto_2", "Pau-Santo", 1, "", -10);
        assertEquals(0 , element.getIdElement());
    }

    @Test
    public void testIfElementIsCreatedWithoutIdBookAndDescription() throws Exception{
        element = new Element(0, 1, 100, "ponto_2", "Pau-Santo", 1, "", -10);
        assertEquals(0 , element.getIdElement());
    }

    @Test
    public void testIfElementIsCreatedWitOnlyIdElementQrCodeNumberElementScoreDefaultImageAndNameElement() throws Exception{
        try {
            element = new Element(1, 0, 100, "btn_google_dark_normal", "beterraba");
            assertEquals(1, element.getIdElement());
        }catch(Exception elementException){
            elementException.printStackTrace();
        }
    }

    @Test
    public void testIfIdElementIsLargerThan100() throws Exception{
        boolean invalid = false;

        try{
            element = new Element(101, 0, 100, "btn_google_dark_normal","beterraba");
        }catch (IllegalArgumentException idElementException){
            invalid = idElementException.getMessage().equals("Invalid element's id");
        }catch (Exception idElementException){
            idElementException.printStackTrace();
        }

        assertTrue(invalid);
    }

    @Test
    public void testIfIdElementIsSmallerThanZero() throws Exception{
        boolean invalid = false;

        try{
            element = new Element(-1, 0, 100, "btn_google_dark_normal","beterraba");
        }catch (IllegalArgumentException idElementException){
            invalid = idElementException.getMessage().equals("Invalid element's id");
        }catch (Exception idElementException){
            idElementException.printStackTrace();
        }

        assertTrue(invalid);
    }

    @Test
    public void testIfQrCodeNumberIsSmallerThanZero() throws Exception{
        boolean invalid = false;

        try{
            element = new Element(1, -1, 100, "btn_google_dark_normal","beterraba");
        }catch (IllegalArgumentException qrCodeNumberException){
            invalid = qrCodeNumberException.getMessage().equals("Invalid QRCode");
        }catch (Exception qrCodeNumberException){
            qrCodeNumberException.printStackTrace();
        }

        assertTrue(invalid);
    }

    @Test
    public void testIfNameElementLengthMoreThan80() throws Exception{
        boolean invalid = false;

        try{
            element = new Element(1, 0, 100, "ponto_2","TesteTesteTesteTesteTesteTesteTesteTesteTesteTesteTesteTesteTesteTesteTesteTesteTeste");
        }catch (IllegalArgumentException nameElementException){
            invalid = nameElementException.getMessage().equals("Invalid element's name");
        }catch (Exception nameElementException){
            nameElementException.printStackTrace();
        }

        assertTrue(invalid);
    }

    @Test
    public void testIfNameElementIsNull() throws Exception{
        boolean invalid = false;

        try{
            element = new Element(1, 0, 100, "ponto_2", null);
        }catch (IllegalArgumentException nameElementException){
            invalid = nameElementException.getMessage().equals("Invalid element's name");
        }catch (Exception nameElementException){
            nameElementException.printStackTrace();
        }

        assertTrue(invalid);
    }

    @Test
    public void testIfDefaultImageIsNull() throws Exception{
        boolean invalid = false;

        try{
            element = new Element(1, 0, 100, null, "Teste");
        }catch (IllegalArgumentException defaultImageException){
            invalid = defaultImageException.getMessage().equals("Invalid element's default image");
        }catch (Exception defaultImageException){
            defaultImageException.printStackTrace();
        }

        assertTrue(invalid);
    }

    @Test
    public void testIfIdBookIsCreated() throws Exception{
        element = new Element(0, 1, 100, "ponto_2", "Pau-Santo", 1, "", -10);
        assertEquals(1 , element.getIdBook());
    }

    @Test
    public void testIfIdBookIsSmallerThanZero() throws Exception{
        boolean invalid = false;

        try {
            element = new Element(0, 1, 100, "ponto_2", "Pau-Santo", -1, "", -10);
        }catch (IllegalArgumentException idBookException) {
            invalid = idBookException.getMessage().equals("Invalid id book: -1");
        }catch (Exception idBookException){
            idBookException.printStackTrace();
        }

        assertTrue(invalid);
    }

    @Test
    public void testIfIdBookIsLargerThan3() throws Exception{
        boolean invalid = false;

        try {
            element = new Element(0, 1, 100, "ponto_2", "Pau-Santo", 4, "", -10);
        }catch (IllegalArgumentException idBookException) {
            invalid = idBookException.getMessage().equals("Invalid id book: 4");
        }catch (Exception idBookException){
            idBookException.printStackTrace();
        }

        assertTrue(invalid);
    }

    @Test
    public void testIfTextDescriptionIsNull() throws Exception{
        boolean invalid = false;

        try {
            element = new Element(0, 1, 100, "ponto_2", "Pau-Santo", 1, null, -10);
        }catch (IllegalArgumentException textDescriptionException) {
            invalid = textDescriptionException.getMessage().equals("Invalid element's description");
        }catch (Exception textDescriptionException){
            textDescriptionException.printStackTrace();
        }

        assertTrue(invalid);
    }

    @Test
    public void testIfElementScoreIsInvalid() throws Exception{
        boolean invalid = false;

        try {
            element = new Element(0, 1, -1, "ponto_2", "Pau-Santo", 1, "", -10);
        }catch (IllegalArgumentException elementScoreException) {
            invalid = elementScoreException.getMessage().equals("Invalid element's score");
        }catch (Exception elementScoreException){
            elementScoreException.printStackTrace();
        }

        assertTrue(invalid);
    }

    @Test
    public void testIfCoordinateIsInsert() throws Exception{
        element = new Element(0, 1, 100, "ponto_2", "Pau-Santo", 1, "", 15.123f, 14.123f, -10);
        assertEquals(0 , element.getIdElement());
    }

    @Test
    public void testIfEnergeticValueIsCreated() throws Exception{
        element = new Element(0, 1, 100, "ponto_2", "Pau-Santo", 1, "", -10);
        assertEquals(-10 , element.getEnergeticValue());
    }

    @Test
    public void testIfEnergeticValueSmallerThanMinus10() throws Exception{
        boolean invalid = false;

        try {
            element = new Element(0, 1, 100, "ponto_2", "Pau-Santo", 3, "", -11);
        }catch (IllegalArgumentException energeticValueException) {
            invalid = energeticValueException.getMessage().equals("Invalid Energetic Value: -11");
        }catch (Exception EnergeticValueException){
            EnergeticValueException.printStackTrace();
        }

        assertTrue(invalid);
    }

    @Test
    public void testIfEnergeticValueIsLargerThan100() throws Exception{
        boolean invalid = false;

        try {
            element = new Element(0, 1, 100, "ponto_2", "Pau-Santo", 3, "", 101);
        }catch (IllegalArgumentException energeticValueException) {
            Log.d("Test", energeticValueException.getMessage());
            invalid = energeticValueException.getMessage().equals("Invalid Energetic Value: 101");
        }catch (Exception energeticValueException){
            energeticValueException.printStackTrace();
        }

        assertTrue(invalid);
    }
}