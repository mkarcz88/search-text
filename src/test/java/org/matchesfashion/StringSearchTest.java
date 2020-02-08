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
        String text = "abc" + System.getProperty("line.separator") + "def";

        String expected = "abc" +
                "def";
        String actual = stringSearch.normalizeText(text);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void normalizeTextRemoveCommas() {
        String text = "ab,c";

        String expected = "abc";
        String actual = stringSearch.normalizeText(text);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void normalizeTextRemoveDots() {
        String text = "google.com";

        String expected = "googlecom";
        String actual = stringSearch.normalizeText(text);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void normalizeTextRemoveMultipleSpaces() {
        String text = "w     w    w";

        String expected = "w w w";
        String actual = stringSearch.normalizeText(text);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void normalizeTextRemoveStartingSpaces() {
        String text = "   www";

        String expected = "www";
        String actual = stringSearch.normalizeText(text);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void normalizeTextRemoveEndingSpaces() {
        String text = "www       ";

        String expected = "www";
        String actual = stringSearch.normalizeText(text);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void normalizeTextRemoveQuotes() {
        String text = "\"the end\"";

        String expected = "the end";
        String actual = stringSearch.normalizeText(text);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void normalizeTextReturnsOnlyLowerCaseLetters() {
        String text = "WWW";

        String expected = "www";
        String actual = stringSearch.normalizeText(text);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void normalizeTextPreserveApostrophe() {
        String text = "hasn't";

        String expected = "hasn't";
        String actual = stringSearch.normalizeText(text);
        assertThat(actual).isEqualTo(expected);
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
    public void searchMostFrequentWords() {
        String text = "In a village of La Mancha, the name of which I have\n" +
                "no desire to call to\n" +
                "mind, there lived not long since one of those gentlemen that keep a lance\n" +
                "in the lance-rack, an old buckler, a lean hack, and a greyhound for\n" +
                "coursing. An olla of rather more beef than mutton, a salad on most\n" +
                "nights, scraps on Saturdays, lentils on Fridays, and a pigeon or so extra\n" +
                "on Sundays, made away with three-quarters of his income.\")\n";

        String[] expected = {"a", "of", "on"};
        String[] actual = stringSearch.searchMostFrequentWords(text);

        assertThat(actual).containsExactly(expected);
    }

    @Test
    public void searchMostFrequentWords2() {
        String text = "e e e e DDD ddd DdD: ddd ddd aa aA Aa, bb cc cC e e e";

        String[] expected = {"e", "ddd", "aa"};
        String[] actual = stringSearch.searchMostFrequentWords(text);

        assertThat(actual).containsExactly(expected);
    }

    @Test
    public void searchMostFrequentWords3() {
        String text = "wont won't won't";

        String[] expected = {"won't", "wont"};
        String[] actual = stringSearch.searchMostFrequentWords(text);

        assertThat(actual).containsExactly(expected);
    }

    @Test
    public void searchMostFrequentWordsForEmptyText() {
        String text = "";

        String[] expected = {};
        String[] actual = stringSearch.searchMostFrequentWords(text);

        assertThat(actual).containsExactly(expected);
    }

    @Test
    public void searchMostFrequentWordsWhenOnly1Word() {
        String text = "cat";

        String[] expected = {"cat"};
        String[] actual = stringSearch.searchMostFrequentWords(text);

        assertThat(actual).containsExactly(expected);
    }

    @Test
    public void searchMostFrequentWordsWhenOnly2Words() {
        String text = "cat dog";

        String[] expected = {"cat", "dog"};
        String[] actual = stringSearch.searchMostFrequentWords(text);

        assertThat(actual).containsExactly(expected);
    }
}
