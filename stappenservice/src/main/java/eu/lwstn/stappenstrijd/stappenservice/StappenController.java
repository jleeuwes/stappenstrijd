package eu.lwstn.stappenstrijd.stappenservice;

import eu.lwstn.stappenstrijd.domain.core.BunchOfSteps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
public class StappenController {
	@Autowired
	private KafkaTemplate<String, BunchOfSteps> kafkaTemplate;

	private enum KafkaSynchronicity {
		ASYNCHRONOUS,
		SYNCHRONOUS
	}
	private static final KafkaSynchronicity KAFKA_SYNCHRONICITY = KafkaSynchronicity.SYNCHRONOUS;

	@PostMapping(
			value="/stappen",
			consumes = "application/json",
			produces = "application/json"
	)
	public ResponseEntity<?> createBunchOfSteps(@RequestBody BunchOfSteps bunchOfSteps) {
		// TODO some rudimental dummy authentication (userid as a token?) and authorization (only allow adding steps to yourself)

		System.out.println("Creating " + bunchOfSteps); // TODO replace with proper logging

		var sendFuture = kafkaTemplate.send(BunchOfSteps.KAFKA_TOPIC, bunchOfSteps);

		// TODO pick between async and sync; this switch is just to play around
		switch (KAFKA_SYNCHRONICITY) {
			case SYNCHRONOUS:
				try {
					sendFuture.get();
					return ResponseEntity.noContent().build();
				} catch (InterruptedException | ExecutionException e) {
					throw new KafkaError(e); // hoping this is turned into a proper JSON 500
				}
			case ASYNCHRONOUS:
				// Just fire and forget. TODO probably log errors instead of ignoring them :P
				return ResponseEntity.accepted().build();
		}
		throw new ForgottenCaseError(KAFKA_SYNCHRONICITY); // would not be necessary with https://openjdk.java.net/jeps/361#Exhaustiveness from Java 14
	}

	public static final class KafkaError extends RuntimeException {
		KafkaError(Throwable cause) {
			super(cause);
		}
	}
}
