package com.vicksanchez.challenge_literalura;

import com.vicksanchez.challenge_literalura.repository.AuthorRepository;
import com.vicksanchez.challenge_literalura.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeLiteraluraApplication implements CommandLineRunner {

		@Autowired
		private BooksRepository booksRepository;
		@Autowired
		private AuthorRepository authorRepository;

	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(booksRepository, authorRepository);
		main.showMenu();
	}
}
