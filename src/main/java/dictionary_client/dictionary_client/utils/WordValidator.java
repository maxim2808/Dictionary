package dictionary_client.dictionary_client.utils;

import dictionary_client.dictionary_client.dto.WordDTO;
import dictionary_client.dictionary_client.models.Word;
import dictionary_client.dictionary_client.services.WordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class WordValidator implements Validator {
    private final WordService wordService;
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(WordDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        WordDTO wordDTO = (WordDTO) target;
        if (wordService.getWordByName(wordDTO.getName()).isPresent()) {
          errors.rejectValue("name", "", "Это слово уже есть в словаре");
        }


    }
}
