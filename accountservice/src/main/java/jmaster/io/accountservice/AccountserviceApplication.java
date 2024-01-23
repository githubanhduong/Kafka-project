package jmaster.io.accountservice;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AccountserviceApplication { 

	public static void main(String[] args) {
		SpringApplication.run(AccountserviceApplication.class, args);
	}

	@Bean
	NewTopic notification() {
		// name topic, partion number, replication number
		return new NewTopic("notification", 1, (short) 3);
	}
	
	@Bean
	NewTopic statistic() {
		
		return new NewTopic("statistics", 3, (short) 2);
	}
}
