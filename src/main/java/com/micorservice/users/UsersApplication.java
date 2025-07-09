package com.micorservice.users;

import com.micorservice.users.infrastructure.out.jpa.entity.RoleEntity;
import com.micorservice.users.infrastructure.out.jpa.repository.IRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class UsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}


	}

