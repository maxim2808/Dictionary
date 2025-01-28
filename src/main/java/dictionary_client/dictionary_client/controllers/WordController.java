package dictionary_client.dictionary_client.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import dictionary_client.dictionary_client.dto.WordDTO;
import dictionary_client.dictionary_client.models.Word;
import dictionary_client.dictionary_client.services.TranslationService;
import dictionary_client.dictionary_client.services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/words")
public class WordController {

    private final WordService wordService;
    private final TranslationService translationService;
    private final DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration;

    @Autowired
    public WordController(WordService wordService, TranslationService translationService, DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration) {
        this.wordService = wordService;
        this.translationService = translationService;
        this.dataSourceTransactionManagerAutoConfiguration = dataSourceTransactionManagerAutoConfiguration;
    }


    @GetMapping("")
    public String getWord(Model model) {
        model.addAttribute("allWords", wordService.findAllWords());
        return "/words/allWords";
    }

    @GetMapping("/{id}")
    public String getWord(@PathVariable int id, Model model) {
        model.addAttribute("word", wordService.getWordById(id).orElseThrow());
        return "/words/oneWord";
    }

    @GetMapping("/add")
    public String addWordGet(Model model) {

        WordDTO wordDTO = new WordDTO();
        model.addAttribute("word", wordDTO);
    return "/words/addWord";
    }

    @PostMapping("/add")
    public String addWordPost(@ModelAttribute("word") WordDTO wordDTO) {
        wordService.addWord(wordDTO.getName(), wordDTO.getTranslations());
        return "redirect:/words";
    }
    @GetMapping("/addFromServer")
    public String addWordFromServerGet(Model model) {
        model.addAttribute("name", new String());
        return "/words/addWordFromServer";
    }

    @PostMapping("/addFromServer")
    public String addWordFromServerPost(@ModelAttribute("name") String name) throws JsonProcessingException {
        wordService.getWordFromServer(name);
        return "redirect:/words";
    }





    @DeleteMapping("{id}/delete")
    public String deleteWord(@PathVariable int id) {
        wordService.deleteWord(id);
        return "redirect:/words";
    }

    @GetMapping("/{id}/edit")
    public String editWordGet(@PathVariable int id, Model model) {
        Word word = wordService.getWordById(id).orElseThrow();
;
        model.addAttribute("idModel", id);
        model.addAttribute("wordName", word.getName());
        model.addAttribute("word", word);
        String newTranslation = "";
        model.addAttribute("newTranslation", newTranslation);
;       return "/words/editWord";
    }

    @PatchMapping("/{id}/edit")
    public String editWordPost(@ModelAttribute("wordName") String name, @PathVariable int id) {
        System.out.println("new name: " + name);
        System.out.println("patch method id " + id);
        Word word = wordService.getWordById(id).orElseThrow();
        word.setName(name);
        System.out.println("word id " + word.getId());
        wordService.edit(word, id);
        return "redirect:/words/" + id;

    }

    @PostMapping("/{id}/edit")
    public String addNewTranslation(@PathVariable int id, @ModelAttribute("newTranslation") String translation) {
        System.out.println("post translation");
        System.out.println("new translation: " + translation);
        Word word = wordService.getWordById(id).orElseThrow();
        translationService.addTranslation(word, translation);
        return "redirect:/words/" + id+"/edit";
    }

    @DeleteMapping("/{id}/edit/{translationId}")
    public String deleteTraslation(@PathVariable int id, @PathVariable int translationId) {
        translationService.deleteTranslation(translationId);
        return "redirect:/words/" + id+"/edit";
    }




}
