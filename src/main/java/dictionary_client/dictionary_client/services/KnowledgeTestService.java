package dictionary_client.dictionary_client.services;

import dictionary_client.dictionary_client.models.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KnowledgeTestService {
    private final WordService wordService;

    @Value("${countWordInTest}")
  public int countWordInTest;
    List<Word> allListWord;
    int currentNumberInTest = 0;
    List<Word> currentFixList;
    Word word;
    boolean lastAnswerWasCorrect = true;




    public void setCountWordInTest(int countWordInTest) {
        this.countWordInTest = countWordInTest;
    }

    @Autowired
    public KnowledgeTestService(WordService wordService) {
        this.wordService = wordService;
    }


//    public void simpleTranslationTest(Model model){
//        List<Word> allWords = wordService.findAllWords();
//        System.out.println("allWords: " + allWords);
//        for (int i = 0; i < countWordInTest; i++) {
//            Word word = allWords.get(i);
//            List<Word> shuffleListWithFixSize = getFixWordListForTest(allWords, word, i, model);
//            System.out.println("word: " +word.getName());
//            System.out.println(getListTranslationWithNumbering(shuffleListWithFixSize));
//            boolean answer = checkAnswer(1, shuffleListWithFixSize, word);
//            if (answer) {
//                System.out.println("Правильный ответ");
//            }
//            else System.out.println("Неправильный ответ");
//        }
//        System.out.println("Тест закончен");
//    }

   public List<Word> getFixWordListForTest(int i){
        if(lastAnswerWasCorrect){
        initializeWordList();
        initializeWord();
        List<Word> list = new ArrayList<>();
        list.add(word);
        Random random = new Random();

        while (list.size() < countWordInTest){
            int index = random.nextInt(allListWord.size());
            Word randomWord = allListWord.get(index);
            if (!list.contains(randomWord)){
                list.add(randomWord);
            }
        }
        Collections.shuffle(list);
        currentFixList = list;
        return list;}
        else return currentFixList;
    }


    private List<String> getListTranslationWithNumbering(List<Word> listWord){

        List<String> listTranslationString = new ArrayList<>();
       // StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i<listWord.size(); i++){
            int number = i+1;

            String translationWithNumber = String.valueOf(number)+"."+getOneStringTranslationOneWord(listWord.get(i));

            listTranslationString.add(translationWithNumber);
        }

        return listTranslationString;
    }

    public String getOneStringTranslationOneWord(Word word){
        StringBuilder stringBuilder = new StringBuilder();
//        for(Translation translation:word.getTranslationList()){
//            stringBuilder.append(translation.getName()).append(", ");
//        }
        for (int i=0; i<word.getTranslationList().size(); i++){
            if (i+1==word.getTranslationList().size()){
                stringBuilder.append(word.getTranslationList().get(i).getName()).append("");
            }
            else {stringBuilder.append(word.getTranslationList().get(i).getName()).append(", ");}
        }
        return stringBuilder.toString();
    }

        public List<Word> initializeWordList(){
        if (allListWord == null){
            allListWord = wordService.findAllWords();
            Collections.shuffle(allListWord);
        }
        return allListWord;
        }

        public Word initializeWord(){
//        if (word==null){
//            word = allListWord.get(0);
//        }
//        return word;
            word = allListWord.get(currentNumberInTest);
            return word;
        }




    public boolean checkAnswer(int incomingNumber){

        System.out.println("Введите свой ответ");
        int answerNumber = incomingNumber;
        System.out.println("answerNumber: " + answerNumber);
        Word selectedWord = currentFixList.get(answerNumber);
        System.out.println("selectedWord: " + selectedWord + " word: " + word.getName());
        if (selectedWord.equals(word)){
            currentNumberInTest++;
            wordService.increaseProgress(word);
            lastAnswerWasCorrect = true;
            return true;
        }
        else{
            lastAnswerWasCorrect = false;
            wordService.decreaseProgress(word);
        return false;}
    }
    public Boolean testShouldBeFinished(){
        if(getCurrentNumberInTest()==getAllListWord().size()){
            finishTest();
            return true;
        }
        return false;
    }

    public void finishTest(){
            setCurrentNumberInTest(0);

    }


    public int getCountWordInTest() {
        return countWordInTest;
    }

    public List<Word> getAllListWord() {
        return allListWord;
    }

    public int getCurrentNumberInTest() {
        return currentNumberInTest;
    }

    public List<Word> getCurrentFixList() {
        return currentFixList;
    }

    public Word getWord() {
        return word;
    }

    public void setAllListWord(List<Word> allListWord) {
        this.allListWord = allListWord;
    }

    public void setCurrentNumberInTest(int currentNumberInTest) {
        this.currentNumberInTest = currentNumberInTest;
    }

    public void setCurrentFixList(List<Word> currentFixList) {
        this.currentFixList = currentFixList;
    }

    public void setWord(Word word) {
        this.word = word;
    }
}
