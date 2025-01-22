package com.example.dictionary_server.controllers;

import com.example.dictionary_server.models.StringWord;
import com.example.dictionary_server.models.Translation;
import com.example.dictionary_server.models.Word;
import com.example.dictionary_server.repositories.WordRepository;
import com.example.dictionary_server.services.TranslationService;
import com.example.dictionary_server.services.WordService;
import com.example.dictionary_server.util.WordNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WordController.class)
public class WordControllerTest {

    @MockitoBean
    private WordService wordService;

    @MockitoBean
    private TranslationService translationService;

    @Autowired
    private MockMvc mockMvc;




    @Test
    public void giveWordTest() throws Exception {
        StringWord stringWord = new StringWord();
        stringWord.setName("car");
        List<String> listString = new ArrayList<>();
        listString.add("машина");
        listString.add("автомобиль");
        listString.add("легковой автомобиль");
        stringWord.setTranslations(listString);
        Mockito.when(wordService.giveStringWordByName("car")).thenReturn(stringWord);
        String content = "car";
        mockMvc.perform(post("/api/giveWord").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());
        verify(wordService, times(1)).giveStringWordByName("car");
    }
}
