package com.ap.portfolio;

import com.ap.portfolio.models.MockUser;
import com.ap.portfolio.repositories.MockUserRepository;
import com.ap.portfolio.security.enums.RoleName;
import com.ap.portfolio.security.repositories.IRoleRepository;
import com.ap.portfolio.security.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;


import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
public class PortfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioApplication.class, args);
	}
	@Bean
	public RedirectStrategy redirectStrategy() {
		return new DefaultRedirectStrategy();
	}
	@Autowired
	private IRoleRepository iRoleRepository;

	@Bean
	public CommandLineRunner initData(MockUserRepository webUserRepository){
		return (args) -> {


			Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1988);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date dateRepresentation = cal.getTime();

		MockUser juan = new MockUser("Juan", "Perez",  LocalDate.now());

		MockUser john = new MockUser("John", "Johnson", LocalDate.now());

			Role admin = new Role(RoleName.ROLE_ADMIN);
			Role user = new Role(RoleName.ROLE_USER);

		//	this.iRoleRepository.save(admin);
		//	this.iRoleRepository.save(user);

//		webUserRepository.save(john);
//		webUserRepository.save(juan);
		};
	}

}
