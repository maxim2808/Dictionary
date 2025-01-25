package dictionary_client.dictionary_client.controllers;

import dictionary_client.dictionary_client.services.KnowledgeTestService;
import dictionary_client.dictionary_client.services.WordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Scanner;

@AllArgsConstructor
@RequestMapping("/test")
@Controller
public class TestKnowledgeController {

    private KnowledgeTestService knowledgeTestService;

    @GetMapping("")
    public String testGet() {
        knowledgeTestService.simpleTranslationTest(new Scanner(System.in));
        return "/testKnowledge";
    }

}
