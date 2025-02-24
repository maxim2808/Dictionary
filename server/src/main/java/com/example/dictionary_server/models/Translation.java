package com.example.dictionary_server.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "translation")
@Data
public class Translation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    @Column(name = "name")
    String name;
    @ManyToOne()
    @JoinColumn(name = "word_id", referencedColumnName = "id")
    Word word;
    @Temporal(TemporalType.TIMESTAMP)
    Date registrationDate;

    @Override
    public String toString() {
        return "Translation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                //   ", word=" + word +
                ", registrationDate=" + registrationDate +
                '}';
    }


}