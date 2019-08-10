import com.ulka.WordRecord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordRecordTest {

    private static final String DOCUMENT_1 = "document1";
    private static final String DOCUMENT_2 = "document2";

    @Test
    public void testAddOccurring(){
        //GIVEN
        var wordRecord = new WordRecord();

        //WHEN
        wordRecord.addOccurring(DOCUMENT_1);
        wordRecord.addOccurring(DOCUMENT_1);

        //THEN
        assertEquals(2, wordRecord.getDocumentOccurrences(DOCUMENT_1));
    }

    @Test
    public void testNoAddOccurring(){
        //GIVEN
        var wordRecord = new WordRecord();

        //THEN
        assertEquals(0, wordRecord.getDocumentOccurrences(DOCUMENT_1));
    }

    @Test
    public void testCountTFForDocument(){
        //GIVEN
        var wordRecord = new WordRecord();
        wordRecord.addOccurring(DOCUMENT_1);
        wordRecord.addOccurring(DOCUMENT_1);

        //WHEN
        wordRecord.countTFForDocument(DOCUMENT_1,5);

        //THEN
        assertEquals(0.4, wordRecord.getDocumentTF(DOCUMENT_1));
    }

    @Test
    public void testCountIDFNoOccurenceInTwo(){
        //GIVEN
        var wordRecord = new WordRecord();
        Integer nrOfDocuments = 2;

        //WHEN
        wordRecord.countIDF(nrOfDocuments);

        //THEN
        assertEquals(Math.log(nrOfDocuments.doubleValue() / 0), wordRecord.getIdf());
    }

    @Test
    public void testCountIDFOneOccurenceInTwo(){
        //GIVEN
        var wordRecord = new WordRecord();
        Integer nrOfDocuments = 2;
        wordRecord.addOccurring(DOCUMENT_1);

        //WHEN
        wordRecord.countTFForDocument(DOCUMENT_1,5);
        wordRecord.countIDF(nrOfDocuments);

        //THEN
        assertEquals(Math.log(nrOfDocuments.doubleValue() / 1), wordRecord.getIdf());
    }

    @Test
    public void testCountIDFTwoOccurencesInTwo(){
        //GIVEN
        var wordRecord = new WordRecord();
        Integer nrOfDocuments = 2;
        wordRecord.addOccurring(DOCUMENT_1);
        wordRecord.addOccurring(DOCUMENT_2);

        //WHEN
        wordRecord.countTFForDocument(DOCUMENT_1,5);
        wordRecord.countTFForDocument(DOCUMENT_2,5);
        wordRecord.countIDF(nrOfDocuments);

        //THEN
        assertEquals(Math.log(nrOfDocuments.doubleValue() / 2), wordRecord.getIdf());
    }

    @Test
    public void testCountTFIDF(){
        //GIVEN
        var wordRecord = new WordRecord();
        Integer nrOfDocuments = 2;
        wordRecord.addOccurring(DOCUMENT_1);

        //WHEN
        wordRecord.countTFForDocument(DOCUMENT_1,2);
        wordRecord.countIDF(nrOfDocuments);
        wordRecord.countTFIDFs();

        //THEN
        assertEquals(wordRecord.getDocumentTF(DOCUMENT_1) * wordRecord.getIdf(),
                wordRecord.getDocumentTFIDF(DOCUMENT_1));
    }

    @Test
    public void testSortTFIDFByImportance(){
        var wordRecord = new WordRecord();
        Integer nrOfDocuments = 2;
        wordRecord.addOccurring(DOCUMENT_1);
        wordRecord.addOccurring(DOCUMENT_2);

        //WHEN
        wordRecord.countTFForDocument(DOCUMENT_1,2);
        wordRecord.countTFForDocument(DOCUMENT_2,4);
        wordRecord.countIDF(nrOfDocuments);
        wordRecord.countTFIDFs();
        wordRecord.sortTFIDF();

        //THEN
        assertEquals("[" + DOCUMENT_1 + ", " + DOCUMENT_2 + "]", wordRecord.getDocumentsSortedByTFIDF());
    }

    @Test
    public void testSortTFIDFByNrOfOccurrences(){
        var wordRecord = new WordRecord();
        Integer nrOfDocuments = 3;
        wordRecord.addOccurring(DOCUMENT_1);
        wordRecord.addOccurring(DOCUMENT_2);
        wordRecord.addOccurring(DOCUMENT_2);

        //WHEN
        wordRecord.countTFForDocument(DOCUMENT_1,5);
        wordRecord.countTFForDocument(DOCUMENT_2,5);
        wordRecord.countIDF(nrOfDocuments);
        wordRecord.countTFIDFs();
        wordRecord.sortTFIDF();

        //THEN
        assertEquals("[" + DOCUMENT_2 + ", " + DOCUMENT_1 + "]", wordRecord.getDocumentsSortedByTFIDF());
    }


}
