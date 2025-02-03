package dictionary_client.dictionary_client.controllers;

import dictionary_client.dictionary_client.models.Word;
import dictionary_client.dictionary_client.services.KnowledgeTestService;
import dictionary_client.dictionary_client.services.WordService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
@RequestMapping("/test")
@Controller
public class TestKnowledgeController {

    private final DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration;
    private KnowledgeTestService knowledgeTestService;
    private WordService wordService;

    @GetMapping("")
    public String testGet(Model model) {


        if (wordService.findAllWords().size()<knowledgeTestService.countWordInTest){
            model.addAttribute("sizeList", wordService.findAllWords().size());
            model.addAttribute("countInTest", knowledgeTestService.countWordInTest);
            return "/tooFewWordsErrorPage";
        }
        List<Word> list=  knowledgeTestService.getFixWordListForTest();
        model.addAttribute("list", list);
        model.addAttribute( "correctWord", knowledgeTestService.getWord());
        model.addAttribute( "wordNumber", knowledgeTestService.getCurrentNumberInTest());

        return "/testKnowledge";
    }

    @GetMapping("/{id}")
    public String testPost(Model model, @PathVariable int id, @ModelAttribute("correctedWord") Word correctWord)
    {
        knowledgeTestService.checkAnswer(id);
        if (knowledgeTestService.testShouldBeFinished()){
            return "redirect:/test/finish";
        };
        return "redirect:/test";
    }


    @GetMapping("/finish")
    public String finish() {
        return "finishTest";
    }

    @GetMapping("/interrupt")
    public String interrupt(){
        knowledgeTestService.finishTest();
        return "redirect:/words";
    }
}
