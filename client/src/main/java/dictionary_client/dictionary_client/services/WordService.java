package dictionary_client.dictionary_client.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dictionary_client.dictionary_client.dto.WordDTO;
import dictionary_client.dictionary_client.models.Translation;
import dictionary_client.dictionary_client.models.Word;
import dictionary_client.dictionary_client.repositories.WordRepository;
import dictionary_client.dictionary_client.utils.WordNotFoundException;
import dictionary_client.dictionary_client.utils.WordNotFoundResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class WordService {
    @Value("${progressAtATime}")
    int progressAtATime;

    @Value("${addressOfServer}")
    String addressOfServer;
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

    @Transactional
    public void edit(Word word, int id){
         word.setId(id);
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
            word.setProgress(0);
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
    public void changeName(String name, Word word){
        word.setName(name);
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
        Word word = wordRepository.findWordsByName(name).get();
        translationService.addTranslation(word, translationName);
    }



    @Transactional
    public void increaseProgress(Word word){
        if ((word.getProgress() + progressAtATime)>=100){
            word.setProgress(100);
        }
        else{word.setProgress(word.getProgress() + progressAtATime);}
        wordRepository.save(word);
    }

    @Transactional
    public void decreaseProgress(Word word){
        if ((word.getProgress() - progressAtATime)<=0){
            word.setProgress(0);
        }
        else { word.setProgress(word.getProgress() - progressAtATime);}
        wordRepository.save(word);
    }


    public ResponseEntity<String> getResponseFromServer(String wordName){

        RestTemplate template = new RestTemplate();
        String url = "http://"+addressOfServer+"/api/giveWord";
        String response;
        ;
        try {
            response = template.postForObject(url, wordName, String.class);
        }
        catch (HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND){
                String responseBody = e.getResponseBodyAsString();
                ObjectMapper mapper = new ObjectMapper();
                try {
                    WordNotFoundResponse message = mapper.readValue(responseBody, WordNotFoundResponse.class);
                    return new ResponseEntity<>(message.getMessage(), HttpStatus.NOT_FOUND);
                } catch (JsonProcessingException ex) {

                }

            }

            return new ResponseEntity<>("Слово не обнаружено", HttpStatus.NOT_FOUND);

        }

        catch (ResourceAccessException e){

            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK );
    }
    public ResponseEntity<WordDTO> getWordFromServer(String wordName) throws JsonProcessingException {
        ResponseEntity<String> responseEntity = getResponseFromServer(wordName);
        if(responseEntity.getStatusCode() == HttpStatus.OK){
        String response = responseEntity.getBody();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode obj = mapper.readTree(response);
        String nameFromResponse = obj.get("name").asText();
        JsonNode listNode = obj.get("translations");
        List<String> stringList = new ArrayList<>();
        for (JsonNode node : listNode){
            stringList.add(node.asText());
        }

        WordDTO wordDTO = new WordDTO();
        wordDTO.setName(nameFromResponse);
        wordDTO.setTranslations(stringList);
        return new ResponseEntity<>(wordDTO, HttpStatus.OK);
        }
        else {return new ResponseEntity<>(null, responseEntity.getStatusCode());}
    }

    public void saveWordFromServer(WordDTO wordDTO, boolean needSave){

        if (needSave==false||wordDTO==null)
        {
            return;
        }
        addWord(wordDTO.getName(), wordDTO.getTranslations());
    }

    public Optional<Word> getWordByName(String wordName){
        return wordRepository.findWordsByName(wordName);
    }


}
