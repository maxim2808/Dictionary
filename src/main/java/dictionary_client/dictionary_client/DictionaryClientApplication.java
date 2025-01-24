package dictionary_client.dictionary_client;

import dictionary_client.dictionary_client.dto.WordDTO;
import dictionary_client.dictionary_client.services.KnowledgeTestService;
import dictionary_client.dictionary_client.services.TranslationService;
import dictionary_client.dictionary_client.services.WordService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DictionaryClientApplication implements CommandLineRunner {

	private WordService wordService;
    private TranslationService translationService;
    private KnowledgeTestService knowledgeTestService;

    public DictionaryClientApplication(WordService wordService, TranslationService translationService, KnowledgeTestService knowledgeTestService) {
        this.wordService = wordService;
        this.translationService = translationService;
        this.knowledgeTestService = knowledgeTestService;
    }

    public static void main(String[] args) {
		SpringApplication.run(DictionaryClientApplication.class, args);

	}


    @Override
    public void run(String... args) throws Exception {
//     WordDTO wordDTO = wordService.getWordFromServer("keep");
//        System.out.println(wordDTO);
//     wordService.saveWordFromServer(wordDTO, false);
//

        wordService.deleteWord(11);
      System.out.println(wordService.findAllWords());

      //   wordService.addOneTranslation("mouse", "мышь");

      //  knowledgeTestService.simpleTranslationTest();




   //   wordService.addOneTranslation("house", "домик");
//        List <String> list1 = new ArrayList<>();
//        list1.add("злой");
//        list1.add("раздраженный");
//        list1.add("недовольный");
//        wordService.addWord("angry", list1);
//        List <String> list2 = new ArrayList<>();
//        list2.add("мяч");
//        list2.add("мячик");
//        wordService.addWord("ball", list2);
//        List <String> list3 = new ArrayList<>();
//        list3.add("горечь");
//        list3.add("горький");
//        wordService.addWord("bitter", list3);
//
//
//
//        System.out.println(wordService.findAllWords());


    }


}
