package com.fiap.dow.bitcoin.BITCOIN;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BitcoinApplication {

	public static void main(String[] args) {
		SpringApplication.run(BitcoinApplication.class, args);
	}
}
