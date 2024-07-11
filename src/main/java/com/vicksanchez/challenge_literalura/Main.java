package com.vicksanchez.challenge_literalura;

import com.vicksanchez.challenge_literalura.models.BookData;
import com.vicksanchez.challenge_literalura.models.Data;
import com.vicksanchez.challenge_literalura.repository.BooksRepository;
import com.vicksanchez.challenge_literalura.services.API;
import com.vicksanchez.challenge_literalura.services.ConvertData;

import java.util.Optional;
import java.util.Scanner;

public class Main {

    private BooksRepository repository;

    private final String URL_BASE = "https://gutendex.com";

    private API api =  new API();

    private ConvertData converter = new ConvertData();

    private Scanner keyboard  = new Scanner(System.in);

    public Main(BooksRepository repository) {
        this.repository = repository;
    }


    public void showMenu() {
        var option = -1;
        while (option != 0) {
            var menu = """
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
            System.out.println(menu);
            option = keyboard.nextInt();
            keyboard.nextLine();

            switch (option) {
                case 1:
                    findBookByTitle();
                    break;
                case 2:
//                    buscarEpisodioPorSerie();
                    break;
                case 3:
//                    mostrarSeriesBuscadas();
                    break;
                case 4:
//                    buscarSeriePorTitulo();
                    break;
                case 5:
//                    buscarTop5Series();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...\n");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }
    private void showBookInfo(BookData bookData){
        var data =  "Titulo: " + bookData.titulo() + '\'' +
                    "Autor: " + bookData.autor() +
                    "Idioma: " + bookData.idiomas() +
                    "Numero de Descargas: " + bookData.descargas() + '\'';
        System.out.println(data);
    }

    // Search a book by the given title
    private void findBookByTitle() {
        System.out.println("Escribe el titulo que deseas buscar");
        var searchTitle = keyboard.nextLine();
        var json = api.getData(URL_BASE + "/books/");
        var dataConverted = converter.getData(json, Data.class);
        Optional<BookData> findedBook = dataConverted.resultados().stream()
                .filter( libro -> libro.titulo().toUpperCase().contains(searchTitle.toUpperCase()))
                .findFirst();
        if (findedBook.isPresent()) {
            System.out.println("Libro encontrado");

            var book = findedBook.get();
            showBookInfo(book);
        }else {
            System.out.println("Libro No Encontrado");
        }
    }


}
