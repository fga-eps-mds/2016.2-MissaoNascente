package com.example.jbbmobile;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.model.Element;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ElementDAOTest {
    Context context;
    ElementDAO elementDAO;

    @Before
    public void setup(){
        context = InstrumentationRegistry.getTargetContext();
        elementDAO = new ElementDAO(context);
        elementDAO.createTableElement(elementDAO.getWritableDatabase());
    }

    @Test
    public void testIfInsertElementIsSuccessful() throws Exception{
        Element element = new Element(18, 17, 200, "ponto_3", "Jacarandá do Cerrado", "Jacaranda", 1, "Planta do cerrado");
        int successful = elementDAO.insertElement(element);
        assertNotEquals(-1,successful);
    }

    @Test
    public void testIfInsertElementNotIsSuccessful() throws Exception{
        Element element = new Element();
        int notSuccessful = elementDAO.insertElement(element);
        assertEquals(-1,notSuccessful);
    }

    @Test
    public void testIfSelectElementIsSuccessful() throws Exception{
        int idElement = 18;
        testIfInsertElementIsSuccessful();
        Element element = elementDAO.findElement(idElement);
        testIfDeleteElementIsSuccessful();
        assertEquals(idElement,element.getIdElement());
    }

    @Test
    public void testIfSelectElementIsNotSuccessful() throws Exception{
        int idElement = -988;
        Element element = elementDAO.findElement(idElement);
        assertNotEquals(idElement,element.getIdElement());
    }

    @Test
    public void testIfSelect1ElementIsSuccessful() throws Exception{
        int idBook = 1;
        testIfInsertElementIsSuccessful();
        List<Element> elementList = elementDAO.findElementsBook(idBook);
        testIfDeleteElementIsSuccessful();
        assertNotEquals(0,elementList.size());
    }

    @Test
    public void testIfSelect1ElementIsNotSuccessful() throws Exception{
        int idBook = -988;
        List<Element> elementList = elementDAO.findElementsBook(idBook);
        assertEquals(0,elementList.size());
    }

    @Test
    public void testIfUpdateElementIsSuccessful() throws Exception{
        testIfInsertElementIsSuccessful();
        Element element = new Element(18, 17, 200, "ponto_3", "Jacarandá do Cerrado do Centro-Oeste", "Jacaranda", 1, "Planta do cerrado");
        int successful;
        successful = elementDAO.updateElement(element);
        testIfDeleteElementIsSuccessful();
        assertEquals(1,successful);
    }

    @Test
    public void testIfUpdateElementNotIsSuccessful() throws Exception{
        Element element = new Element();
        element.setIdElement(-987);
        int notSuccessful = elementDAO.updateElement(element);
        assertEquals(0,notSuccessful);
    }

    @Test
    public void testIfDeleteElementIsSuccessful() throws Exception{
        Element element = new Element(18, 17, 200, "ponto_3", "Jacarandá do Cerrado", "Jacaranda", 1, "Planta do cerrado");
        int successful;
        successful = elementDAO.deleteElement(element);
        assertEquals(1,successful);
    }

    @Test
    public void testIfDeleteElementNotIsSuccessful() throws Exception{
        Element element = new Element();
        element.setIdElement(-987);
        int notSuccessful = elementDAO.deleteElement(element);
        assertEquals(0,notSuccessful);
    }

    @After
    public void closeDataBase(){
        elementDAO.close();
    }
}
