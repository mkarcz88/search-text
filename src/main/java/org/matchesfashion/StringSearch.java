package org.matchesfashion;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class StringSearch {

    private static final int NUMBER_OF_MOST_FREQUENT_WORDS = 3;

    public String[] searchMostFrequentWords(String text) {
        text = normalizeText(text);

        if (text.length() == 0) {
            return new String[0];
        }

        Map<String, Integer> words = splitTextIntoSortedWords(text);

        return words.keySet().stream()
                .limit(NUMBER_OF_MOST_FREQUENT_WORDS)
                .toArray(String[]::new);
    }

    String normalizeText(String text) {
        return text.toLowerCase()
                .replace("\n", "")
                .replace("\r", "")
                .replace(",", "")
                .replace(".", "")
                .replace("\"", "")
                .replaceAll(" +", " ")
                .trim();
    }

    Map<String, Integer> splitTextIntoSortedWords(String text) {
        Map<String, Integer> words = new HashMap<>();
        List<String> wordsAsList = asList(text.split(" "));
        wordsAsList.forEach(word -> words.merge(word, 1, Integer::sum));

        return words.entrySet().stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

}
