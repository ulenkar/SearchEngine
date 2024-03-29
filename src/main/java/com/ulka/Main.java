package com.ulka;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        var documents = Map.of(
                "document1","the brown fox jumped over the brown dog",
                "document2", "the lazy brown dog sat in the corner",
                "document3", "the red fox bit the lazy dog");
        var wordManager = new WordManager();
        wordManager.loadAll(documents);
        wordManager.sort();
        SearchEngine.init(wordManager);
    }
}
