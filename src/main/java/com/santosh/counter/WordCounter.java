package com.santosh.counter;

import com.santosh.exception.WordCounterException;

/**
 * Word Counter Interface.
 */
public interface WordCounter {

    /**
     * Add Word.
     *
     * @param word word to be added
     */
    void addWord(String word) throws WordCounterException;

    /**
     * Get Count.
     *
     * @param word word get the count of.
     * @return count of word passed.
     */
    Integer getCount(String word);
}
