package com.example.jbbmobile;

import com.example.jbbmobile.model.Element;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by igor on 19/09/16.
 */
public class ElementTest {

    private Element element;

    @Test
    public void testIfElementIsCreated() throws Exception{
        List<String> description = new ArrayList<String>();
        description.add("Description.");
        element = new Element(0, 0, 100, "btn_google_dark_normal","beterraba", "pequi", 0, 0, description);
        assertEquals(0 , element.getIdElement());
    }

    @Test
    public void testIfElementIsCreatedWithoutIdBookIdInformationAndDescription() throws Exception{
        element = new Element(0, 0, 100, "btn_google_dark_normal","beterraba", "pequi");
        assertEquals(0 , element.getIdElement());
    }

    @Test
    public void testIfElementIsCreatedWitOnlyIdElementQrCodeNumberElementScoreDefaultImageAndNameElement() throws Exception{
        element = new Element(0, 0, 100, "btn_google_dark_normal","beterraba");
        assertEquals(0 , element.getIdElement());
    }

    @Test
    public void testIfElementIsCreatedWitOnlyIdElement() throws Exception{
        element = new Element(0);
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
        List<String> description = new ArrayList<String>();
        description.add("Description.");
        element = new Element(0, 0, 100, "btn_google_dark_normal","beterraba", "pequi", 0, 0, description);
        assertEquals(0 , element.getIdBook());
    }

    @Test
    public void testIfIdInformationIsCreated() throws Exception{
        List<String> description = new ArrayList<String>();
        description.add("Description.");
        element = new Element(0, 0, 100, "btn_google_dark_normal","beterraba", "pequi", 0, 0, description);
        assertEquals(0 , element.getIdInformation());
    }

    @Test
    public void testIfIdInformationIsSmallerThanZero() throws Exception{
        List<String> description = new ArrayList<String>();
        description.add("Description.");
        try {
            element = new Element(0, 0, 100, "btn_google_dark_normal", "beterraba", "pequi", 0, -1, description);
        }catch (IllegalArgumentException idInformationException) {
            assertEquals(idInformationException.getMessage(), "Invalid id Information");
        }
    }

    @Test
    public void testIfIdInformationIsLargerThan100() throws Exception{
        List<String> description = new ArrayList<String>();
        description.add("Description.");
        try {
            element = new Element(0, 0, 100, "btn_google_dark_normal", "beterraba", "pequi", 0, 101, description);
        }catch (IllegalArgumentException idInformationException) {
            assertEquals(idInformationException.getMessage(), "Invalid id Information");
        }
    }

    @Test
    public void testIfIdBookIsSmallerThanZero() throws Exception{
        List<String> description = new ArrayList<String>();
        description.add("Description.");
        try {
            element = new Element(0, 0, 100, "btn_google_dark_normal", "beterraba", "pequi", -1, 0, description);
        }catch (IllegalArgumentException idBookException) {
            assertEquals(idBookException.getMessage(), "Invalid id book");
        }
    }

    @Test
    public void testIfIdBookIsLargerThan3() throws Exception{
        List<String> description = new ArrayList<String>();
        description.add("Description.");
        try {
            element = new Element(0, 0, 100, "btn_google_dark_normal", "beterraba", "pequi", 4, 0, description);
        }catch (IllegalArgumentException idBookException) {
            assertEquals(idBookException.getMessage(), "Invalid id book");
        }
    }

    @Test
    public void testIfElementScoreIsInvalid() throws Exception{
        List<String> description = new ArrayList<String>();
        description.add("Description.");
        try {
            element = new Element(0, 0, -1, "btn_google_dark_normal", "beterraba", "pequi", 0, 0, description);
        }catch (IllegalArgumentException elementScoreException) {
            assertEquals(elementScoreException.getMessage(), "Invalid element's score");
        }
    }
}

