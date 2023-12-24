package jmaster.io.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Notificationservice1Application {

	public static void main(String[] args) {
		SpringApplication.run(Notificationservice1Application.class, args);
	}
	
	@Bean
	JsonMessageConverter converter() {
		return new JsonMessageConverter();
	}

}
