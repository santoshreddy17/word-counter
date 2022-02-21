package com.santosh.counter;

import com.santosh.dictionary.EnglishDictionary;
import com.santosh.domain.Word;
import com.santosh.exception.WordCounterException;
import com.santosh.translator.Translator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Word Count Impl.
 */
public class WordCounterImpl implements WordCounter {

    final Map<Word, Integer> wordCountMap;
    final EnglishDictionary englishDictionary;
    final Translator translator;

    /**
     * Word Count Impl
     *
     * @param englishDictionary english dictionary
     * @param translator        translator.
     */
    public WordCounterImpl(final EnglishDictionary englishDictionary, final Translator translator) {
        wordCountMap = new ConcurrentHashMap<>();
        this.englishDictionary = englishDictionary;
        this.translator = translator;
    }

    /**
     * Add Word.
     * Assumptions made : Word to be considered english word, it is a valid dictionary word.
     * If it is not a valid dictionary word, translate is tried and if none is found it is rejected.
     *
     * @param stringWord word
     */
    @Override
    public void addWord(final String stringWord) throws WordCounterException {
        final Word word = new Word(stringWord);
        if (englishDictionary.isValid(word.getWord())) {
            wordCountMap.merge(word, 1, Integer::sum);
        } else {
            final String englishWord = translator.translate(word.getWord());
            if (englishWord != null) {
                wordCountMap.merge(new Word(englishWord), 1, Integer::sum);
            } else {
                throw new WordCounterException("Invalid word, no matching words found in english dictionary or translate for :" + word.getWord());
            }
        }
    }

    /**
     * Get Count.
     * Count is returned for english and same words in different languages
     *
     * @param stringWord word
     * @return Integer.
     */
    @Override
    public Integer getCount(final String stringWord) {
        final Word word = new Word(stringWord);
        if (!wordCountMap.containsKey(word)) {
            final String translate = translator.translate(stringWord);
            if (translate != null) {
                return wordCountMap.getOrDefault(new Word(translate), 0);
            }
        } else {
            return wordCountMap.getOrDefault(word, 0);
        }
        return 0;
    }
}
