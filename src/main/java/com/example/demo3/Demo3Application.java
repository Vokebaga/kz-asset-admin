package com.example.demo3;

<<<<<<< HEAD
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
=======
import com.example.demo3.service.interfaces.StorageService;
import com.example.demo3.storage.StorageProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
>>>>>>> Admin/master
public class Demo3Application {

	public static void main(String[] args) {
		SpringApplication.run(Demo3Application.class, args);
	}
<<<<<<< HEAD
=======
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
>>>>>>> Admin/master

}
