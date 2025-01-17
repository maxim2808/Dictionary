package com.example.dictionary_server.controllers;

import com.example.dictionary_server.models.StringWord;
import com.example.dictionary_server.models.Word;
import com.example.dictionary_server.services.WordService;
import com.example.dictionary_server.util.WordNotFoundException;
import com.example.dictionary_server.util.WordNotFoundResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class WordController {

    private final WordService wordService;


    @PostMapping("/giveWord")
    public ResponseEntity<StringWord> giveWord(@RequestBody String wordName) {
        StringWord stringWord = wordService.giveStringWordByName(wordName);
        return new ResponseEntity<>(stringWord, HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<WordNotFoundResponse> notFoundExceptionHandler(WordNotFoundException ex){
        WordNotFoundResponse response = new WordNotFoundResponse();
        response.setMessage("Слово не было найденано");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
