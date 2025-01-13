package dictionary_client.dictionary_client.controllers;

import dictionary_client.dictionary_client.models.Word;
import dictionary_client.dictionary_client.services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class WordController {

    private final WordService wordService;

    @Autowired
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }


    @GetMapping("/word")
    public List<Word> getWord() {
        return wordService.findAllWords();
    }

    @GetMapping("/wordo")
    public List<String> getWordOriginal() {
        return wordService.findAllOriginal();
    }

    @PostMapping("/add")
    public void addWord() {
        Word word = new Word();
        word.setName("laptop");
     //   word.setTranslation("Ноутбук");
        word.setProgress(0);
        wordService.save(word);
    }

}
