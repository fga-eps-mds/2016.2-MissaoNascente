package com.example.jbbmobile;

import android.support.annotation.Nullable;

import com.example.jbbmobile.model.Explorers;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * Created by renata on 13/09/16.
 */
public class ExplorerTest {

    private Explorers explorer;

    @org.junit.Test
    public void testIfExploreIsCreated() throws Exception {

        try {
            explorer = new Explorers("user", "user@email.com", "12345678", "12345678");
            assertEquals("user", explorer.getNickname());
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }
    }

    @org.junit.Test
    public void testIfExploreIsCreatedOnlyWithEmailAndPassword() throws Exception {

        try {
            explorer = new Explorers("user2@email.com", "12345678");
            assertEquals("user2@email.com", explorer.getEmail());
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }
    }

    @org.junit.Test
    public void testIfExploreIsCreatedOnlyWithNicknameAndEmail() throws Exception {

        try {
            explorer = new Explorers();
            explorer.googleExplorer("user", "user3@email.com");
            assertEquals("user", explorer.getNickname());
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }
    }

    @org.junit.Test
    public void testIfNicknameLengthIsLessThan2() throws Exception {
        boolean invalid = false;

        try {
            explorer = new Explorers("u", "user@email.com", "12345678", "12345678");
        } catch (IllegalArgumentException emailException) {
            invalid = emailException.getMessage().equals("nick");
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }

        assertTrue(invalid);
    }

    @org.junit.Test
    public void testIfNicknameLengthIsMoreThan13() throws Exception {
        boolean invalid = false;

        try {
            explorer = new Explorers("useruseruseruseruser", "user@email.com", "12345678", "12345678");
        } catch (IllegalArgumentException emailException) {
            invalid = emailException.getMessage().equals("nick");
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }

        assertTrue(invalid);
    }

    @org.junit.Test
    public void testIfNicknameIsValid() throws Exception {
        boolean invalid = false;

        try {
            explorer = new Explorers("user", "user@email.com", "12345678", "12345678");
        } catch (IllegalArgumentException emailException) {
            invalid = emailException.getMessage().equals("nick");
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }

        assertFalse(invalid);
    }

    @org.junit.Test
    public void testIfPasswordLengthIsLessThan6() throws Exception {
        boolean invalid = false;

        try {
            explorer = new Explorers("user", "user@email.com", "12345", "12345");
        } catch (IllegalArgumentException emailException) {
            invalid = emailException.getMessage().equals("password");
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }

        assertTrue(invalid);
    }

    @org.junit.Test
    public void testIfPasswordLengthIsMoreThan13() throws Exception {
        boolean invalid = false;

        try {///
            explorer = new Explorers("user", "user@email.com", "1234567890abcde", "1234567890abcde");
        } catch (IllegalArgumentException emailException) {
            invalid = emailException.getMessage().equals("password");
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }

        assertTrue(invalid);
    }

    @org.junit.Test
    public void testIfPasswordIsValid() throws Exception {
        boolean invalid = false;

        try {
            explorer = new Explorers("user", "user@email.com", "1234567", "1234567");
        } catch (IllegalArgumentException emailException) {
            invalid = emailException.getMessage().equals("password");
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }

        assertFalse(invalid);
    }

    @org.junit.Test
    public void testIfPasswordAndConfirmationAreNotEquals() throws Exception {
        boolean invalid = false;

        try {
            explorer = new Explorers("user", "user@email.com", "1234567", "12345678");
        } catch (IllegalArgumentException emailException) {
            invalid = emailException.getMessage().equals("confirmPassword");
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }

        assertTrue(invalid);
    }

    @org.junit.Test
    public void testIfPasswordAndConfirmationAreEquals() throws Exception {
        boolean invalid = false;

        try {
            explorer = new Explorers("user", "user@email.com", "1234567", "1234567");
        } catch (IllegalArgumentException emailException) {
            invalid = emailException.getMessage().equals("confirmPassword");
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }

        assertFalse(invalid);
    }

    @org.junit.Test
    public void testIfEmailIsNotValid() throws Exception {
        boolean invalid = false;

        try {
            explorer = new Explorers("user", "user.com", "1234567", "1234567");
        } catch (IllegalArgumentException emailException) {
            invalid = emailException.getMessage().equals("email");
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }

        assertTrue(invalid);
    }

    @org.junit.Test
    public void testIfEmailIsValid() throws Exception {
        boolean invalid = false;

        try {
            explorer = new Explorers("user", "user@email.com", "1234567", "1234567");
        } catch (IllegalArgumentException emailException) {
            invalid = emailException.getMessage().equals("email");
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }

        assertFalse(invalid);
    }
}