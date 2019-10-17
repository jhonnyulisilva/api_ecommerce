package com.udemy.cursomc;

import com.udemy.cursomc.domain.*;
import com.udemy.cursomc.domain.Enums.EstadoPagamento;
import com.udemy.cursomc.domain.Enums.TipoCliente;
import com.udemy.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	public void run(String... args) throws Exception {
	}
}
