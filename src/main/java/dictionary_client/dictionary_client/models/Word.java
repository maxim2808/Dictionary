package dictionary_client.dictionary_client.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
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
    @OneToMany(mappedBy = "word", fetch = FetchType.EAGER)
    List<Translation> translationList;
//    @Column(name = "translation")
//    private  String translation;
    @Column(name = "progress")
    private  int progress;
    @Column(name = "registration_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date registrationDate;


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

    public List<Translation> getTranslationList() {
        return translationList;
    }

    public void setTranslationList(List<Translation> translationList) {
        this.translationList = translationList;
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


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Translation translation : translationList) {

            sb.append(translation.getName() + ", ");
        }

        return "Word{" +
                "id=" + id +
                ", original='" + original + '\'' +
                ", translation =" + sb +
                "progress=" + progress +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
