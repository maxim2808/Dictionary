package dictionary_client.dictionary_client;

import dictionary_client.dictionary_client.models.Word;
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


    public static void main(String[] args) {
		SpringApplication.run(DictionaryClientApplication.class, args);

	}


    @Override
    public void run(String... args) throws Exception {
//        wordService.addWord("people", "люди");
//        wordService.addWord("mouse", "мышь");
//       wordService.addWord("tiger", "тигр");
       // wordService.edit("people", "толпа", 8);
        wordService.deleteWord(8);
        System.out.println(wordService.findAllWords());
    }


}
