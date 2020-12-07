package com.example.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "genre")
@NamedEntityGraph(name = "genre_all_data", attributeNodes = {@NamedAttributeNode("books")})
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "genre")
    private String genreName;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Book> books;

    public Genre(String genre) {
        this.genreName = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre1 = (Genre) o;
        return Objects.equals(genreName, genre1.genreName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, genreName);
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", genre='" + genreName + '\'' +
                '}';
    }
}
