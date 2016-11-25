package gov.jbb.missaonascente.controller;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import gov.jbb.missaonascente.controller.HistoryController;
import gov.jbb.missaonascente.dao.BookDAO;
import gov.jbb.missaonascente.dao.ElementDAO;
import gov.jbb.missaonascente.dao.ExplorerDAO;
import gov.jbb.missaonascente.model.Book;
import gov.jbb.missaonascente.model.Element;
import gov.jbb.missaonascente.model.Explorer;

import org.junit.Before;
import org.junit.Test;

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
    public void testIfGetElementHistory() throws Exception {
        Element element1 = new Element(18, 17, 200, "ponto_2", "Pequi", 3, "Planta do cerrado", -10, 1, "Mensagem18");
        Element element2 = new Element(19, 1, 200, "ponto_3", "Jacarandá do Cerrado", 3, "Planta do cerrado", -10, 2,"Mensagem19");
        Book book = new Book(3,"Summer");
        bookDAO = new BookDAO(context);
        bookDAO.insertBook(book);

        elementDAO.insertElement(element1);
        elementDAO.insertElement(element2);

        historyController = new HistoryController(context);
        historyController.getElementsHistory();

        assertEquals(1, historyController.getElement().getHistory());
    }

    @Test
    public void testIfElementsHistoryDoNotExist() throws Exception {
        Book book = new Book(3,"Summer");

        bookDAO.insertBook(book);

        historyController.getElementsHistory();

        assertEquals(null, historyController.getElement());
    }

    @Test
    public void testIfFirstElementHistoryRegister() throws Exception{
        historyController.deleteSave(context);
        Element element1 = new Element(18, 18, 200, "ponto_2", "Pequi", 3, "Planta do cerrado", -10, 1, "Mensagem18");
        Element element2 = new Element(19, 19, 200, "ponto_3", "Jacarandá do Cerrado", 3, "Planta do cerrado", -10, 2,"Mensagem19");
        Book book = new Book(3,"Summer");
        bookDAO.insertBook(book);

        elementDAO.insertElement(element1);
        elementDAO.insertElement(element2);

        historyController = new HistoryController(context);
        historyController.getElementsHistory();

        Explorer explorer = new Explorer("user", "user@email.com", "12345678", "12345678");
        explorer.setScore(0);
        explorerDAO.insertExplorer(explorer);

        historyController.sequenceElement(1,explorer);
        historyController.loadSave();

        assertEquals(2,historyController.getCurrentElement());
    }

    @Test
    public void testIfNotBeginHistory() throws Exception{
        Element element1 = new Element(18, 18, 200, "ponto_2", "Pequi", 3, "Planta do cerrado", -10, 1, "Mensagem18");
        Element element2 = new Element(19, 19, 200, "ponto_3", "Jacarandá do Cerrado", 3, "Planta do cerrado", -10, 2,"Mensagem19");
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

        assertEquals(1,historyController.getCurrentElement());
    }

    @Test
    public void testIfFinishHistory() throws Exception{
        Element element1 = new Element(18, 18, 200, "ponto_2", "Pequi", 3, "Planta do cerrado", -10, 1, "Mensagem18");
        Element element2 = new Element(19, 19, 200, "ponto_3", "Jacarandá do Cerrado", 3, "Planta do cerrado", -10, 2,"Mensagem19");
        Book book = new Book(3,"Summer");
        bookDAO.insertBook(book);

        elementDAO.insertElement(element1);
        elementDAO.insertElement(element2);

        Explorer explorer = new Explorer("user", "user@email.com", "12345678", "12345678");
        explorer.setScore(0);

        explorerDAO.insertExplorer(explorer);

        historyController = new HistoryController(context);
        historyController.getElementsHistory();

        historyController.sequenceElement(1,explorer);
        historyController.loadSave();
        historyController.sequenceElement(2,explorer);
        historyController.loadSave();

        boolean historyFinish = historyController.endHistory();

        assertTrue(historyFinish);
    }

}
