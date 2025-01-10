package dictionary_client.dictionary_client.repositories;

import dictionary_client.dictionary_client.models.Translation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationRepository extends CrudRepository<Translation, Integer> {
}
