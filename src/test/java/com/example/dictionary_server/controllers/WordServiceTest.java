package com.example.dictionary_server.controllers;

import com.example.dictionary_server.models.StringWord;
import com.example.dictionary_server.models.Translation;
import com.example.dictionary_server.models.Word;
import com.example.dictionary_server.repositories.WordRepository;
import com.example.dictionary_server.services.TranslationService;
import com.example.dictionary_server.services.WordService;
import com.example.dictionary_server.util.WordNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class WordServiceTest {

    @InjectMocks
    private WordService wordService;

    @Mock
    private WordRepository wordRepository;

    @Mock
    private TranslationService translationService;


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

    @Test
    void giveStringWordByNameReturnsStringWord(){
        Word word = getOneWord();
        Mockito.when(wordRepository.findWordByName("table")).thenReturn(Optional.of(word));
        StringWord stringWord = wordService.giveStringWordByName("table");
        Assertions.assertEquals("table", stringWord.getName());
        Assertions.assertEquals("столик", stringWord.getTranslations().get(1));
    }

    @Test
    void giveStringWordByNameReturnsThrowsException(){
        Assertions.assertThrows(WordNotFoundException.class, () -> wordService.giveStringWordByName("create"));
    }

}
