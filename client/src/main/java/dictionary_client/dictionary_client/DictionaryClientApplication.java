package dictionary_client.dictionary_client;

import dictionary_client.dictionary_client.dto.WordDTO;
import dictionary_client.dictionary_client.services.KnowledgeTestService;
import dictionary_client.dictionary_client.services.TranslationService;
import dictionary_client.dictionary_client.services.WordService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DictionaryClientApplication  {

//	private WordService wordService;
//    private TranslationService translationService;
//    private KnowledgeTestService knowledgeTestService;
//
//    public DictionaryClientApplication(WordService wordService, TranslationService translationService, KnowledgeTestService knowledgeTestService) {
//        this.wordService = wordService;
//        this.translationService = translationService;
//        this.knowledgeTestService = knowledgeTestService;
//    }

    public static void main(String[] args) {
		SpringApplication.run(DictionaryClientApplication.class, args);

	}




}
