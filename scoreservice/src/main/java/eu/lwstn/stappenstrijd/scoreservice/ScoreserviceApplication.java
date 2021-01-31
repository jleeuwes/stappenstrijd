package eu.lwstn.stappenstrijd.scoreservice;

import eu.lwstn.stappenstrijd.messages.BunchOfSteps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;

@SpringBootApplication
public class ScoreserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScoreserviceApplication.class, args);
	}

}
