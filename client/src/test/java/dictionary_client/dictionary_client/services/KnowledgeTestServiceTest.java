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
import static org.mockito.Mockito.times;

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




    @BeforeEach()
    public void getWords(){
        WordServiceTest ws = new WordServiceTest();

      Mockito.lenient().when(wordService.findAllWords()).thenReturn(ws.getWords());
    }

    @BeforeEach()
    public void setCountWordInTest(){
        knowledgeTestService.setCountWordInTest(6);
    }

@Test
void testGetAllWords() {
    List <Word> listForTest = wordService.findAllWords();
    Assertions.assertEquals(6, listForTest.size());
}


    @Test
    void testCheckAnswerShouldBeTrue(){
        knowledgeTestService.getFixWordListForTest();
        Assertions.assertTrue(testHaveCorrectAnswer());
    }

    @Test
    void testHaveFinish(){
     //   KnowledgeTestService knowledgeTestServiceSpy = Mockito.spy(knowledgeTestService);
        int count = 0;
        while (true){
            knowledgeTestService.getFixWordListForTest();
            testHaveCorrectAnswer();
            if (
                   knowledgeTestService.testShouldBeFinished()

            )
            {
                count++;
                break;
            }
        }
        Assertions.assertEquals(1, count);
      // Mockito.verify(knowledgeTestService, times(1)).testShouldBeFinished();

    }




@Test
void everyWordInListIsUniqueInTetFixWordListForTest(){

    List <Word> listForTest = wordService.findAllWords();
  for (Word word : listForTest) {
      List<Word> list = knowledgeTestService.getFixWordListForTest();
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

    private boolean testHaveCorrectAnswer(){
        boolean correctAnswer = false;
        for (int i = 0; i < 6; i++) {
            correctAnswer = knowledgeTestService.checkAnswer(i);
            if (correctAnswer) {
                break;
            }
        }
        return correctAnswer;
    }





}
