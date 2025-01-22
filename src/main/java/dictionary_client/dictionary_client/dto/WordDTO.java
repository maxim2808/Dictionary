package dictionary_client.dictionary_client.dto;

import lombok.Data;

import java.util.List;
@Data
public class WordDTO {
    private String name;
    private List<String> translations;
}
