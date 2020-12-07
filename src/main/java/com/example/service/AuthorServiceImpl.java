package com.example.service;

import com.example.exception.AuthorNotFoundException;
import com.example.model.Author;
import com.example.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService{
    private static final String EXCEPTION_MESSAGE = "Author with id %d not found";
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Author> findById(Long id) {
         Optional<Author> author = authorRepository.findById(id);
         return author;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Author> findByFirstNameAndLastName(String firstName, String lastName) {
        return authorRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Transactional
    @Override
    public void updateById(Author author, Long id) {
        authorRepository.deleteById(id);
        author.setId(id);
        authorRepository.save(author);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }

}
