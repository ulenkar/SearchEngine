package com.ulka;

import java.util.*;
import java.util.stream.Collectors;

public class WordRecord {

    private String word;
    private Map<String, Integer> documentsOccurings = new HashMap<>();
    private Map<String, Double> documentsTF = new HashMap<>();
    private Double idf;
    private Map<String, Double> documentsTFIDF = new LinkedHashMap<>();

    public WordRecord(String word) {
        this.word = word;
    }


    public void addOccuring(String docName){
        if (documentsOccurings.containsKey(docName)){
            documentsOccurings.replace(docName, documentsOccurings.get(docName) + 1);
        } else {
            documentsOccurings.put(docName, 1);
        }
    }

    public void countTFForDocument(String docName, int docLenght){
        if (documentsOccurings.containsKey(docName)) {
            documentsTF.put(docName, documentsOccurings.get(docName).doubleValue() / docLenght);
        }
    }

    public void countIDF(Integer nrOfDocuments){
        idf = Math.log( nrOfDocuments.doubleValue() / documentsOccurings.size());
    }

    public void countTFIDFs(){
        documentsTF.forEach((docName, tf) -> documentsTFIDF.put(docName, tf * idf));
    }

    public void sortTFIDF(){
        documentsTFIDF =
                documentsTFIDF.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByKey())
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    public String getDocumentsTFIDF() {
        return documentsTFIDF.keySet().toString();
    }
}
