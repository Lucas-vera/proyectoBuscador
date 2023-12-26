package com.proyecto.proyectoBuscador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ProyectoBuscadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoBuscadorApplication.class, args);
	}

}
