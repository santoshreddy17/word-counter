package com.santosh.counter;

import com.santosh.AddWordRunnable;
import com.santosh.dictionary.EnglishDictionary;
import com.santosh.exception.WordCounterException;
import com.santosh.translator.Translator;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

public class WordCounterImplTest {


    @Test
    public void addWordSimple() throws WordCounterException {
        final EnglishDictionary mockEnglishDictionary = Mockito.mock(EnglishDictionary.class);
        final Translator mockTranslator = Mockito.mock(Translator.class);
        when(mockEnglishDictionary.isValid("spell")).thenReturn(true);
        when(mockEnglishDictionary.isValid("fruit")).thenReturn(true);
        when(mockEnglishDictionary.isValid("nut")).thenReturn(true);
        final WordCounter wordCounter = new WordCounterImpl(mockEnglishDictionary, mockTranslator);
        wordCounter.addWord("Spell");
        wordCounter.addWord("Spell");
        wordCounter.addWord("Spell");
        wordCounter.addWord("Fruit");
        wordCounter.addWord("FRUIT");
        wordCounter.addWord("NUt");
        Assert.assertEquals(Long.valueOf(3), wordCounter.getCount("Spell"));
        Assert.assertEquals(Long.valueOf(2), wordCounter.getCount("fruit"));
        Assert.assertEquals(Long.valueOf(1), wordCounter.getCount("nut"));
        Assert.assertEquals(Long.valueOf(0), wordCounter.getCount("rock"));
    }

    @Test
    public void addWordTranslate() throws WordCounterException {
        final EnglishDictionary mockEnglishDictionary = Mockito.mock(EnglishDictionary.class);
        final Translator mockTranslator = Mockito.mock(Translator.class);
        when(mockEnglishDictionary.isValid("spell")).thenReturn(true);
        when(mockEnglishDictionary.isValid("flower")).thenReturn(true);
        when(mockEnglishDictionary.isValid("flor")).thenReturn(false);
        when(mockEnglishDictionary.isValid("blume")).thenReturn(false);
        when(mockTranslator.translate("flor")).thenReturn("flower");
        when(mockTranslator.translate("blume")).thenReturn("flower");
        final WordCounter wordCounter = new WordCounterImpl(mockEnglishDictionary, mockTranslator);
        wordCounter.addWord("spell ");
        wordCounter.addWord("flower");
        wordCounter.addWord("flor");
        wordCounter.addWord("blume");
        Assert.assertEquals(Long.valueOf(1), wordCounter.getCount("Spell"));
        Assert.assertEquals(Long.valueOf(3), wordCounter.getCount("Flower"));
        //Testing reverse translate for count
        Assert.assertEquals(Long.valueOf(3), wordCounter.getCount("flor"));
        Assert.assertEquals(Long.valueOf(3), wordCounter.getCount("blume"));
    }

    @Test
    public void addWordTranslateException() throws WordCounterException {
        final EnglishDictionary mockEnglishDictionary = Mockito.mock(EnglishDictionary.class);
        final Translator mockTranslator = Mockito.mock(Translator.class);
        when(mockEnglishDictionary.isValid("mpls")).thenReturn(false);
        when(mockTranslator.translate("mpls")).thenReturn(null);
        final WordCounter wordCounter = new WordCounterImpl(mockEnglishDictionary, mockTranslator);
        final WordCounterException exception = assertThrows(WordCounterException.class, () -> wordCounter.addWord("mpls"));
        assertEquals("Invalid word, no matching words found in english dictionary or translate for :mpls", exception.getMessage());
        when(mockEnglishDictionary.isValid("spell")).thenReturn(true);
        wordCounter.addWord("spell");
        Assert.assertEquals(Long.valueOf(1), wordCounter.getCount("Spell"));
    }


    @Test
    public void addWordMulti() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final EnglishDictionary mockEnglishDictionary = Mockito.mock(EnglishDictionary.class);
        final Translator mockTranslator = Mockito.mock(Translator.class);
        when(mockEnglishDictionary.isValid("flower")).thenReturn(true);
        when(mockEnglishDictionary.isValid("play")).thenReturn(true);
        final WordCounter wordCounter = new WordCounterImpl(mockEnglishDictionary, mockTranslator);
        int barrierCount = 4001;
        final CyclicBarrier barrier = new CyclicBarrier(barrierCount);
        for (int i = 0; i < barrierCount - 1; i++) {
            if (i % 2 == 0) {
                executorService.submit(new AddWordRunnable(barrier, wordCounter, "flower"));
            } else {
                executorService.submit(new AddWordRunnable(barrier, wordCounter, "play"));
            }
        }
        Thread.sleep(1000);
        executorService.shutdown();
        Assert.assertEquals(2000, wordCounter.getCount("flower").intValue());
        Assert.assertEquals(2000, wordCounter.getCount("play").intValue());
    }


}