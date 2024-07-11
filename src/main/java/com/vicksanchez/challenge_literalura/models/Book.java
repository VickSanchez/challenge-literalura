package com.vicksanchez.challenge_literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String titulo;

//    private List<AuthorData> autor;

//    private List<String> idiomas;

    private Double descargas;
}
