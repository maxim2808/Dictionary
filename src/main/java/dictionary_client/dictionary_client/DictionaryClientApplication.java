package dictionary_client.dictionary_client;

import dictionary_client.dictionary_client.models.Translation;
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
  // wordService.deleteWord(12);

   // System.out.println(wordService.findAllWords());
//        List <String> list = new ArrayList<>();
//        list.add("дом");
//        list.add("строение");
//        wordService.addWord("house", list);
//        System.out.println(wordService.findAllWords());

//        Translation translation = translationService.findTranslationById(2).get();
//        System.out.println(translation);
      System.out.println(translationService.findAll());
    }


}
