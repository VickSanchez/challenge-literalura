package com.vicksanchez.challenge_literalura.repository;

import com.vicksanchez.challenge_literalura.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);

    @Query("SELECT b FROM Book b WHERE b.language = :searchLanguage")
    List<Book> findBooksByLanguage(String searchLanguage);
}
