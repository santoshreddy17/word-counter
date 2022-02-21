package com.santosh.domain;

import org.junit.Assert;
import org.junit.Test;

public class WordTest {

    @Test
    public void getWord() {
        final Word word = new Word("  string  ");
        Assert.assertEquals("string", word.getWord());
    }

    @Test
    public void testToString() {
        final Word word = new Word("string");
        Assert.assertEquals("Word{word='string'}", word.toString());
    }

    @Test
    public void testEquals() {
        Assert.assertEquals(new Word("string"), new Word("string"));
    }


}