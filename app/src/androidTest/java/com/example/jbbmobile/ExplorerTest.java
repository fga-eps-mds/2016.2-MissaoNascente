package com.example.jbbmobile;

import com.example.jbbmobile.model.Explorer;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ExplorerTest {

    private Explorer explorer;

    @Test
    public void testIfExploreIsCreated() throws Exception {

        try {
            explorer = new Explorer("user", "user@email.com", "12345678", "12345678");
            assertEquals("user", explorer.getNickname());
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }
    }

    @Test
    public void testIfExplorerIsCreatedOnlyWithScoreAndNickname() throws Exception{
        try{
            explorer = new Explorer(30, "nickname");
            assertEquals(30, explorer.getScore());
            assertEquals("nickname", explorer.getNickname());
        } catch (Exception explorerException){
            explorerException.printStackTrace();
        }
    }

    @Test
    public void testIfExplorerIsCreatedOnlyWithScoreNicknameAndPosition() throws Exception{
        try {
            explorer = new Explorer(30, "nickname", 1);
            assertEquals(30, explorer.getScore());
            assertEquals("nickname", explorer.getNickname());
            assertEquals(1, explorer.getPosition());
        }catch (Exception explorerException){
            explorerException.printStackTrace();
        }
    }

    @org.junit.Test
    public void testIfExploreIsCreatedOnlyWithEmailAndPassword() throws Exception {

        try {
            explorer = new Explorer("user2@email.com", "12345678");
            assertEquals("user2@email.com", explorer.getEmail());
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }
    }

    @Test
    public void testIfExploreIsCreatedOnlyWithNicknameAndEmail() throws Exception {

        try {
            explorer = new Explorer();
            explorer.googleExplorer("user", "user3@email.com");
            assertEquals("user", explorer.getNickname());
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }
    }

    @Test
    public void testIfNicknameNotAcceptedsEspecialChar() throws Exception {
        boolean invalid = false;

        try {
            explorer = new Explorer("*?!.=", "user@email.com", "12345678", "12345678");
        } catch (IllegalArgumentException emailException) {
            invalid = emailException.getMessage().equals("Invalid nick");
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }

        assertTrue(invalid);
    }

    @Test
    public void testIfNicknameLengthIsLessThan2() throws Exception {
        boolean invalid = false;

        try {
            explorer = new Explorer("u", "user@email.com", "12345678", "12345678");
        } catch (IllegalArgumentException emailException) {
            invalid = emailException.getMessage().equals("Invalid nick");
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }

        assertTrue(invalid);
    }

    @Test
    public void testIfNicknameLengthIsMoreThan13() throws Exception {
        boolean invalid = false;

        try {
            explorer = new Explorer("useruseruseruseruser", "user@email.com", "12345678", "12345678");
        } catch (IllegalArgumentException emailException) {
            invalid = emailException.getMessage().equals("Invalid nick");
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }

        assertTrue(invalid);
    }

    @Test
    public void testIfNicknameIsValid() throws Exception {
        boolean invalid = false;

        try {
            explorer = new Explorer("user", "user@email.com", "12345678", "12345678");
        } catch (IllegalArgumentException emailException) {
            invalid = emailException.getMessage().equals("Invalid nick");
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }

        assertFalse(invalid);
    }

    @Test
    public void testIfPasswordLengthIsLessThan6() throws Exception {
        boolean invalid = false;

        try {
            explorer = new Explorer("user", "user@email.com", "12345", "12345");
        } catch (IllegalArgumentException emailException) {
            invalid = emailException.getMessage().equals("Invalid password");
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }

        assertTrue(invalid);
    }

    @Test
    public void testIfPasswordLengthIsMoreThan13() throws Exception {
        boolean invalid = false;

        try {
            explorer = new Explorer("user", "user@email.com", "1234567890abcde", "1234567890abcde");
        } catch (IllegalArgumentException emailException) {
            invalid = emailException.getMessage().equals("Invalid password");
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }

        assertTrue(invalid);
    }

    @Test
    public void testIfPasswordIsValid() throws Exception {
        boolean invalid = false;

        try {
            explorer = new Explorer("user", "user@email.com", "1234567", "1234567");
        } catch (IllegalArgumentException emailException) {
            invalid = emailException.getMessage().equals("Invalid password");
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }

        assertFalse(invalid);
    }

    @Test
    public void testCryptographyPassword() throws Exception{
        boolean invalid = false;
        String passwordDigest = null;

        try{
            explorer = new Explorer("user", "user@email.com", "1234567", "1234567");
            passwordDigest = explorer.cryptographyPassword ("1234567");
        } catch (IllegalArgumentException passwordDigestException) {
            invalid = passwordDigest.equals("1234567");
        }

        assertFalse(invalid);
    }

    @Test
    public void testIfPasswordAndConfirmationAreNotEquals() throws Exception {
        boolean invalid = false;

        try {
            explorer = new Explorer("user", "user@email.com", "1234567", "12345678");
        } catch (IllegalArgumentException emailException) {
            invalid = emailException.getMessage().equals("Invalid confirmPassword");
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }

        assertTrue(invalid);
    }

    @Test
    public void testIfPasswordAndConfirmationAreEquals() throws Exception {
        boolean invalid = false;

        try {
            explorer = new Explorer("user", "user@email.com", "1234567", "1234567");
        } catch (IllegalArgumentException emailException) {
            invalid = emailException.getMessage().equals("Invalid confirmPassword");
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }

        assertFalse(invalid);
    }

    @Test
    public void testIfEmailLengthLowerThanThree() throws Exception {
        boolean invalid = false;

        try {
            explorer = new Explorer("user", "a@", "1234567", "1234567");
        } catch (IllegalArgumentException emailException) {
            invalid = emailException.getMessage().equals("Invalid email");
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }

        assertTrue(invalid);
    }

    @Test
    public void testIfEmailIsNotValid() throws Exception {
        boolean invalid = false;

        try {
            explorer = new Explorer("user", "user.com", "1234567", "1234567");
        } catch (IllegalArgumentException emailException) {
            invalid = emailException.getMessage().equals("Invalid email");
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }

        assertTrue(invalid);
    }

    @Test
    public void testIfEmailIsValid() throws Exception {
        boolean invalid = false;

        try {
            explorer = new Explorer("user", "user@email.com", "1234567", "1234567");
        } catch (IllegalArgumentException emailException) {
            invalid = emailException.getMessage().equals("Invalid email");
        } catch (Exception explorerException) {
            explorerException.printStackTrace();
        }

        assertFalse(invalid);
    }

    @Test
    public void testIfToStringReturnExplorerNicknameAndEmail() throws Exception {
        explorer = new Explorer("user", "user@email.com", "1234567", "1234567");
        String explorerString = "user\nuser@email.com\n";
        assertEquals(explorerString, explorer.toString());
    }

    @Test
    public void testIfExplorerPositionWasSetted() throws Exception {
        explorer = new Explorer("user", "user@email.com", "1234567", "1234567");
        int positionUser = 1;
        explorer.setPosition(positionUser);
        assertEquals(explorer.getPosition(), positionUser);
    }

    @Test
    public void testIfScoreWasUpdated() throws Exception {
        explorer = new Explorer("user", "user@email.com", "1234567", "1234567");
        explorer.setScore(10);
        explorer.updateScore(20);

        assertEquals(30, explorer.getScore());
    }

    @Test
    public void testIfEnergyIsValid() throws Exception {
        explorer = new Explorer("explorer@explorer.com", "explorer", "1234567");
        int energy = 0;

        explorer.setEnergy(energy);

        assertEquals(energy,explorer.getEnergy());
    }

    @Test
    public void testIfEnergyIsNotValid() throws Exception {
        explorer = new Explorer("explorer@explorer.com", "explorer", "1234567");
        int energy = -1;
        boolean invalid = false;

        try {
            explorer.setEnergy(energy);
        } catch (IllegalArgumentException energyException){
            invalid = energyException.getMessage().equals("Invalid Energy");
        }
        assertTrue(invalid);
    }
}