package dictionary_client.dictionary_client.services;


import dictionary_client.dictionary_client.models.Translation;
import dictionary_client.dictionary_client.models.Word;
import dictionary_client.dictionary_client.repositories.TranslationRepository;
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
import java.util.Arrays;
import java.util.List;

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

    @Test
    public void findAllOriginalSizeShouldBe3(){
//        List<Word> words = getWords();
//        Mockito.when(wordService.findAllWords()).thenReturn(words);
        List<Word> word =  replaceList();
        List<String> listOriginalWords = wordService.findAllOriginal();
        Assertions.assertEquals(listOriginalWords.size(), 3);
        Assertions.assertEquals("grass", listOriginalWords.get(2));
    }

    @Test
    public void wordShouldBeAdded(){
//        List<Translation> translationList = new ArrayList<>();
//        Translation translation1 = new Translation();
//        translation1.setId(7);
//        translation1.setName("автобус");
//        translationList.add(translation1);
//
//        Translation translation2 = new Translation();
//        translation2.setId(8);
//        translation2.setName("бус");
//        translationList.add(translation2);

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
        Assertions.assertTrue(words.get(3).getOriginal().equals("bus"));
        Assertions.assertEquals(wordService.getTranslationOneWord(words.get(3)).equals(listForCheck), true);
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
        List<Translation> list1 = new ArrayList<>();
        Translation translation1Word1 = new Translation();
        translation1Word1.setName("Книга");
        translation1Word1.setId(1);
        list1.add(translation1Word1);
        word1.setTranslationList(list1);
        word1.setProgress(0);



        List<Translation> list2 = new ArrayList<>();
        Translation translation1Word2 = new Translation();
        translation1Word2.setName("машина");
        translation1Word2.setId(2);
        list2.add(translation1Word2);
        Translation translation2Word2 = new Translation();
        translation2Word2.setId(3);
        translation2Word2.setName("автомобиль");
        list2.add(translation2Word2);
        Translation translation3Word2 = new Translation();
        translation3Word2.setId(4);
        translation3Word2.setName("легковой автомобиль");
        list2.add(translation3Word2);

        Word word2 = new Word();
        word2.setId(2);
        word2.setTranslationList(list2);
        word2.setOriginal("car");
        word2.setProgress(0);


        List<Translation> list3 = new ArrayList<>();
        Translation translation1Word3 = new Translation();
        translation1Word3.setId(5);
        translation1Word3.setName("трава");
        list3.add(translation1Word3);

        Translation translation2Word3 = new Translation();
        translation2Word3.setId(6);
        translation2Word3.setName("газон");
        list3.add(translation2Word3);


        Word word3 = new Word();
        word3.setId(3);
        word3.setOriginal("grass");
        word3.setTranslationList(list3);
        word3.setProgress(0);
        List<Word> words = new ArrayList<>();
        words.add(word1);
        words.add(word2);
        words.add(word3);
        return words;
    }



}
