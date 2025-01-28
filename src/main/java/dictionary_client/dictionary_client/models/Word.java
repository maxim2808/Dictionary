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
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "word"
 , fetch = FetchType.EAGER, cascade = CascadeType.REMOVE
    )
    List<Translation> translationList;

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

    public String getName() {
        return name;
    }

    public void setName(String original) {
        this.name = original;
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
                "progress=" + progress +
                ", registrationDate=" + registrationDate +
                '}';
    }

    public String getStringTranslation() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < translationList.size()-1; i++) {
            sb.append(translationList.get(i).getName()).append(", ");
        }
        sb.append(translationList.get(translationList.size()-1).getName());
        return sb.toString();
    }

}
