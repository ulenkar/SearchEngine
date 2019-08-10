package com.ulka;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Finds and manages words in all documents.
 */
public class WordManager {

    private Map<String, WordRecord> words = new HashMap<>();

    private static final String NOT_FOUND_INFO = "not found";
    /**
     * Loads words from all documents and counts TFIDFs for each of them. Creates inverted index.
     * @param documents all documents from which words should be collected
     */
    public void loadAll(Map<String, String> documents) {
        documents.forEach(this::loadDocument);
        words.forEach((word, wordRecord) -> {
            wordRecord.countIDF(documents.size());
            wordRecord.countTFIDFs();
        });
    }

    private void loadDocument(String docName, String docText) {
        Scanner scanner = new Scanner(docText);
        int docLenght = 0;
        while (scanner.hasNext()){
            docLenght++;
            var word = scanner.next();
            if (!words.containsKey(word)){
                words.put(word, new WordRecord());
            }
            getWord(word).addOccurring(docName);
        }
        scanner.close();
        final int docLengthFinal = docLenght;
        words.forEach((word, wordRecord) -> wordRecord.countTFForDocument(docName, docLengthFinal));
    }

    /**
     * Sorts documents for each word by TF-IDF
     */
    public void sort() {
        words.forEach((word, wordRecord) -> wordRecord.sortTFIDF());
    }

    /**
     * Finds a word in created inverted index and returns list of matching documents
     * @param word Word to find
     * @return list of matching documents
     */
    public String find(String word) {
        if (words.containsKey(word)) {
            return getWord(word).getDocumentsSortedByTFIDF();
        } else {
            return NOT_FOUND_INFO;
        }
    }

    /**
     * Returns all information record for given word
     */
    public WordRecord getWord(String word) {
        if (words.containsKey(word)) {
            return words.get(word);
        } else {
            return null;
        }
    }
}
