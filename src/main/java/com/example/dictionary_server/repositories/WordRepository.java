package com.example.dictionary_server.repositories;

import com.example.dictionary_server.models.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WordRepository  extends JpaRepository<Word, Integer> {
    public Optional<Word> findWordByName(String original);
}
