package dictionary_client.dictionary_client.services;

import dictionary_client.dictionary_client.models.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KnowledgeTestService {
    @Value("${countWordInTest}")
  public int countWordInTest;

    private final WordService wordService;

    @Autowired
    public KnowledgeTestService(WordService wordService) {
        this.wordService = wordService;
    }


    public void simpleTranslationTest(){
        List<Word> allWords = wordService.findAllWords();
        System.out.println("allWords: " + allWords);
        Scanner scanner = new Scanner(System.in);
        for (Word word : allWords) {
            List<Word> shuffleListWithFixSize = getFixWordListForTest(allWords, word);
            System.out.println("shuffleListWithFixSize: " + shuffleListWithFixSize);
           // System.out.println(getListTranslationWithNumbering(shuffleListWithFixSize));
            System.out.println(word.getName());
            System.out.println(getListTranslationWithNumbering(shuffleListWithFixSize));

            boolean answer = checkAnswer(scanner, shuffleListWithFixSize, word);
            if (answer) {
                System.out.println("Правильный ответ");
            }
            else System.out.println("Неправильный ответ");
        }
        System.out.println("Тест закончен");
    }

   public List<Word> getFixWordListForTest(List<Word> allWords, Word word){
        List<Word> list = new ArrayList<>();
        list.add(word);
        Random random = new Random();
       System.out.println("count words in test!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! "+ countWordInTest);
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


    private List<String> getListTranslationWithNumbering(List<Word> listWord){
        List<String> listTranslationString = new ArrayList<>();
       // StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i<listWord.size(); i++){
            int number = i+1;
            String translationWithNumber = String.valueOf(number)+"."+wordService.getOneStringTranslationOneWord(listWord.get(i));
            listTranslationString.add(translationWithNumber);
        }
        return listTranslationString;
    }

    private boolean checkAnswer(Scanner scanner, List<Word> listWord, Word correctWord){
        System.out.println("Введите свой ответ");
        int answerNumber = scanner.nextInt();
        Word selectedWord = listWord.get(answerNumber-1);
        if (selectedWord.equals(correctWord)){
            return true;
        }
        else return checkAnswer(scanner, listWord, correctWord);
    }








}
