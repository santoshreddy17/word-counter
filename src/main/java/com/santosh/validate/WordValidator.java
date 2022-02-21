package com.santosh.validate;

import com.santosh.exception.WordValidationException;

/**
 * Word Validator.
 */
public class WordValidator {

    /**
     * Validate the word.
     *
     * @param word word.
     */
    public void validate(final String word) {
        if (word == null || word.isEmpty()) {
            throw new WordValidationException("Passed in word is null or empty");
        }
        if (!word.matches("^[a-zA-Z\\s]*$")) {
            throw new WordValidationException("Passed in word has special characters or a number :" + word);
        }
    }
}
