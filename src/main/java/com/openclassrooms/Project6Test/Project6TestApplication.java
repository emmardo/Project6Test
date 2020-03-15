package com.openclassrooms.Project6Test;

import com.openclassrooms.Project6Test.Repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//Added but not sure if it works...
@EnableJpaRepositories( basePackageClasses = UserRepository.class)
public class Project6TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(Project6TestApplication.class, args);
	}

}
