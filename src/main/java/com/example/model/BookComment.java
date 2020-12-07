package com.example.model;


import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "book_comment")
public class BookComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "comment", nullable = false)
    private String comment;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="book_id")
    private Book book;

    public BookComment(String comment) {
        this.comment = comment;
    }

    public BookComment(String comment, Book book) {
        this.comment = comment;
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookComment that = (BookComment) o;
        return id == that.id &&
                Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, comment);
    }

    @Override
    public String toString() {
        return "BookComment{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                '}';
    }
}
