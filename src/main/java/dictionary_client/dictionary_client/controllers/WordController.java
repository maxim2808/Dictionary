package dictionary_client.dictionary_client.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import dictionary_client.dictionary_client.dto.WordDTO;
import dictionary_client.dictionary_client.models.Word;
import dictionary_client.dictionary_client.services.TranslationService;
import dictionary_client.dictionary_client.services.WordService;
import dictionary_client.dictionary_client.utils.WordValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/words")
public class WordController {

    private final WordService wordService;
    private final TranslationService translationService;
    private final DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration;
    private final WordValidator wordValidator;

    @Autowired
    public WordController(WordService wordService, TranslationService translationService, DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration, WordValidator wordValidator) {
        this.wordService = wordService;
        this.translationService = translationService;
        this.dataSourceTransactionManagerAutoConfiguration = dataSourceTransactionManagerAutoConfiguration;
        this.wordValidator = wordValidator;
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
    public String addWordPost(@ModelAttribute("word") @Valid WordDTO wordDTO, BindingResult bindingResult) {
        wordValidator.validate(wordDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/words/addWord";
        }
        wordService.addWord(wordDTO.getName(), wordDTO.getTranslations());
        return "redirect:/words";
    }
//    @GetMapping("/addFromServer")
//    public String addWordFromServerGet(Model model) {
//        model.addAttribute("name", new String());
//        return "/words/addWordFromServer";
//    }
//
//    @PostMapping("/addFromServer")
//    public String addWordFromServerPost(@ModelAttribute("name") String name,) throws JsonProcessingException {
//        System.out.println("post method name = " + name);
//        WordDTO wordDTO = wordService.getWordFromServer(name);
//
//        return "redirect:/words";
//    }

        @GetMapping("/addFromServer")
    public String addWordFromServerPost(Model model, @ModelAttribute("name") String name) throws JsonProcessingException {
        model.addAttribute("name", new String());

        if (name!=null&&!name.isEmpty()) {
            ResponseEntity<WordDTO> response = wordService.getWordFromServer(name);
           if (response.getStatusCode().is2xxSuccessful()) {
            WordDTO wordDTO = wordService.getWordFromServer(name).getBody();
            model.addAttribute("word", wordDTO);
           }
           if(response.getStatusCode()==HttpStatus.INTERNAL_SERVER_ERROR){
               String message = "Не удалось подключиться к серверу, попробуйте добавить слово вручную";
               model.addAttribute("message", message);
            }
           if(response.getStatusCode()==HttpStatus.NOT_FOUND){
               String message = "Слово не было найдено, попробуйте добавить слово вручную";
               model.addAttribute("message", message);
           }

        }

     return "/words/addWordFromServer";
    }

        @PostMapping("/addFromServer")
    public String addWordFromServerPost(@ModelAttribute("word")  @Valid WordDTO wordDTO, BindingResult bindingResult) throws JsonProcessingException {
        wordValidator.validate(wordDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/words/addWordFromServer";
        }
        wordService.saveWordFromServer(wordDTO, true);

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
;       WordDTO wordDTO = new WordDTO();
        wordDTO.setName(word.getName());
        model.addAttribute("idModel", id);
        model.addAttribute("wordDTO", wordDTO);
        model.addAttribute("word", word);
        String newTranslation = "";
        model.addAttribute("newTranslation", newTranslation);
;       return "/words/editWord";
    }

    @PatchMapping("/{id}/edit")
    public String editWordPost(@ModelAttribute("wordDTO") @Valid WordDTO wordDTO, BindingResult bindingResult, @ModelAttribute("word") Word word2,
                               @PathVariable int id, Model model) {
        wordValidator.validate(wordDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("word", word2);
            return "/words/editWord";
        }
        System.out.println("pathch " + wordDTO.getName());
        Word word = wordService.getWordById(id).orElseThrow();
        word.setName(wordDTO.getName());
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
