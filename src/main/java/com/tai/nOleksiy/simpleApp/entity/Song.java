package com.tai.nOleksiy.simpleApp.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.tai.nOleksiy.simpleApp.songsValidator.SongsValidator.removeSpacesAndTrim;

@Getter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name="songs")
public class Song {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private long id;

    @Column(nullable=false, unique=true)
    @NotBlank(message="title is mandatory")
    @NonNull private String title;

    @Column(nullable = false)
    @NotBlank(message="author is mandatory")
    @NonNull private String author;

    @Column(nullable = false)
    @NonNull private LocalDate year_pub;

    @Column(nullable=false)
    @NonNull private String language;

    public Song(Long id, String title, String author, LocalDate year_pub, String language){
        this.id=id;
        this.title = removeSpacesAndTrim(title);
        this.author = removeSpacesAndTrim(author);
        this.year_pub = year_pub;
        this.language = language;
    }
    public Song() {

    }

    public void setId(Long id){
        this.id = id;
    }

    public void setTitle(String title){
        this.title = removeSpacesAndTrim(title);
    }

    public void setAuthor(String author){
        this.author = removeSpacesAndTrim(author);
    }

    public void setYear_pub(LocalDate year_pub){
        this.year_pub = year_pub;
    }

    public void setLanguage(String language){
        this.language = language.trim();
    }

    public static Set<String> getColumnNames(){
        Set<String> res = new HashSet<>();
        Field[] fields = Song.class.getDeclaredFields();
        for(var  field : fields) {
            field.setAccessible(true);
            res.add(field.getName());
        }
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Song song = (Song) o;
        return Objects.equals(id, song.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
