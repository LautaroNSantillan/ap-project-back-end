package com.ap.portfolio;

import com.ap.portfolio.models.MockUser;
import com.ap.portfolio.repositories.MockUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
public class PortfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(MockUserRepository webUserRepository){
		return (args) -> {


		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1988);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date dateRepresentation = cal.getTime();

		MockUser juan = new MockUser("Juan", "Perez", "jperez@email.com");

		MockUser john = new MockUser("John", "Johnson", "jjohn@email.com");
		webUserRepository.save(john);
		webUserRepository.save(juan);
		};
	}

}
