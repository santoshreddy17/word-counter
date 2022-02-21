package com.santosh.translator;

/**
 * Translator interface as specified by spec.
 */
public interface Translator {

    /**
     * Translate from other languages to english
     *
     * @param word word
     * @return translated english word else null.
     */
    String translate(String word);
}
