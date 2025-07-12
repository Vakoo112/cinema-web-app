package com.example.ticket_um;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.ticket_um.v1.domain.model")
@EnableJpaRepositories("com.example.ticket_um.v1.repository")
public class TicketUmApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketUmApplication.class, args);
	}

}
