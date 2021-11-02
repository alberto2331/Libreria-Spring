package com.semillero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
//(exclude = SecurityAutoConfiguration.class)
//la siguiente linea de codigo sirve para deshabilitar el springSecurity :(exclude = SecurityAutoConfiguration.class)
//y: import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration; 
public class LibreriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibreriaApplication.class, args);
	}

}
