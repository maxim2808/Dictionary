package dictionary_client.dictionary_client.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import dictionary_client.dictionary_client.dto.WordDTO;
import dictionary_client.dictionary_client.models.Translation;
import dictionary_client.dictionary_client.models.Word;
import dictionary_client.dictionary_client.repositories.TranslationRepository;
import dictionary_client.dictionary_client.repositories.WordRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
public class WordServiceTest {

    @Mock
    private TranslationService translationService;
    @Mock
    private TranslationRepository translationRepository;

    @InjectMocks
    private WordService wordService;
    @Mock
    private WordRepository wordRepository;

    @Value("4")
    int progressAtATime;
    @Test
    public void findAllOriginalSizeShouldBe6(){
//        List<Word> words = getWords();
//        Mockito.when(wordService.findAllWords()).thenReturn(words);
        List<Word> word =  replaceList();
        List<String> listOriginalWords = wordService.findAllOriginal();
        Assertions.assertEquals(listOriginalWords.size(), 6);
        Assertions.assertEquals("apple", listOriginalWords.get(2));
    }

    @Test
    public void wordShouldBeAdded(){


        List<Translation> translations = new ArrayList<>();
        List<Word> words =  replaceList();


        Mockito.doAnswer(invocation->{
            Translation translation = invocation.getArgument(0);
            translations.add(translation);
            return null;
        }).when(translationService).save(Mockito.any(Translation.class));



        Mockito.doAnswer(invocation -> {
            Word word = invocation.getArgument(0);
            words.add(word);
            return null;
        }).when(wordRepository).save(Mockito.any(Word.class));

        List<String> trasnlationString =new ArrayList<>();
        trasnlationString.add("автобус");
        trasnlationString.add("бус");
        wordService.addWord("bus", trasnlationString);
        List<String> listForCheck = new ArrayList<>();
        listForCheck.add("автобус");
        listForCheck.add("бус");
        Assertions.assertTrue(words.get(6).getName().equals("bus"));
        Assertions.assertEquals(wordService.getTranslationOneWord(words.get(6)).equals(listForCheck), true);
        Assertions.assertNotNull(words.get(6).getRegistrationDate());
    }




    @Test
    public void listContainsWordShouldBeTrue(){
        List<Word> words =  replaceList();
        Assertions.assertTrue(wordService.listContainWord("apple"));
    }

    @Test
    public void listContainsWordShouldBeFalse(){
        List<Word> words =  replaceList();
        Assertions.assertFalse(wordService.listContainWord("calculator"));
    }

    @Test
    public void theWordFromServerCanBeAdded() throws JsonProcessingException {
        String responseFromServer = "{\"name\":\"dog\",\"translations\":[\"собака\",\"собачка\",\"пёс\"]}";
        ResponseEntity <String> responseEntity = ResponseEntity.ok(responseFromServer);
        WordService wordService1 = wordService;
        WordService spy = Mockito.spy(wordService1);
        Mockito.doReturn(responseEntity).when(spy).getResponseFromServer("dog");
        Assertions.assertEquals("dog", spy.getWordFromServer("dog").getBody().getName());
    }


    @Test
    void maximumProgressEquals100(){
        replaceList();
        ReflectionTestUtils.setField(wordService, "progressAtATime", 4);
        Word word = wordService.findAllWords().get(2);
        for (int i = 0; i < 200; i++) {
            wordService.increaseProgress(word);
        }
        Assertions.assertEquals(100, word.getProgress());
    }

    @Test
    void minimumProgressEquals0(){
        replaceList();
        ReflectionTestUtils.setField(wordService, "progressAtATime", 4);
        Word word = wordService.findAllWords().get(1);
        word.setProgress(50);
        for (int i = 0; i < 200; i++) {
            wordService.decreaseProgress(word);
        }
        Assertions.assertEquals(0, word.getProgress());
    }




    private List<Word> replaceList(){
        List<Word> words = getWords();
        when(wordService.findAllWords()).thenReturn(words);
        return words;
    }

//


    public Word getOneWord(){
        Word word = new Word();
        List<Translation> list = new ArrayList<>();
        Translation translation = new Translation();
        translation.setId(1);
        translation.setWord(word);
        translation.setName("стол");
        Translation translation2 = new Translation();
        translation2.setId(2);
        translation2.setWord(word);
        translation2.setName("столик");
        list.add(translation);
        list.add(translation2);
        word.setId(1);
        word.setName("table");
        word.setTranslationList(list);
        return word;
    }


public List<Word> getWords(){
    // Слово 1
    Word word1 = new Word();
    word1.setId(1);
    word1.setName("book");
    List<Translation> list1 = new ArrayList<>();
    Translation translation1Word1 = new Translation();
    translation1Word1.setName("Книга");
    translation1Word1.setId(1);
    list1.add(translation1Word1);
    word1.setTranslationList(list1);
    word1.setProgress(0);

    // Слово 2
    Word word2 = new Word();
    word2.setId(2);
    word2.setName("car");
    List<Translation> list2 = new ArrayList<>();
    Translation translation1Word2 = new Translation();
    translation1Word2.setName("машина");
    translation1Word2.setId(2);
    list2.add(translation1Word2);
    Translation translation2Word2 = new Translation();
    translation2Word2.setName("автомобиль");
    translation2Word2.setId(3);
    list2.add(translation2Word2);
    Translation translation3Word2 = new Translation();
    translation3Word2.setName("легковой автомобиль");
    translation3Word2.setId(4);
    list2.add(translation3Word2);
    word2.setTranslationList(list2);
    word2.setProgress(0);

    // Слово 3
    Word word3 = new Word();
    word3.setId(3);
    word3.setName("apple");
    List<Translation> list3 = new ArrayList<>();
    Translation translation1Word3 = new Translation();
    translation1Word3.setName("яблоко");
    translation1Word3.setId(5);
    list3.add(translation1Word3);
    Translation translation2Word3 = new Translation();
    translation2Word3.setName("яблоня");
    translation2Word3.setId(6);
    list3.add(translation2Word3);
    word3.setTranslationList(list3);
    word3.setProgress(0);

    // Слово 4
    Word word4 = new Word();
    word4.setId(4);
    word4.setName("computer");
    List<Translation> list4 = new ArrayList<>();
    Translation translation1Word4 = new Translation();
    translation1Word4.setName("компьютер");
    translation1Word4.setId(6);
    list4.add(translation1Word4);
    word4.setTranslationList(list4);
    word4.setProgress(0);

    // Слово 5
    Word word5 = new Word();
    word5.setId(5);
    word5.setName("dog");
    List<Translation> list5 = new ArrayList<>();
    Translation translation1Word5 = new Translation();
    translation1Word5.setName("собака");
    translation1Word5.setId(7);
    list5.add(translation1Word5);
    word5.setTranslationList(list5);
    word5.setProgress(0);

    // Слово 6
    Word word6 = new Word();
    word6.setId(6);
    word6.setName("house");
    List<Translation> list6 = new ArrayList<>();
    Translation translation1Word6 = new Translation();
    translation1Word6.setName("дом");
    translation1Word6.setId(8);
    list6.add(translation1Word6);
    word6.setTranslationList(list6);
    word6.setProgress(0);

    List<Word> words = new ArrayList<>();
    words.add(word1);
    words.add(word2);
    words.add(word3);
    words.add(word4);
    words.add(word5);
    words.add(word6);

    return words;
}

}
