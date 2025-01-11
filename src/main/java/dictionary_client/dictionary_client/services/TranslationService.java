package dictionary_client.dictionary_client.services;

import dictionary_client.dictionary_client.models.Translation;
import dictionary_client.dictionary_client.repositories.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(readOnly = true)
public class TranslationService {
    @Autowired
    private final TranslationRepository translationRepository;

    public TranslationService(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    @Transactional
    public void save(Translation translation) {
translationRepository.save(translation);
    }





//    @Transactional
//    public void editTranslation(Translation translation) {
//     translationRepository.save(translation);
//    }

    @Transactional
    public void addTranslation(String name) {
    Translation translation = new Translation();
    translation.setName(name);
    translation.setRegistrationDate(new Date());
    translationRepository.save(translation);
    }


    @Transactional
    public void deleteTranslation(int id) {
        translationRepository.deleteById(id);
    }











}
