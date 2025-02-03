package com.example.dictionary_server.dto;

import lombok.Data;

import java.util.List;
@Data
public class WordDTO {
    private String name;
    private List<String> translations;
}
