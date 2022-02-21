package com.santosh.dictionary;

/**
 * To find out if the word is a valid english word.
 */
public interface EnglishDictionary {

    /**
     * Is Valid.
     * Validates if the word passed is a valid english word.
     *
     * @param word word.
     * @return true if valid english dictionary word else false.
     */
    boolean isValid(String word);
}
