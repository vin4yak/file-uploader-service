package com.mercedes.fileuploader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FileUploaderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileUploaderServiceApplication.class, args);
	}
}
