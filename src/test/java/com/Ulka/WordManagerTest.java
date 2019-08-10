package com.Ulka;

import com.ulka.WordManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordManagerTest {

    private static final String DOCUMENT_1 = "document1";
    private static final String DOCUMENT_2 = "document2";
    private WordManager wordManager = new WordManager();
    private Map<String, String> documents = Map.of(
            DOCUMENT_1, "sample text is sample",
            DOCUMENT_2, "it sample is very nice");

    @BeforeEach
    public void loadDocuments() {
        wordManager.loadAll(documents);
        wordManager.sort();
    }

    @Test
    public void testLoadAllOccurrencesOfSample(){
        assertEquals(2, wordManager.getWord("sample").getDocumentOccurrences(DOCUMENT_1));
    }

    @Test
    public void testLoadAllDocumentTF(){
        assertEquals(1.0 / 4.0, wordManager.getWord("text").getDocumentTF(DOCUMENT_1));
    }

    @Test
    public void testLoadAllIDF(){
        assertEquals(Math.log(2.0 / 1.0), wordManager.getWord("text").getIdf());
        assertEquals(Math.log(2.0 / 2.0), wordManager.getWord("is").getIdf());
    }

    @Test
    public void testSortTwoOccurrencesInOneAndOneInTwo(){
        assertEquals("[" + DOCUMENT_1 + ", " + DOCUMENT_2 + "]",
                wordManager.getWord("sample").getDocumentsSortedByTFIDF());
    }

    @Test
    public void testSortOneOccurrenceInOne(){
        assertEquals("[" + DOCUMENT_2 + "]",
                wordManager.getWord("very").getDocumentsSortedByTFIDF());
    }

    @Test
    public void testFindTwoOccurrencesInOneAndOneInTwo(){
        assertEquals("[" + DOCUMENT_1 + ", " + DOCUMENT_2 + "]",
                wordManager.find("sample"));
    }

    @Test
    public void testFindOneOccurrenceInOne(){
        assertEquals("[" + DOCUMENT_2 + "]",
                wordManager.find("very"));
    }

    @Test
    public void testFindNoOccurrence(){
        assertEquals("not found",
                wordManager.find("maybe"));
    }
}
