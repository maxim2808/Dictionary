package com.example.dictionary_server.repositories;

import com.example.dictionary_server.models.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Integer> {
}
