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
import org.mockito.stubbing.LenientStubber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.query.JSqlParserUtils;
import static org.mockito.Mockito.lenient;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@ExtendWith(MockitoExtension.class)
public class KnowledgeTestServiceTest {

    @Value("${countWordInTest}")
    int countWordInTest;
    @Value("${testValue}")
    int testValue;


    private WordServiceTest wordServiceTest;


    @InjectMocks
    private KnowledgeTestService knowledgeTestService;
       @Mock
    private WordService wordService;

//


    @BeforeEach()
    public void getWords(){

        WordServiceTest ws = new WordServiceTest();
        //knowledgeTestService.countWordInTest = countWordInTest;

      Mockito.lenient().when(wordService.findAllWords()).thenReturn(ws.getWords());
    }

    @BeforeEach()
    public void setCountWordInTest(){
        knowledgeTestService.setCountWordInTest(6);
    }




@Test
void testGetAllWords() {
    System.out.println("count word in test " +  countWordInTest);
    System.out.println("test value " + testValue);
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
void everyWordInListIsUniqueInTetFixWordListForTest(){


    List <Word> listForTest = wordService.findAllWords();
  for (Word word : listForTest) {
      List<Word> list = knowledgeTestService.getFixWordListForTest(listForTest,word);
      Assertions.assertEquals(1, countWordInList(word,list));
  }



}

    private int countWordInList(Word word, List<Word> listForTest) {
        int i = 0;
        for (Word word1 : listForTest) {
            if (word.equals(word1)) {
                i++;
            }
        }
        return i;
    }

@Test
    void testHasEnd(){
    Scanner scanner = Mockito.mock(Scanner.class);
    Mockito.doAnswer(invocation -> new Random().nextInt(knowledgeTestService.countWordInTest) + 1).when(scanner).nextInt();
    knowledgeTestService.setCountWordInTest(6);
        List <Word> listForTest = wordService.findAllWords();
        Mockito.when(wordService.findAllWords()).thenReturn(listForTest);
    knowledgeTestService.simpleTranslationTest(scanner);
}
}
