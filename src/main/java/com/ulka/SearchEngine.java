package com.ulka;

import java.util.Scanner;

public class SearchEngine {

    public static void init(DocumentManager documentManager) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to search engine! To exit enter \"exit\"");
        System.out.println("Enter word to search: ");
        String word;

        while (!"exit".equalsIgnoreCase(word = scanner.nextLine())) {
            String documentsList = documentManager.find(word);
            System.out.println("Found documents: " + documentsList);
            System.out.println("Enter word to search: ");
        }
    }
}
