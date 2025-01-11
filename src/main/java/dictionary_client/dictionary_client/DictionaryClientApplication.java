package dictionary_client.dictionary_client;

import dictionary_client.dictionary_client.models.Word;
import dictionary_client.dictionary_client.services.TranslationService;
import dictionary_client.dictionary_client.services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.*;

@SpringBootApplication
public class DictionaryClientApplication implements CommandLineRunner {
	@Autowired
	private WordService wordService;
    private TranslationService translationService;

    public DictionaryClientApplication(TranslationService translationService) {
        this.translationService = translationService;
    }


    public static void main(String[] args) {
		SpringApplication.run(DictionaryClientApplication.class, args);

	}


    @Override
    public void run(String... args) throws Exception {
        Word word = wordService.getWordById(7).get();
        System.out.println(wordService.getTranslationOneWord(word));


     //System.out.println(wordService.findAllWords());

    }


}
