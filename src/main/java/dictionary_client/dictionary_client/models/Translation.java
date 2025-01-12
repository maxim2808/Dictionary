package dictionary_client.dictionary_client.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Entity
@Table(name = "translation")
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

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
