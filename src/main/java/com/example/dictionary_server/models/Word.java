package com.example.dictionary_server.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "word")
@Data
public class Word {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "word"
            , fetch = FetchType.EAGER, cascade = CascadeType.REMOVE
    )
    List<Translation> translationList;

    @Column(name = "registration_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date registrationDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return Objects.equals(name, word.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Translation translation : translationList) {

            sb.append(translation.getName() + ", ");
        }

        return "Word{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", translation =" + sb +
                ", registrationDate=" + registrationDate +
                '}';
    }
}