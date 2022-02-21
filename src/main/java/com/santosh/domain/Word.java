package com.santosh.domain;

import com.santosh.validate.WordValidator;

import java.util.Locale;
import java.util.Objects;

/**
 * Immutable representation of word.
 */
public final class Word {

    private final String word;

    public Word(final String word) {
        final WordValidator validator = new WordValidator();
        validator.validate(word);
        this.word = word.trim().toLowerCase(Locale.ROOT);
    }

    public String getWord() {
        return word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return word.equals(word1.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                '}';
    }
}
