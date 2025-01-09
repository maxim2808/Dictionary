package dictionary_client.dictionary_client.services;


import dictionary_client.dictionary_client.models.Word;
import dictionary_client.dictionary_client.repositories.WordRepository;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class WordServiceTest {

    @InjectMocks
    private WordService wordService;
    @Mock
    private WordRepository wordRepository;

    @Test
    public void findAllOriginalSizeShouldBe3(){
//        List<Word> words = getWords();
//        Mockito.when(wordService.findAllWords()).thenReturn(words);
        List<Word> word =  replaceList();
        List<String> listOriginalWords = wordService.findAllOriginal();
        Assertions.assertEquals(listOriginalWords.size(), 3);
        Assertions.assertEquals("car", listOriginalWords.get(2));
    }

    @Test
    public void wordShouldBeAdded(){
        List<Word> words =  replaceList();
        Mockito.doAnswer(invocation -> {
            Word word = invocation.getArgument(0);
            words.add(word);
            return null;
        }).when(wordRepository).save(Mockito.any(Word.class));
        wordService.addWord("bus", "автобус");
        Assertions.assertTrue(words.get(3).getOriginal().equals("bus"));
        Assertions.assertNotNull(words.get(3).getRegistrationDate());
    }




    @Test
    public void listContainsWordShouldBeTrue(){
        List<Word> words =  replaceList();
        Assertions.assertTrue(wordService.listContainWord("grass"));
    }

    @Test
    public void listContainsWordShouldBeFalse(){
        List<Word> words =  replaceList();
        Assertions.assertFalse(wordService.listContainWord("calculator"));
    }





    private List<Word> replaceList(){
        List<Word> words = getWords();
        Mockito.when(wordService.findAllWords()).thenReturn(words);
        return words;
    }

    private List<Word> getWords(){
        Word word1 = new Word();
        word1.setId(1);
        word1.setOriginal("book");
        word1.setTranslation("книга");
        word1.setProgress(0);
        Word word2 = new Word();
        word2.setId(2);
        word2.setOriginal("grass");
        word2.setTranslation("трава");
        word2.setProgress(0);
        Word word3 = new Word();
        word3.setId(3);
        word3.setOriginal("car");
        word3.setTranslation("машина");
        word3.setProgress(0);
        List<Word> words = new ArrayList<>();
        words.add(word1);
        words.add(word2);
        words.add(word3);
        return words;
    }



}
