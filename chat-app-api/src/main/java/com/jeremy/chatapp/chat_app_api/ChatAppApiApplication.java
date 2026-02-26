package com.jeremy.chatapp.chat_app_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class ChatAppApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatAppApiApplication.class, args);
	}
}
