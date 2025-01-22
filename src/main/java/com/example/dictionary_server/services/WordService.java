package com.example.dictionary_server.services;

import com.example.dictionary_server.models.StringWord;
import com.example.dictionary_server.models.Translation;
import com.example.dictionary_server.models.Word;
import com.example.dictionary_server.repositories.WordRepository;
import com.example.dictionary_server.util.WordNotFoundException;
import com.example.dictionary_server.util.WordNotFoundResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WordService {
    private WordRepository wordRepository;
    private TranslationService translationService;


    public Optional<Word> findById(int id) {
        return wordRepository.findById(id);
    }


    public List<Word> findAllWords(){
        return wordRepository.findAll();
    }

    public List<String> findAllOriginal(){
        //  System.out.println("find all originals started");
        List<Word> words = findAllWords();
        List<String> originalWords = new ArrayList<>();
        for (Word word : words) {
            originalWords.add(word.getName());
        }
        return originalWords;
    }


    public List<String> getTranslationOneWord(Word word){
        List<String> list = new ArrayList<>();
        for(Translation translation:word.getTranslationList()){
            list.add(translation.getName());
        }
        return list;
    }

    public Optional<Word> getWordById(int id){
        return wordRepository.findById(id);
    }

    @Transactional()
    public void save(Word word){
        word.setRegistrationDate(new Date());
        wordRepository.save(word);
    }


    public boolean listContainWord(String word){
        return findAllOriginal().contains(word);
    }

    public boolean addWord(String original, List<String> translationsString){

        if(listContainWord(original)){

            return false;
        }
        else {
            Word word = new Word();
            word.setName(original);
            List<Translation> translations = new ArrayList<>();
            save(word);
            for (String translation : translationsString){
                Translation translationObj = new Translation();
                translationObj.setName(translation);
                translationObj.setWord(word);
                translations.add(translationObj);
                translationObj.setRegistrationDate(new Date());
                translationService.save(translationObj);
            }
            word.setTranslationList(translations);
            save(word);

            return true;
        }
    }


    @Transactional
    public void edit(String original, List<Translation> translations, int id){
        Word word = wordRepository.findById(id).get();
        word.setName(original);
        word.setTranslationList(translations);
        wordRepository.save(word);
    }

    @Transactional
    public void deleteWord(int id){
        wordRepository.deleteById(id);
    }

    @Transactional
    public void deleteOneTranslation(int wordId, String translationName){
        Word word = wordRepository.findById(wordId).get();
        Translation translation =translationService.findTranslationByName(word.getTranslationList(), translationName).get();
        translationService.deleteTranslation(translation.getId());

    }

    @Transactional
    public void addOneTranslation(String name, String translationName){
        Word word = wordRepository.findWordByName(name).get();
        translationService.addTranslation(word, translationName);
    }


    public StringWord giveStringWordByName(String wordName){
        Word word = findWordByName(wordName);
        List<String> listStringTranslation = getTranslationOneWord(word);
        StringWord stringWord = new StringWord();
        stringWord.setName(word.getName());
        stringWord.setTranslations(listStringTranslation);
        return stringWord;
    }

    public Word findWordByName(String wordName){
        return wordRepository.findWordByName(wordName).orElseThrow(WordNotFoundException::new);
    }






}
