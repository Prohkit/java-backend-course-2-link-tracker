package edu.java.bot.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void isUrlValid() {
        String validUrl = "https://stackoverflow.com/";
        String invalidUrl = "htps://stackoverflow.com/";

        assertTrue(Util.isUrlValid(validUrl));
        assertFalse(Util.isUrlValid(invalidUrl));
    }
}
