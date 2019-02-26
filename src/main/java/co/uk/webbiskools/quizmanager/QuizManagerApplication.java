package co.uk.webbiskools.quizmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Connection;

@SpringBootApplication
public class QuizManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizManagerApplication.class, args);
	}

}
