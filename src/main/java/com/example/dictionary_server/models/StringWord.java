package com.example.dictionary_server.models;

import lombok.Data;

import java.util.List;
@Data
public class StringWord {
    private String name;
    private List<String> translations;
}
