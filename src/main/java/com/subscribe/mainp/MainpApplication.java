package com.subscribe.mainp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.subscribe.mainp.entity.User;
import com.subscribe.mainp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@EnableScheduling

public class MainpApplication {
	//Parv

	@Autowired
	UserRepo repo;
//	@PostConstruct
//	public void initUsers() {
//		List<User> users = Stream.of(
//				new User(101, 31, "parv", "javatechie", "javatechie@gmail.com", "password"),
//				new User(102, 32, "saket", "user21", "user1@gmail.com", "pwd1"),
//				new User(103, 33, "palak", "user22", "user2@gmail.com", "pwd2"),
//				new User(104, 34, "rohan", "user23", "user3@gmail.com", "pwd3")
//		).collect(Collectors.toList());
//		repo.saveAll(users);
//	}
	public static void main(String[] args) {
		SpringApplication.run(MainpApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean public ObjectMapper objectMapper() {
		return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);}
}
