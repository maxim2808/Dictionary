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


   public List<Word> getFixWordListForTest(){
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
        for(int i = 0; i<listWord.size(); i++){
            int number = i+1;

            String translationWithNumber = String.valueOf(number)+"."+getOneStringTranslationOneWord(listWord.get(i));

            listTranslationString.add(translationWithNumber);
        }

        return listTranslationString;
    }

    public String getOneStringTranslationOneWord(Word word){
        StringBuilder stringBuilder = new StringBuilder();
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
            word = allListWord.get(currentNumberInTest);
            return word;
        }




    public boolean checkAnswer(int incomingNumber){
        int answerNumber = incomingNumber;
        Word selectedWord = currentFixList.get(answerNumber);
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
            allListWord = null;
            initializeWordList();
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
