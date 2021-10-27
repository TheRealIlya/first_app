package by.academy.jee.util;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class PasswordHasherTest {

    @Test
    public void generateSaltIsNotNull() {
        assertNotNull(PasswordHasher.generateSalt());
    }

    @Test
    public void getEncryptedPasswordWhenPasswordIsNotEmpty() {

        //given
        byte[] salt = {103, -79, -104, -40, -6, 48, 108, -91};
        String password = "qwe";
        byte[] expectedEncryptedPassword = {66, -30, 107, -7, 58, 18, -119, -31, 92, 74, -67, 85, -1,
                -84, -20, -77, 93, -7, 41, -43};
        //when
        byte[] actualEncryptedPassword = PasswordHasher.getEncryptedPassword(password, salt);
        //then
        assertEquals(Arrays.toString(expectedEncryptedPassword), Arrays.toString(actualEncryptedPassword));
    }

    @Test
    public void getEncryptedPasswordWhenPasswordIsEmpty() {

        //given
        byte[] salt = {103, -79, -104, -40, -6, 48, 108, -91};
        String password = "";
        byte[] expectedEncryptedPassword = {26, 12, -116, 92, -96, 80, -101, -19, 66, -128, 79,
                106, -84, 91, -39, 83, 99, 75, 74, -8};
        //when
        byte[] actualEncryptedPassword = PasswordHasher.getEncryptedPassword(password, salt);
        //then
        assertEquals(Arrays.toString(expectedEncryptedPassword), Arrays.toString(actualEncryptedPassword));
    }

    @Test
    public void authenticateWhenPasswordIsCorrect() {

        //given
        String attemptedPassword = "qwe";
        byte[] encryptedPassword = {66, -30, 107, -7, 58, 18, -119, -31, 92, 74, -67, 85, -1,
                -84, -20, -77, 93, -7, 41, -43};
        byte[] salt = {103, -79, -104, -40, -6, 48, 108, -91};
        //when
        boolean expectedTrue = PasswordHasher.authenticate(attemptedPassword, encryptedPassword, salt);
        //then
        assertTrue(expectedTrue);
    }

    @Test
    public void authenticateWhenPasswordIsNotCorrect() {

        //given
        String attemptedPassword = "qw";
        byte[] encryptedPassword = {66, -30, 107, -7, 58, 18, -119, -31, 92, 74, -67, 85, -1,
                -84, -20, -77, 93, -7, 41, -43};
        byte[] salt = {103, -79, -104, -40, -6, 48, 108, -91};
        //when
        boolean expectedFalse = PasswordHasher.authenticate(attemptedPassword, encryptedPassword, salt);
        //then
        assertFalse(expectedFalse);
    }
}