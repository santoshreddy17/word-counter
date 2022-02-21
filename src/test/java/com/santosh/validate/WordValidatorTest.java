package com.santosh.validate;

import com.santosh.exception.WordValidationException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class WordValidatorTest {

    @Test
    public void validateEmptyString() {
        final WordValidationException exception = assertThrows(WordValidationException.class, () -> new WordValidator().validate(""));
        assertEquals("Passed in word is null or empty", exception.getMessage());
    }

    @Test
    public void validateNullString() {
        final WordValidationException exception = assertThrows(WordValidationException.class, () -> new WordValidator().validate(null));
        assertEquals("Passed in word is null or empty", exception.getMessage());
    }

    @Test
    public void validateSpecialCharactersString() {
        final WordValidationException exception = assertThrows(WordValidationException.class, () -> new WordValidator().validate("$tring"));
        assertEquals("Passed in word has special characters or a number :$tring", exception.getMessage());
    }

    @Test
    public void validateNumbersString() {
        final WordValidationException exception = assertThrows(WordValidationException.class, () -> new WordValidator().validate("Str1ng"));
        assertEquals("Passed in word has special characters or a number :Str1ng", exception.getMessage());
    }

}