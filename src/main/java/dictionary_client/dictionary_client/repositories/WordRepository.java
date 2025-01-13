package dictionary_client.dictionary_client.repositories;

import dictionary_client.dictionary_client.models.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository<Word, Integer> {
    public Optional<Word> findWordsByName(String original);
}
