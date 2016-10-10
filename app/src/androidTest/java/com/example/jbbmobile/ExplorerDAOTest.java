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

    @Before
    public void setup(){
        Context context = InstrumentationRegistry.getTargetContext();
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
        testIfInsertExplorerIsSuccessful();
        Explorer explorer = explorerDAO.findExplorer(email);
        testIfDeleteExplorerIsSuccessful();
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
        testIfInsertExplorerIsSuccessful();
        Explorer explorer = explorerDAO.findExplorerLogin(email,password);
        testIfDeleteExplorerIsSuccessful();
        assertEquals(email,explorer.getEmail());
    }

    @Test
    public void testIfSelectExplorerLoginIsNotSuccessful() throws Exception{
        String email = "user@email.com", password = "abcdefg";
        Explorer explorer = explorerDAO.findExplorerLogin(email,password);
        assertNotEquals(email,explorer.getEmail());
    }

    @Test
    public void testIfUpdateExplorerIsSuccessful() throws Exception{
        testIfInsertExplorerIsSuccessful();
        Explorer explorer = new Explorer("USER", "user@email.com", "1234567", "1234567");
        int successful = explorerDAO.updateExplorer(explorer);
        testIfDeleteExplorerIsSuccessful();
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

    @After
    public void closeDataBase(){
        explorerDAO.close();
    }
}