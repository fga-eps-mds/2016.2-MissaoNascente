package com.example.jbbmobile;


import android.content.Context;
import android.support.test.InstrumentationRegistry;


import com.example.jbbmobile.controller.RegisterController;
import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.dao.RegisterRequest;
import com.example.jbbmobile.model.Explorer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

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
        /* This test needs a thread because we need to wait for the webservice response.
         wait() doesn't work were, because it freezes all code operations, include the webservice
         thread.
         */
        Thread thread = new Thread(){
            int waited = 0;
            @Override
            public void run() {
                while(waited < 1000){
                    try {
                        sleep(200);
                        waited += 200;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        thread.start();

        while(thread.isAlive()){
            new RegisterController().checkIfHasError(context);
        }

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
        Explorer explorerTest = new Explorer("user", "user@email.com", "1234567", "1234567");
        testIfInsertExplorerIsSuccessful();
        Explorer explorer = explorerDAO.findExplorerLogin(explorerTest.getEmail(),explorerTest.getPassword());
        testIfDeleteExplorerIsSuccessful();
        assertEquals(explorerTest.getEmail(),explorer.getEmail());
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

    @Test
    public void testIFAllExplorersWereDeleted() throws Exception {
        testIfInsertExplorerIsSuccessful();
        explorerDAO.deleteAllExplorers(explorerDAO.getWritableDatabase());
        Explorer explorer = explorerDAO.findExplorer("user@email.com");
        assertEquals(null, explorer.getEmail());
    }

    @After
    public void closeDataBase(){
        explorerDAO.close();
    }
}