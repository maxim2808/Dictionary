package dictionary_client.dictionary_client.services;


import dictionary_client.dictionary_client.models.Translation;
import dictionary_client.dictionary_client.models.Word;
import dictionary_client.dictionary_client.repositories.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class WordService {

   private final WordRepository wordRepository;
   private final TranslationService translationService;

    @Autowired
    public WordService(WordRepository wordRepository, TranslationService translationService) {
        this.wordRepository = wordRepository;
        this.translationService = translationService;
    }

    public Optional<Word>findById(int id) {
        return wordRepository.findById(id);
    }


    public List<Word> findAllWords(){
     //   System.out.println(wordRepository.findAll());
        return wordRepository.findAll();
    }

    public List<String> findAllOriginal(){
      //  System.out.println("find all originals started");
        List<Word> words = findAllWords();
        List<String> originalWords = new ArrayList<>();
        for (Word word : words) {
            originalWords.add(word.getOriginal());
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
            word.setOriginal(original);
            List<Translation> translations = new ArrayList<>();
            word.setProgress(0);
            save(word);
            for (String translation : translationsString){
                Translation translationObj = new Translation();
                translationObj.setName(translation);
                translationObj.setWord(word);
                translations.add(translationObj);
                translationService.save(translationObj);
            }
            word.setTranslationList(translations);
            wordRepository.save(word);

            return true;
        }








    }


    @Transactional
    public void edit(String original, List<Translation> translations, int id){
        Word word = wordRepository.findById(id).get();
        word.setOriginal(original);
        word.setTranslationList(translations);
        wordRepository.save(word);
    }

    @Transactional
    public void deleteWord(int id){
        wordRepository.deleteById(id);
    }












}
