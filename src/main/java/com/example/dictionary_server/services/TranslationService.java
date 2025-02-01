package com.example.dictionary_server.services;

import com.example.dictionary_server.models.Translation;
import com.example.dictionary_server.models.Word;
import com.example.dictionary_server.repositories.TranslationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TranslationService {
    private TranslationRepository translationRepository;

    public List<Translation> findAll() {
        return translationRepository.findAll();
    }

    public Optional<Translation> findTranslationById(int id) {
        return translationRepository.findById(id);
    }

    public Optional<Translation> findTranslationByName(List<Translation> translations, String name) {
        return translations.stream().filter(translation -> translation.getName().equals(name)).findFirst();
    }




    @Transactional
    public void save(Translation translation) {
        translationRepository.save(translation);
    }



    @Transactional
    public void addTranslation(Word word, String name) {
        Translation translation = new Translation();
        translation.setName(name);
        translation.setRegistrationDate(new Date());
        translation.setWord(word);
        translationRepository.save(translation);
    }


    @Transactional
    public void deleteTranslation(int id) {
        translationRepository.deleteById(id);
    }







}
