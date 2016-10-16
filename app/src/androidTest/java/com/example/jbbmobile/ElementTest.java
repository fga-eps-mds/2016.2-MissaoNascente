package com.example.jbbmobile;

import com.example.jbbmobile.model.Element;

import org.junit.Test;

import static org.junit.Assert.*;

public class ElementTest {

    private Element element;

    @Test
    public void testIfElementIsCreated() throws Exception{
        element = new Element(0, 1, 100, "ponto_2", "Pau-Santo", 1, "");
        assertEquals(0 , element.getIdElement());
    }

    @Test
    public void testIfElementIsCreatedWithoutIdBookandDescription() throws Exception{
        element = new Element(0, 1, 100, "ponto_2", "Pau-Santo", 1, "");
        assertEquals(0 , element.getIdElement());
    }

    @Test
    public void testIfElementIsCreatedWitOnlyIdElementQrCodeNumberElementScoreDefaultImageAndNameElement() throws Exception{
        element = new Element(0, 0, 100, "btn_google_dark_normal","beterraba");
        assertEquals(0 , element.getIdElement());
    }

    @Test
    public void testIfIdElementIsLargerThan100() throws Exception{
        try{
            element = new Element(101, 0, 100, "btn_google_dark_normal","beterraba");
        }catch (IllegalArgumentException idElementException){
            assertEquals(idElementException.getMessage(), "Invalid element's id");
        }
    }
    @Test
    public void testIfQrCodeNumberIsSmallerThanZero() throws Exception{
        try{
            element = new Element(1, -1, 100, "btn_google_dark_normal","beterraba");
        }catch (IllegalArgumentException qrCodeNumberException){
            assertEquals(qrCodeNumberException.getMessage(), "Invalid QRCode");
        }
    }

    @Test
    public void testIfNameElementLengthLessThan10() throws Exception{
        try{
            element = new Element(1, 0, 100, "btn_google_dark_normal","beterrababeterraba");
        }catch (IllegalArgumentException nameElementException){
            assertEquals(nameElementException.getMessage(), "Invalid element's name");
        }
    }
    @Test
    public void testIfIdElementIsSmallerThanZero() throws Exception{
        try{
            element = new Element(-1, 0, 100, "btn_google_dark_normal","beterraba");
        }catch (IllegalArgumentException idElementException){
            assertEquals(idElementException.getMessage(), "Invalid element's id");
        }
    }

    @Test
    public void testIfIdBookIsCreated() throws Exception{
        element = new Element(0, 1, 100, "ponto_2", "Pau-Santo", 1, "");
        assertEquals(1 , element.getIdBook());
    }

    @Test
    public void testIfIdBookIsSmallerThanZero() throws Exception{

        try {
            element = new Element(0, 1, 100, "ponto_2", "Pau-Santo", -1, "");
        }catch (IllegalArgumentException idBookException) {
            assertEquals(idBookException.getMessage(), "Invalid id book: -1");
        }
    }

    @Test
    public void testIfIdBookIsLargerThan3() throws Exception{
        try {
            element = new Element(0, 1, 100, "ponto_2", "Pau-Santo", 4, "");
        }catch (IllegalArgumentException idBookException) {
            assertEquals(idBookException.getMessage(), "Invalid id book: 4");
        }
    }

    @Test
    public void testIfElementScoreIsInvalid() throws Exception{
        try {
            element = new Element(0, 1, -1, "ponto_2", "Pau-Santo", 1, "");
        }catch (IllegalArgumentException elementScoreException) {
            assertEquals(elementScoreException.getMessage(), "Invalid element's score");
        }
    }

    @Test
    public void testIfCoordinateIsInsert() throws Exception{
        element = new Element(0, 1, 100, "ponto_2", "Pau-Santo", 1, "",15.123f,14.123f);
        assertEquals(0 , element.getIdElement());
    }

}

