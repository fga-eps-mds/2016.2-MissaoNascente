package com.example.jbbmobile;

import android.content.Context;
import android.database.SQLException;
import android.support.test.InstrumentationRegistry;

import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Element;
import com.example.jbbmobile.model.Explorer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ElementDAOTest {
    private ElementDAO elementDAO;
    private ExplorerDAO explorerDAO;
    final float DATABASE_VERSION = 1.0f;
    final float ELEMENT_VERSION = 1.0f;
    final float DEFAULT_DATABASE_VERSION = 0.0f;
    final float DEFAULT_ELEMENT_VERSION = 0.0f;

    @Before
    public void setup(){
        Context context = InstrumentationRegistry.getTargetContext();
        elementDAO = new ElementDAO(context);
        explorerDAO = new ExplorerDAO(context);
        elementDAO.onUpgrade(elementDAO.getReadableDatabase(),1,1);
        explorerDAO.onUpgrade(explorerDAO.getReadableDatabase(),1,1);
    }

    // Relation Table Methods
    @Test
    public void testIfFindElementFromRelationTableIsSuccessful() throws Exception{
        Explorer explorer = new Explorer("email@email.com","Name","1234567");
        Element element = new Element(18, 17, 200, "ponto_3", "Jacarandá do Cerrado", 1, "Planta do cerrado", -10);

        explorerDAO.insertExplorer(explorer);
        elementDAO.insertElement(element);
        elementDAO.insertElementExplorer(element.getIdElement(),explorer.getEmail(),"2016-12-31", null);

        Element element1 = elementDAO.findElementFromRelationTable(element.getIdElement(),explorer.getEmail());
        assertEquals(element1.getIdElement(),element.getIdElement());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIfFindElementFromRelationTableIsNotSuccessful() throws Exception{
        String email = "email@email.com";
        int idElement = 18;
            Element element = elementDAO.findElementFromRelationTable(idElement, email);
            assertNotEquals(element.getIdElement(),idElement);
    }

    @Test
    public void testIfFindElementsExplorerBookIsSuccessful() throws Exception{
        Explorer explorer = new Explorer("email@email.com","Name","1234567");
        Element element = new Element(18, 17, 200, "ponto_3", "Jacarandá do Cerrado", 1, "Planta do cerrado", -10);

        explorerDAO.insertExplorer(explorer);
        elementDAO.insertElement(element);
        elementDAO.insertElementExplorer(element.getIdElement(),explorer.getEmail(),"2016-12-31", null);

        List<Element> elementList = elementDAO.findElementsExplorerBook(element.getIdBook(),explorer.getEmail());
        assertEquals(element.getIdBook(),elementList.get(0).getIdBook());
    }

    @Test
    public void testIfFindElementsExplorerBookIsNotSuccessful() throws Exception{
        int idBook = 1;
        String email = "email@email.com";

        List<Element> elementList = elementDAO.findElementsExplorerBook(idBook,email);
        assertEquals(0,elementList.size());
    }

    @Test
    public void testIfUpdateElementExplorerIsSuccessful() throws Exception{
        Explorer explorer = new Explorer("email@email.com","Name","1234567");
        Element element = new Element(18, 17, 200, "ponto_3", "Jacarandá do Cerrado", 1, "Planta do cerrado", -10);

        explorerDAO.insertExplorer(explorer);
        elementDAO.insertElement(element);
        elementDAO.insertElementExplorer(element.getIdElement(),explorer.getEmail(),"2016-12-31", null);

        int successful = elementDAO.updateElementExplorer(element.getIdElement(),explorer.getEmail(),"2012-11-28", null);
        assertEquals(1,successful);
    }

    @Test
    public void testIfUpdateElementExplorerNotIsSuccessful() throws Exception{
        int idElement = 1;
        String email = "email@email.com";
        String data = "2016-12-31";

        int notSuccessful = elementDAO.updateElementExplorer(idElement, email, data, null);
        assertEquals(0,notSuccessful);
    }

    @Test
    public void testIfDeleteElementExplorerIsSuccessful() throws Exception{
        Explorer explorer = new Explorer("email@email.com","Name","1234567");
        Element element = new Element(18, 17, 200, "ponto_3", "Jacarandá do Cerrado", 1, "Planta do cerrado", -10);

        explorerDAO.insertExplorer(explorer);
        elementDAO.insertElement(element);
        elementDAO.insertElementExplorer(element.getIdElement(),explorer.getEmail(),"2016-12-31", null);

        int successful = elementDAO.deleteElementExplorer(element.getIdElement(),explorer.getEmail());
        assertEquals(1,successful);
    }

    @Test
    public void testIfDeleteElementExplorerNotIsSuccessful() throws Exception{
        int idElement = 1;
        String email = "email@email.com";

        int notSuccessful = elementDAO.deleteElementExplorer(idElement,email);
        assertEquals(0,notSuccessful);
    }

    @Test
    public void testIfInsertElementExplorerIsSuccessful() throws Exception{
        Explorer explorer = new Explorer("email@email.com","Name","1234567");
        Element element = new Element(18, 17, 200, "ponto_3", "Jacarandá do Cerrado", 1, "Planta do cerrado", -10);

        explorerDAO.insertExplorer(explorer);
        elementDAO.insertElement(element);
        int successful = elementDAO.insertElementExplorer(element.getIdElement(),explorer.getEmail(),"2016-12-31", null);
        assertNotEquals(-1,successful);
    }

    @Test
    public void testIfInsertElementExplorerIsNotSuccessful() throws Exception{
        int idElement = 18;
        int notSuccessful = elementDAO.insertElementExplorer(idElement, null,"2016-12-31", null);
        assertEquals(-1,notSuccessful);
    }

    // Element Table Methods

    @Test
    public void testIfFindElementFromElementTableIsSuccessful() throws Exception{
        int idElement = 18;
        testIfInsertElementIsSuccessful();
        Element element = elementDAO.findElementFromElementTable(idElement);
        assertEquals(idElement,element.getIdElement());
    }

    @Test(expected = Exception.class)
    public void testIfFindElementFromElementTableIsNotSuccessful() throws Exception{
        int idElement = 1;
        Element element = elementDAO.findElementFromElementTable(idElement);
        assertNotEquals(idElement,element.getIdElement());
    }

    @Test
    public void testIfFindElementsBookIsSuccessful() throws Exception{
        int idBook = 1;
        testIfInsertElementIsSuccessful();
        List<Element> elementList = elementDAO.findElementsBook(idBook);
        assertEquals(idBook,elementList.get(0).getIdBook());
    }

    @Test
    public void testIfFindElementsBookIsNotSuccessful() throws Exception{
        int idBook = 1;
        List<Element> elementList = elementDAO.findElementsBook(idBook);
        assertEquals(0,elementList.size());
    }

    @Test
    public void testIfUpdateElementIsSuccessful() throws Exception{
        testIfInsertElementIsSuccessful();
        Element element = new Element(18, 17, 200, "ponto_3", "Jacarandá do Cerrado do Centro-Oeste", 1, "Planta do cerrado", -10);
        int successful;
        successful = elementDAO.updateElement(element);
        testIfDeleteElementIsSuccessful();
        assertEquals(1,successful);
    }

    @Test
    public void testIfUpdateElementNotIsSuccessful() throws Exception{
        Element element = new Element();
        element.setIdElement(1);
        int notSuccessful = elementDAO.updateElement(element);
        assertEquals(0,notSuccessful);
    }

    @Test
    public void testIfDeleteElementIsSuccessful() throws Exception{
        Element element = new Element(18, 17, 200, "ponto_3", "Jacarandá do Cerrado", 1, "Planta do cerrado", -10);
        int successful;
        elementDAO.insertElement(element);
        successful = elementDAO.deleteElement(element);
        assertEquals(1,successful);
    }

    @Test
    public void testIfDeleteElementNotIsSuccessful() throws Exception{
        Element element = new Element();
        element.setIdElement(1);
        int notSuccessful = elementDAO.deleteElement(element);
        assertEquals(0,notSuccessful);
    }

    @Test
    public void testIfInsertElementIsSuccessful() throws Exception{
        Element element = new Element(18, 17, 200, "ponto_3", "Jacarandá do Cerrado", 1, "Planta do cerrado", -10);
        int successful = elementDAO.insertElement(element);
        assertNotEquals(-1,successful);
    }

    @Test
    public void testIfInsertElementIsNotSuccessful() throws Exception{
        Element element = new Element();
        int notSuccessful = elementDAO.insertElement(element);
        assertEquals(-1,notSuccessful);
    }

    @Test
    public void testInsertElementExplorerIsSuccessful() throws Exception{
        Element element = new Element(1, 1, 230, "ponto_3", "Jacarandá do Cerrado", 1, "Planta do cerrado",1.99f, 1.99f, -10);
        int successful = elementDAO.insertElement(element);
        assertNotEquals(-1,successful);
    }

    @Test
    public void testInsertElementExplorerIsNotSuccessful()  throws Exception{
        Element element = new Element();
        int successful = elementDAO.insertElement(element);
        assertEquals(-1,successful);
    }

    @Test
    public void testFindElementByQrCodeIsSuccessful() throws Exception {
        Element element = new Element(1, 1, 230, "ponto_3", "Jacarandá do Cerrado", 1, "Planta do cerrado",1.99f, 1.99f, -10);
        elementDAO.insertElement(element);

        int qrCodeNumber = 1;
        Element element1 = elementDAO.findElementByQrCode(qrCodeNumber);
        assertEquals(element1.getQrCodeNumber(),qrCodeNumber);
    }

    @Test
    public void testFindElementByQrCodeIsNotSuccessful() throws Exception {
        int qrCodeNumber = 1;
        boolean notSuccessful = false;

        try {
            elementDAO.findElementByQrCode(qrCodeNumber);
        }catch(Exception e){
            notSuccessful = e.getMessage().equals("Qr Code Inválido");
        }

        assertTrue(notSuccessful);
    }

    @Test
    public void testInsertElementExplorerQrCodeIsSuccessful()throws Exception{

        Explorer explorer = new Explorer("email@email.com","Name","1234567");
        Element element =new Element(1, 1, 230, "ponto_3", "Jacarandá do Cerrado", 1, "Planta do cerrado",1.99f, 1.99f, -10);

        explorerDAO.insertExplorer(explorer);
        elementDAO.insertElement(element);
        int successful = elementDAO.insertElementExplorer(element.getQrCodeNumber(),explorer.getEmail(),"2016-12-31", "");
        assertNotEquals(-1,successful);
    }

    @Test
    public void testInsertElementExplorerQrCodeIsNotSuccessful()throws Exception{
        Element element =new Element(1, 1, 230, "ponto_3", "Jacarandá do Cerrado", 1, "Planta do cerrado",1.99f, 1.99f, -10);
        boolean notSuccessful= false;
        elementDAO.insertElement(element);
        try {
            elementDAO.insertElementExplorer(null, "2016-12-31", element.getQrCodeNumber(),null);
        }catch (SQLException exception){
            notSuccessful = true;
        }
        assertTrue(notSuccessful);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIfAllElementsWereDeletedFromRelationTable(){
        String email ="email@email.com";
        String date = "24 de outubro de 2016";
        Element element = new Element(0, 1, 100, "ponto_2", "Pau-Santo", 1, "",15.123f,14.123f,10);
        elementDAO.insertElementExplorer(element.getIdElement(), email, date, "");
        elementDAO.deleteAllElementsFromElementExplorer(elementDAO.getWritableDatabase());
        elementDAO.findElementFromRelationTable(element.getIdElement(), email);
    }

    @Test
    public void testIfVersionTableWasUpdated() throws Exception{
        elementDAO.updateVersion(DATABASE_VERSION);
        assertEquals(DATABASE_VERSION, elementDAO.checkVersion(), 0);
    }

    @Test
    public void testCheckVersion() throws Exception{
        assertEquals(DEFAULT_DATABASE_VERSION, elementDAO.checkVersion(), 0);
    }

    @Test
    public void testElementVersion() throws Exception{
        Element element = new Element(1, 1, 230, "ponto_3", "Jacarandá do Cerrado", 1, "Planta do cerrado",1.99f, 1.99f,10);
        elementDAO.insertElement(element);
        assertEquals(DEFAULT_ELEMENT_VERSION, elementDAO.checkElementVersion(element.getIdElement()), 0);
    }

    @Test
    public void testIfElementVersionWasUpdated() throws Exception{
        Element element = new Element(1, 1, 230, "ponto_3", "Jacarandá do Cerrado", 1, "Planta do cerrado",1.99f, 1.99f,10);
        elementDAO.insertElement(element);
        elementDAO.updateElementVersion(ELEMENT_VERSION, element);
        assertEquals(ELEMENT_VERSION, elementDAO.checkElementVersion(element.getIdElement()), 0);
    }

    @Test(expected =  Exception.class)
    public void testIfDeleteAllElementsWasSuccessful(){
        Element element = new Element(1, 1, 230, "ponto_3", "Jacarandá do Cerrado", 1, "Planta do cerrado",1.99f, 1.99f,10);
        elementDAO.insertElement(element);
        elementDAO.deleteAllElements();
        elementDAO.findElementFromElementTable(element.getIdElement());
    }

    @Test
    public void testIfCouldGetAllElements(){
        Element element = new Element(1, 1, 230, "ponto_3", "Jacarandá do Cerrado", 1, "Planta do cerrado",1.99f, 1.99f,10);
        Element elementTwo = new Element(0, 0, 100, "ponto_2", "Pau-Santo", 1, "",15.123f,14.123f,10);
        elementDAO.insertElement(element);
        elementDAO.insertElement(elementTwo);
        List<Element> elements = elementDAO.findAllElements();
        assertEquals(0, elements.get(0).getIdElement());
        assertEquals(1, elements.get(1).getIdElement());
    }

    @After
    public void closeDataBase(){
        elementDAO.close();
        explorerDAO.close();
    }
}
