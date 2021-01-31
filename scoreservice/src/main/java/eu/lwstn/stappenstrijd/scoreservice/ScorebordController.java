package eu.lwstn.stappenstrijd.scoreservice;

import eu.lwstn.stappenstrijd.messages.BunchOfSteps;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

@RestController
public class ScorebordController {

	/**
	 * I feel like this listener doesn't belong in the controller,
	 * but I couldn't yet figure out how to make it work otherwise.
	 * TODO try in application again now Kafka seems to work
	 */
	@KafkaListener(topics = "steps")
	public void listenToKafka(@Payload BunchOfSteps bunchOfSteps) {
		System.out.println("Received " + bunchOfSteps + "!");
	}

}
