package com.example.jbbmobile;


import android.content.Context;
import android.support.test.InstrumentationRegistry;


import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Explorer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExplorerDAOTest {

    private ExplorerDAO explorerDAO;
    private Context context;
    @Before
    public void setup(){
        Context context = InstrumentationRegistry.getTargetContext();
        this.context = context;
        explorerDAO = new ExplorerDAO(context);
        explorerDAO.onUpgrade(explorerDAO.getReadableDatabase(),1,1);
    }

    @Test
    public void testIfInsertExplorerIsSuccessful() throws Exception{
        Explorer explorer = new Explorer("user", "user@email.com", "1234567", "1234567");
        int successful = explorerDAO.insertExplorer(explorer);
        assertNotEquals(-1,successful);
    }

    @Test
    public void testIfInsertExplorerIsNotSuccessful() throws Exception{
        Explorer explorer = new Explorer();
        int notSuccessful = explorerDAO.insertExplorer(explorer);
        assertEquals(-1,notSuccessful);
    }

    @Test
    public void testIfSelectExplorerIsSuccessful() throws Exception{
        String email = "user@email.com";
        Explorer explorer = new Explorer("user", "user@email.com", "1234567", "1234567");
        explorerDAO.insertExplorer(explorer);

        explorer = explorerDAO.findExplorer(email);
        assertEquals(email,explorer.getEmail());
    }

    @Test
    public void testIfSelectExplorerIsNotSuccessful() throws Exception{
        String email = "notFound@email.com";
        Explorer explorer = explorerDAO.findExplorer(email);
        assertNotEquals(email,explorer.getEmail());
    }

    @Test
    public void testIfSelectExplorerLoginIsSuccessful() throws Exception{
        String email = "user@email.com", password = "1234567";
        Explorer explorer = new Explorer("user", "user@email.com", "1234567", "1234567");
        explorerDAO.insertExplorer(explorer);

        Explorer explorer1 = explorerDAO.findExplorerLogin(email,explorer.cryptographyPassword(password));
        assertEquals(explorer.getEmail(),explorer1.getEmail());
    }

    @Test
    public void testIfSelectExplorerLoginIsNotSuccessful() throws Exception{
        String email = "user@email.com", password = "abcdefg";
        Explorer explorer = explorerDAO.findExplorerLogin(email,password);
        assertNotEquals(email,explorer.getEmail());
    }

    @Test
    public void testIfUpdateExplorerIsSuccessful() throws Exception{
        Explorer explorer = new Explorer("user", "user@email.com", "1234567", "1234567");
        explorerDAO.insertExplorer(explorer);

        Explorer explorer1 = new Explorer("USER", "user@email.com", "1234567", "1234567");
        int successful = explorerDAO.updateExplorer(explorer1);
        assertEquals(1,successful);
    }

    @Test
    public void testIfUpdateExplorerIsNotSuccessful() throws Exception{
        Explorer explorer = new Explorer("notFound@email.com","123456");
        int notSuccessful = explorerDAO.updateExplorer(explorer);
        assertEquals(0,notSuccessful);
    }

    @Test
    public void testIfDeleteExplorerIsSuccessful() throws Exception{
        Explorer explorer = new Explorer("user", "user@email.com", "1234567", "1234567");
        explorerDAO.insertExplorer(explorer);
        int successful = explorerDAO.deleteExplorer(explorer);
        assertEquals(1,successful);
    }

    @Test
    public void testIfDeleteExplorerIsNotSuccessful() throws Exception{
        Explorer explorer = new Explorer("notFound@email.com","1234567");
        int notSuccessful = explorerDAO.deleteExplorer(explorer);
        assertEquals(0,notSuccessful);
    }

    @Test
    public void testIfAllExplorersWereDeleted() throws Exception {
        testIfInsertExplorerIsSuccessful();
        explorerDAO.deleteAllExplorers(explorerDAO.getWritableDatabase());
        Explorer explorer = explorerDAO.findExplorer("user@email.com");
        assertEquals(null, explorer.getEmail());
    }

    @Test(expected = Exception.class)
    public void testIfAllExplorersWereNotDeleted () throws Exception {
        final String TABLE = "EXPLORER";
        explorerDAO.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TABLE);
        explorerDAO.deleteAllExplorers(explorerDAO.getWritableDatabase());
    }

    @After
    public void closeDataBase(){
        explorerDAO.close();
    }
}