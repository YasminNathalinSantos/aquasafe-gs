package br.com.fiap.aquasafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AquaSafeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AquaSafeApplication.class, args);
	}

}
