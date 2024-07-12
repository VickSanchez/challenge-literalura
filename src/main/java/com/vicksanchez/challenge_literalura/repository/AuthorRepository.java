package com.vicksanchez.challenge_literalura.repository;

import com.vicksanchez.challenge_literalura.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);

    @Query("SELECT a FROM Author a WHERE a.birthDate <= :searchDate AND a.deathDate >= :searchDate")
    List<Author> findAuthorsInDBByYear(int searchDate);
}
