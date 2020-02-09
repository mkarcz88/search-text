package org.matchesfashion;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class StringSearchTest {

    private StringSearch stringSearch;

    @Before
    public void init() {
        stringSearch = new StringSearch();
    }

    @Test
    public void normalizeTextRemoveNewLines() {
        String text = "abc\nd\tef\rz";
        assertThat(stringSearch.normalizeText(text)).isEqualTo("abcdefz");
    }

    @Test
    public void normalizeTextRemoveCommas() {
        String text = "ab,c";
        assertThat(stringSearch.normalizeText(text)).isEqualTo("abc");
    }

    @Test
    public void normalizeTextRemoveDots() {
        String text = "google.com";
        assertThat(stringSearch.normalizeText(text)).isEqualTo("googlecom");
    }

    @Test
    public void normalizeTextRemoveMultipleSpaces() {
        String text = "w     w    w";
        assertThat(stringSearch.normalizeText(text)).isEqualTo("w w w");
    }

    @Test
    public void normalizeTextRemoveStartingSpaces() {
        String text = "   www";
        assertThat(stringSearch.normalizeText(text)).isEqualTo("www");
    }

    @Test
    public void normalizeTextRemoveEndingSpaces() {
        String text = "www       ";
        assertThat(stringSearch.normalizeText(text)).isEqualTo("www");
    }

    @Test
    public void normalizeTextRemoveQuotes() {
        String text = "\"the end\"";
        assertThat(stringSearch.normalizeText(text)).isEqualTo("the end");
    }

    @Test
    public void normalizeTextReturnsOnlyLowerCaseLetters() {
        String text = "WWW";
        assertThat(stringSearch.normalizeText(text)).isEqualTo("www");
    }

    @Test
    public void normalizeTextPreserveApostrophe() {
        String text = "hasn't";
        assertThat(stringSearch.normalizeText(text)).isEqualTo("hasn't");
    }

    @Test
    public void splitTextIntoSortedWords() {
        String text = "cat cat dog dog cat bird";

        Map<String, Integer> actual = stringSearch.splitTextIntoSortedWords(text);

        assertThat(actual.keySet()).containsExactly("cat", "dog", "bird");
        assertThat(actual.get("cat")).isEqualTo(3);
        assertThat(actual.get("dog")).isEqualTo(2);
        assertThat(actual.get("bird")).isEqualTo(1);
    }

    @Test
    public void searchMostFrequentWordsInLongString() {
        String text = "In a village of La Mancha, the name of which I have\n" +
                "no desire to call to\n" +
                "mind, there lived not long since one of those gentlemen that keep a lance\n" +
                "in the lance-rack, an old buckler, a lean hack, and a greyhound for\n" +
                "coursing. An olla of rather more beef than mutton, a salad on most\n" +
                "nights, scraps on Saturdays, lentils on Fridays, and a pigeon or so extra\n" +
                "on Sundays, made away with three-quarters of his income.\")\n";

        assertThat(stringSearch.searchMostFrequentWords(text)).containsExactly("a", "of", "on");
    }

    @Test
    public void searchMostFrequentWordsInMediumString() {
        String text = "e e e e DDD ddd DdD: ddd ddd aa aA Aa, bb cc cC e e e";
        assertThat(stringSearch.searchMostFrequentWords(text)).containsExactly("e", "ddd", "aa");
    }

    @Test
    public void searchMostFrequentWordsInShortString() {
        String text = "wont won't won't";
        assertThat(stringSearch.searchMostFrequentWords(text)).containsExactly("won't", "wont");
    }

    @Test
    public void searchMostFrequentWordsForEmptyText() {
        String text = "";
        assertThat(stringSearch.searchMostFrequentWords(text)).isEmpty();
    }


    @Test
    public void searchMostFrequentWordsForNullText() {
        assertThat(stringSearch.searchMostFrequentWords(null)).isEmpty();
    }

    @Test
    public void searchMostFrequentWordsWhenOnly1Word() {
        String text = "cat";
        assertThat(stringSearch.searchMostFrequentWords(text)).containsExactly("cat");
    }

    @Test
    public void searchMostFrequentWordsWhenOnly2Words() {
        String text = "cat dog";
        assertThat(stringSearch.searchMostFrequentWords(text)).containsExactly("cat", "dog");
    }
}
