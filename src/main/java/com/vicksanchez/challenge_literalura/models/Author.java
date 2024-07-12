package com.vicksanchez.challenge_literalura.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int birthDate;

    private int deathDate;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books;

    public Author() {}

    public Author(AuthorData authorData){
        this.name = authorData.name();
        this.birthDate = authorData.birthDate();
        this.deathDate = authorData.deathDate();
    }

    public StringBuilder getBooks() {
        StringBuilder booksStr = new StringBuilder();
        booksStr.append("Libros: ");
        books.forEach(book -> booksStr.append(book.getTitle()));
        return booksStr;
    }

    public int getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(int deathDate) {
        this.deathDate = deathDate;
    }

    public int getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(int birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return  "****************** AUTOR *********************"+"\n" +
                "Nombre: '" + this.getName() + '\'' + "\n" +
                "Año de Nacimiento: " + this.getBirthDate() + "\n" +
                "Año de Muerte: " + this.getDeathDate() + "\n" +
                this.getBooks() + "\n" +
                "*********************************************"+"\n" ;
    }
}
