package dictionary_client.dictionary_client.services;

import dictionary_client.dictionary_client.models.Translation;
import dictionary_client.dictionary_client.models.Word;
import dictionary_client.dictionary_client.repositories.TranslationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TranslationServiceTest {
    @InjectMocks
    private TranslationService translationService;
    @Mock
    private TranslationRepository translationRepository;


    @Test
    public void teslAllFindTranslations() {
        List<Translation> translations = getTranslations();
        Mockito.when(translationRepository.findAll()).thenReturn(translations);
        Assertions.assertEquals(6, translationService.findAll().size());
    }



    @Test
    public void testAddedTranslation() {
        Word word = new Word();
        word.setOriginal("sky");
        word.setId(1);

        List<Translation> list = new ArrayList<>();
        Mockito.doAnswer(invocation -> {
            Translation translation = invocation.getArgument(0);
            list.add(translation);
            return null;

    }).when(translationRepository).save(Mockito.any(Translation.class));
        translationService.addTranslation(word,"небо");
        Assertions.assertEquals("небо", list.get(0).getName());
    }



        private List<Translation> getTranslations(){

            List<Translation> list = new ArrayList<>();
            Translation translation1 = new Translation();
            translation1.setName("Книга");
            translation1.setId(1);
            list.add(translation1);


            Translation translation2 = new Translation();
            translation2.setName("машина");
            translation2.setId(2);
            list.add(translation2);
            Translation translation3= new Translation();
            translation3.setId(3);
            translation3.setName("автомобиль");
            list.add(translation3);
            Translation translation4= new Translation();
            translation4.setId(4);
            translation4.setName("легковой автомобиль");
            list.add(translation4);

            Translation translation5= new Translation();
            translation5.setId(5);
            translation5.setName("трава");
            list.add(translation5);

            Translation translation6 = new Translation();
            translation6.setId(6);
            translation6.setName("газон");
            list.add(translation6);

            return list;



    }
}
