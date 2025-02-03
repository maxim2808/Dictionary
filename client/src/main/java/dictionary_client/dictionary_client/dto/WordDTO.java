package dictionary_client.dictionary_client.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Collections;
import java.util.List;
@Data
public class WordDTO {
    private int id;
    @NotEmpty(message = "Поле не должно быть пустым")
    private String name;
    private List<String> translations;
}
