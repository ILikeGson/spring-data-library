package com.example.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "book")
@NamedEntityGraph(name = "all-data-entity-graph", attributeNodes = {
        @NamedAttributeNode("title"), @NamedAttributeNode("genre"), @NamedAttributeNode("author"), @NamedAttributeNode("comments")})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    @ManyToOne(targetEntity = Genre.class, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<BookComment> comments;

    public Book(String title, Author author, Genre genre, List<BookComment> comments) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.comments = comments;
    }

    public Book(long id, String title, Author author, Genre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public void add(BookComment comment) {
        if (Objects.isNull(comments)) {
            comments = new ArrayList<>();
        }
        comments.add(comment);
        comment.setBook(this);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                '}';
    }
}
