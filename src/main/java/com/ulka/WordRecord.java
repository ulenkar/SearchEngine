package com.ulka;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Gathers all information and methods needed to index one word. Contains information about documents where word occur.
 * Contains methods to count TF-IDF and later sort documents by this statistic.
 */
public class WordRecord {

    private Map<String, Integer> documentsOccurrences = new HashMap<>();
    private Map<String, Double> documentsTF = new HashMap<>();
    private Double idf;
    private Map<String, Double> documentsTFIDF = new LinkedHashMap<>();

    /**
     *  Adds information about one occurring of this word in given document
     * @param docName document where word occurred
     */
    public void addOccurring(String docName){
        if (documentsOccurrences.containsKey(docName)){
            documentsOccurrences.replace(docName, getDocumentOccurrences(docName) + 1);
        } else {
            documentsOccurrences.put(docName, 1);
        }
    }

    /**
     * Counts term frequency (TF) in given document. Its number of word occurrences divided by document length.
     * @param docName document name
     * @param docLenght document length (how many words document contains)
     */
    public void countTFForDocument(String docName, int docLenght){
        if (documentsOccurrences.containsKey(docName)) {
            documentsTF.put(docName, getDocumentOccurrences(docName).doubleValue() / docLenght);
        }
    }

    /**
     * Counts inverse document frequency (IDF) for this word. It is a measure of how much information the word provides
     * accross all documents.
     * @param nrOfDocuments total number of all analyzed documents
     */
    public void countIDF(Integer nrOfDocuments){
        idf = Math.log( nrOfDocuments.doubleValue() / documentsOccurrences.size());
    }

    /**
     * Counts TF-IDF for each of document where word occurs. It is TF multiplied by IDF.
     */
    public void countTFIDFs(){
        documentsTF.forEach((docName, tf) -> documentsTFIDF.put(docName, tf * idf));
    }

    /**
     * Sorts documents where word occurs by document name first and then by TF-IDF.
     */
    public void sortTFIDF(){
        documentsTFIDF =
                documentsTFIDF.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByKey())
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    /**
     * Returns documents where word occurs, sorted by TF-IDF
     * @return list of documents in which word occurs
     */
    public String getDocumentsSortedByTFIDF() {
        return documentsTFIDF.keySet().toString();
    }

    /**
     * @return number of occurrences of this word in given document
     */
    public Integer getDocumentOccurrences(String docName) {
        if (documentsOccurrences.containsKey(docName)) {
            return documentsOccurrences.get(docName);
        } else {
            return 0;
        }
    }

    /**
     * @return tf for given document for this word
     */
    public Double getDocumentTF(String docName) {
        return documentsTF.get(docName);
    }

    /**
     * @return Idf for this word
     */
    public Double getIdf() {
        return idf;
    }

    /**
     * @return TF-IDF for given document for this word
     */
    public Double getDocumentTFIDF(String docName) {
        return documentsTFIDF.get(docName);
    }
}
