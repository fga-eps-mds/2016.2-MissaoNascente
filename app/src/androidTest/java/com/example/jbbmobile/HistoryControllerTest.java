package com.example.jbbmobile;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.example.jbbmobile.controller.HistoryController;
import com.example.jbbmobile.dao.BookDAO;
import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Book;
import com.example.jbbmobile.model.Element;
import com.example.jbbmobile.model.Explorer;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class HistoryControllerTest {
    private Context context;
    private HistoryController historyController;
    private ElementDAO elementDAO;
    private BookDAO bookDAO;
    private ExplorerDAO explorerDAO;

    @Before
    public void setUp(){
        this.context = InstrumentationRegistry.getTargetContext();
        elementDAO = new ElementDAO(context);
        bookDAO = new BookDAO(context);
        explorerDAO = new ExplorerDAO(context);
        elementDAO.onUpgrade(elementDAO.getReadableDatabase(),1,1);
        bookDAO.onUpgrade(elementDAO.getReadableDatabase(),1,1);
        explorerDAO.onUpgrade(explorerDAO.getReadableDatabase(),1,1);
        historyController = new HistoryController(context);
        historyController.deleteSave(context);
    }

    @Test
    public void testIfGetAllElementsHistory() throws Exception {
        Element element1 = new Element(18, 17, 200, "ponto_2", "Pequi", 3, "Planta do cerrado", -10, 1, "Mensagem18");
        Element element2 = new Element(19, 1, 200, "ponto_3", "Jacarandá do Cerrado", 3, "Planta do cerrado", -10, 1,"Mensagem19");
        Book book = new Book(3,"Summer");
        bookDAO = new BookDAO(context);
        bookDAO.insertBook(book);

        elementDAO.insertElement(element1);
        elementDAO.insertElement(element2);

        historyController.getElementsHistory();

        assertEquals(2, historyController.getElements().size());
    }

    @Test
    public void testIfElementsHistoryDoNotExist() throws Exception {
        Book book = new Book(3,"Summer");

        bookDAO.insertBook(book);

        historyController.getElementsHistory();

        assertEquals(0, historyController.getElements().size());
    }

    @Test
    public void testIfFirstElementHistoryRegister() throws Exception{
        historyController.deleteSave(context);
        Element element1 = new Element(18, 18, 200, "ponto_2", "Pequi", 3, "Planta do cerrado", -10, 1, "Mensagem18");
        Element element2 = new Element(19, 19, 200, "ponto_3", "Jacarandá do Cerrado", 3, "Planta do cerrado", -10, 1,"Mensagem19");
        Book book = new Book(3,"Summer");
        bookDAO.insertBook(book);

        elementDAO.insertElement(element1);
        elementDAO.insertElement(element2);

        historyController = new HistoryController(context);
        historyController.getElementsHistory();

        Explorer explorer = new Explorer("user", "user@email.com", "12345678", "12345678");
        explorer.setScore(0);
        explorerDAO.insertExplorer(explorer);

        historyController.sequenceElement(18,explorer);
        historyController.loadSave();

        assertEquals(19,historyController.getCurrentElement());
    }

    @Test
    public void testIfNotBeginHistory() throws Exception{
        Element element1 = new Element(18, 18, 200, "ponto_2", "Pequi", 3, "Planta do cerrado", -10, 1, "Mensagem18");
        Element element2 = new Element(19, 19, 200, "ponto_3", "Jacarandá do Cerrado", 3, "Planta do cerrado", -10, 1,"Mensagem19");
        Book book = new Book(3,"Summer");
        bookDAO.insertBook(book);

        elementDAO.insertElement(element1);
        elementDAO.insertElement(element2);

        historyController = new HistoryController(context);
        historyController.getElementsHistory();

        Explorer explorer = new Explorer("user", "user@email.com", "12345678", "12345678");
        explorer.setScore(0);
        explorerDAO.insertExplorer(explorer);

        historyController.sequenceElement(19,explorer);
        historyController.loadSave();

        assertEquals(18,historyController.getCurrentElement());
    }

    @Test
    public void testIfFinishHistory() throws Exception{
        Element element1 = new Element(18, 18, 200, "ponto_2", "Pequi", 3, "Planta do cerrado", -10, 1, "Mensagem18");
        Element element2 = new Element(19, 19, 200, "ponto_3", "Jacarandá do Cerrado", 3, "Planta do cerrado", -10, 1,"Mensagem19");
        Book book = new Book(3,"Summer");
        bookDAO.insertBook(book);

        elementDAO.insertElement(element1);
        elementDAO.insertElement(element2);

        historyController = new HistoryController(context);
        historyController.getElementsHistory();

        Explorer explorer = new Explorer("user", "user@email.com", "12345678", "12345678");
        explorer.setScore(0);

        explorerDAO.insertExplorer(explorer);

        historyController.sequenceElement(18,explorer);
        historyController.loadSave();
        historyController.sequenceElement(19,explorer);
        historyController.loadSave();

        assertEquals(-10,historyController.getCurrentElement());
    }

}
