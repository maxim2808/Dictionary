package com.example.dictionary_server;

import com.example.dictionary_server.models.Translation;
import com.example.dictionary_server.models.Word;
import com.example.dictionary_server.services.TranslationService;
import com.example.dictionary_server.services.WordService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@AllArgsConstructor
public class DictionaryServerApplication
		implements CommandLineRunner
{

	private final TranslationService translationService;
	private final WordService wordService;

	public static void main(String[] args) {
		SpringApplication.run(DictionaryServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		List<String> list = new ArrayList<>();
		list.add("держать");
		list.add("сохранять");
		list.add("хранить");
		wordService.addWord("keep", list);


	}
}
