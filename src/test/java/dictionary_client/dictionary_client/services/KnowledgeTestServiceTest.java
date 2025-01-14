package dictionary_client.dictionary_client.services;

import dictionary_client.dictionary_client.models.Word;
import dictionary_client.dictionary_client.repositories.WordRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.query.JSqlParserUtils;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class KnowledgeTestServiceTest {

    @Value("${countWordInTest}")
    int countWordInTest;


    private WordServiceTest wordServiceTest;


    @InjectMocks
    private KnowledgeTestService knowledgeTestService;

    @Mock
    private WordService wordService;


    @BeforeEach
    public void getWords(){

        WordServiceTest ws = new WordServiceTest();
        //knowledgeTestService.countWordInTest = countWordInTest;

        Mockito.when(wordService.findAllWords()).thenReturn(ws.getWords());
    }




@Test
void testGetAllWords() {
    List <Word> listForTest = wordService.findAllWords();
    Assertions.assertEquals(6, listForTest.size());
}

@Test
    void getFixWordContainWords(){

    List <Word> listForTest = wordService.findAllWords();
    System.out.println("all words in test " + listForTest);
    System.out.println(knowledgeTestService.getFixWordListForTest(listForTest, listForTest.get(1)));
        Assertions.assertTrue(5<listForTest.size());

        for (Word word : listForTest) {
            Assertions.assertTrue(knowledgeTestService.getFixWordListForTest(listForTest, word).contains(word));
        }
}

@Test
    void testHasEnd(){
        List <Word> listForTest = wordService.findAllWords();
        Mockito.when(wordService.findAllWords()).thenReturn(listForTest);
    knowledgeTestService.simpleTranslationTest();
}






}
