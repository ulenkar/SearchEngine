package com.ulka;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DocumentManager {

    private Map<String, WordRecord> words = new HashMap<>();

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
                words.put(word, new WordRecord(word));
            }
            words.get(word).addOccuring(docName);
        }
        scanner.close();
        final int docLengthFinal = docLenght;
        words.forEach((word, wordRecord) -> wordRecord.countTFForDocument(docName, docLengthFinal));
    }

    public void sort() {
        words.forEach((word, wordRecord) -> wordRecord.sortTFIDF());
    }

    public String find(String word) {
        if (words.containsKey(word)) {
            return words.get(word).getDocumentsTFIDF();
        } else {
            return "not found";
        }
    }
}
