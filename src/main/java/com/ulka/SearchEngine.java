package com.ulka;

import java.util.Scanner;

/**
 * Search engine - enables user to get information about matching documents for given word.
 */
public class SearchEngine {

    /**
     * Initializes search engine - enables user to input word and outputs information about its occurrences.
     * @param wordsManager WordsManager object managing word and their occurrences in documents.
     */
    public static void init(WordsManager wordsManager) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to search engine! To exit enter \"exit\"");
        System.out.println("Enter word to search: ");
        String word;

        while (!"exit".equalsIgnoreCase(word = scanner.nextLine())) {
            String documentsList = wordsManager.find(word);
            System.out.println("Found documents: " + documentsList);
            System.out.println("Enter word to search: ");
        }
    }
}
