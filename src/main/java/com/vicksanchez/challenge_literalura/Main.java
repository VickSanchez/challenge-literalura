package com.vicksanchez.challenge_literalura;

import com.vicksanchez.challenge_literalura.models.Author;
import com.vicksanchez.challenge_literalura.models.Book;
import com.vicksanchez.challenge_literalura.models.BookData;
import com.vicksanchez.challenge_literalura.models.Data;
import com.vicksanchez.challenge_literalura.repository.AuthorRepository;
import com.vicksanchez.challenge_literalura.repository.BooksRepository;
import com.vicksanchez.challenge_literalura.services.API;
import com.vicksanchez.challenge_literalura.services.ConvertData;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private BooksRepository booksRepository;
    private AuthorRepository authorRepository;

    private final String URL_BASE = "https://gutendex.com";

    private API api =  new API();

    private ConvertData converter = new ConvertData();

    private Scanner keyboard  = new Scanner(System.in);

    private String languages = """
                    \n
                    1 - Español
                    2 - Inglés
                    3 - Portugues
                    4 - Frances
                    """;
    private String menu = """
                    \n
                    ------- BIENVENIDO A LITERALURA --------\n
                    Ingresa la Opción deseada \n
                    1 - Buscar Libro Por Título
                    2 - Listar Libros Registrados
                    3 - Listar Autores Registrados
                    4 - Listar Autores Vivos En Un Determinado Año
                    5 - Listar Libros por Idioma
                                         
                    0 - Salir
                    ----------------------------------------
                    """;
    private int option = -1;

    public Main(BooksRepository booksRepository, AuthorRepository authorRepository) {
        this.booksRepository = booksRepository;
        this.authorRepository = authorRepository;
    }


    public void showMenu() {

        while (option != 0) {

            System.out.println(menu);
            option = keyboard.nextInt();
            keyboard.nextLine();

            switch (option) {
                case 1:
                    findBookByTitle();
                    break;
                case 2:
                    findBooksInDB();
                    break;
                case 3:
                    findAuthorsInDB();
                    break;
                case 4:
                    findAuthorsInDBByYear();
                    break;
                case 5:
                    findBooksByLanguage();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...\n");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }


    private void showBookInfo(Book book){

        var data =  "****************** LIBRO *********************"+"\n" +
                "Titulo: '" + book.getTitle() + '\'' + "\n" +
                "Autor: " + book.getAuthor().getName() + "\n" +
                "Idioma: " + book.getLanguage() + "\n" +
                "Numero de Descargas: " + book.getDownloads() +"\n"+
                "*********************************************"+"\n" ;
        System.out.println(data);
    }

    private void showAuthorInfo(Author author) {
        var data =  "****************** AUTOR *********************"+"\n" +
                "Nombre: '" + author.getName() + '\'' + "\n" +
                "Año de Nacimiento: " + author.getBirthDate() + "\n" +
                "Año de Muerte: " + author.getDeathDate() + "\n" +
                "*********************************************"+"\n" ;
        System.out.println(data);
    }

    private Boolean verifyExistingAuthor(Author author) {
        Optional<Author> existingAuthor = authorRepository.findByName(author.getName());
        return existingAuthor.isPresent();
    }

    private Boolean verifyExistingBook(Book book) {
        Optional<Book> existingBook = booksRepository.findByTitle(book.getTitle());
        return existingBook.isPresent();
    }


    // Search a book by the given title
    private void findBookByTitle() {
        System.out.println("Escribe el Titulo que deseas buscar");
        var searchTitle = keyboard.nextLine();
        var json = api.getData(URL_BASE + "/books/" + "?search=" + searchTitle.replace(" ", "%20"));
        var dataConverted = converter.getData(json, Data.class);
//        System.out.println(dataConverted);
        Optional<BookData> findedBook = dataConverted.results().stream().findFirst();
        if (findedBook.isPresent()) {
            System.out.println("Libro encontrado");
            var book = findedBook.get();
            var bookSave = new Book(book);
            Boolean existingBook = verifyExistingBook(bookSave);
            Boolean existingAuthor = verifyExistingAuthor(bookSave.getAuthor());
            if (!existingAuthor){
                authorRepository.save(bookSave.getAuthor());
            }
            if (!existingBook){
                booksRepository.save(bookSave);
            }
            showBookInfo(bookSave);
        }else {
            System.out.println("Libro No Encontrado");
        }
    }

    private void findBooksInDB() {
        List<Book> books = booksRepository.findAll();
        if (!books.isEmpty()){
            books.forEach(book -> showBookInfo(book));
        } else {
            System.out.println("No Hay Libros Registrados");
        }
    }

    private void findAuthorsInDB() {
        List<Author> authors = authorRepository.findAll();
        if (!authors.isEmpty()){
            authors.forEach(author -> showAuthorInfo(author));
        }else {
            System.out.println("No Hay Autores Registrados");
        }
    }

    private void findAuthorsInDBByYear() {
        try {
            System.out.println("Ingresa el Año de Busqueda");
            var searchDate = keyboard.nextInt();
            List<Author> aliveAuthors = authorRepository.findAuthorsInDBByYear(searchDate);
            if (!aliveAuthors.isEmpty()){
                aliveAuthors.forEach(author -> showAuthorInfo(author));
            }else {
                System.out.println("Autores No Encontrados ");
            }
        } catch (InputMismatchException e){
            System.out.println("Valor Inválido");
        }
    }

    private void findBooksByLanguage() {
        System.out.println("Ingresa Opcion Del Idioma Que Deseas Buscar");
        System.out.println(languages);
        var optionLangague = keyboard.nextInt();
        String searchLanguage = "";
        switch (optionLangague) {
            case 1:
                searchLanguage = "es";
                break;
            case 2:
                searchLanguage = "en";
                break;
            case 3:
                searchLanguage = "pt";
                break;
            case 4:
                searchLanguage = "fr";
                break;
            default:
                System.out.println("Opción inválida");

        }

        List<Book> books = booksRepository.findBooksByLanguage(searchLanguage);

        if (!books.isEmpty()){
            books.forEach(book -> showBookInfo(book));
        }else {
            System.out.println("No Hay Libros Registrados En Este Idioma");
        }

    }





}
