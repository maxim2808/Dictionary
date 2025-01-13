package dictionary_client.dictionary_client.services;

import dictionary_client.dictionary_client.models.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class KnowledgeTestService {
    @Value("${countWordInTest}")
    int countWordInTest;

    private final WordService wordService;

    @Autowired
    public KnowledgeTestService(WordService wordService) {
        this.wordService = wordService;
    }


    public void simpleTranslationTest(){
        List<Word> allWords = wordService.findAllWords();
        System.out.println("allWords: " + allWords);
        for (Word word : allWords) {
            System.out.println(getListTranslationWithNumbering(getFixWordListForTest(allWords, word)));
            System.out.println("\n");
        }
    }

    public List<Word> getFixWordListForTest(List<Word> allWords, Word word){
        List<Word> list = new ArrayList<>();
        list.add(word);
        Random random = new Random();
        while (list.size() < countWordInTest){
            int index = random.nextInt(allWords.size());
            Word randomWord = allWords.get(index);
            if (!list.contains(randomWord)){
                list.add(randomWord);
            }
        }
        Collections.shuffle(list);
        return list;
    }


    List<String> getListTranslationWithNumbering(List<Word> listWord){
        List<String> listTranslationString = new ArrayList<>();
       // StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i<listWord.size(); i++){
            int number = i+1;
            String translationWithNumber = String.valueOf(number)+"."+wordService.getOneStringTranslationOneWord(listWord.get(i));
            listTranslationString.add(translationWithNumber);
        }
        return listTranslationString;
    }

    //public boolean








}
