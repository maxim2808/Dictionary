package dictionary_client.dictionary_client.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "word")
public class Word {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "original")
    private String original;
    @Column(name = "translation")
    private  String translation;
    @Column(name = "progress")
    private  int progress;
    @Column(name = "registration_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date registrationDate;

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", original='" + original + '\'' +
                ", translation='" + translation + '\'' +
                ", progress=" + progress +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return Objects.equals(original, word.original);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(original);
    }
}
