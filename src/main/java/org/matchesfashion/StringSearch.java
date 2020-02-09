package org.matchesfashion;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class StringSearch {

    private static final int NUMBER_OF_MOST_FREQUENT_WORDS = 3;
    private static final String SPECIAL_CHARACTERS_REG_EXP = "[\n\r\t,.\"]";
    private static final String MULTIPLE_SPACE_REG_EXP = " +";
    private static final String SPACE = " ";
    private static final String EMPTY_STRING = "";

    public String[] searchMostFrequentWords(String text) {
        text = normalizeText(text);

        if (text.isEmpty()) {
            return new String[0];
        }

        Map<String, Integer> words = splitTextIntoSortedWords(text);

        return words.keySet().stream()
                .limit(NUMBER_OF_MOST_FREQUENT_WORDS)
                .toArray(String[]::new);
    }

    String normalizeText(String text) {
        if (text == null) {
            return EMPTY_STRING;
        }

        return text.toLowerCase()
                .replaceAll(SPECIAL_CHARACTERS_REG_EXP, EMPTY_STRING)
                .replaceAll(MULTIPLE_SPACE_REG_EXP, SPACE)
                .trim();
    }

    Map<String, Integer> splitTextIntoSortedWords(String text) {
        Map<String, Integer> words = new HashMap<>();
        List<String> wordsAsList = asList(text.split(SPACE));
        wordsAsList.forEach(word -> words.merge(word, 1, Integer::sum));

        return words.entrySet().stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

}
