package eu.lwstn.stappenstrijd.stappenservice;

import eu.lwstn.stappenstrijd.messages.BunchOfSteps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

import static eu.lwstn.stappenstrijd.stappenservice.KafkaTopicConfig.STEPS_TOPIC_NAME;

@RestController
public class StappenController {
	@Autowired
	private KafkaTemplate<String, BunchOfSteps> kafkaTemplate;

	private enum KafkaSynchronicity {
		ASYNCHRONOUS,
		SYNCHRONOUS
	}
	private static final KafkaSynchronicity KAFKA_SYNCHRONICITY = KafkaSynchronicity.ASYNCHRONOUS;

	@PostMapping(
			value="/{userId}/stappen",
			consumes = "application/json",
			produces = "application/json" // TODO does this set the Content header?
	)
	public ResponseEntity<?> createBunchOfSteps(@PathVariable String userId, @RequestBody BunchOfSteps bunchOfSteps) {
		// TODO some rudimental authentication (userid as a token?) and authorization (only allow adding steps to yourself)

		System.out.println(bunchOfSteps); // TODO replace with proper logging

		var sendFuture = kafkaTemplate.send(STEPS_TOPIC_NAME, bunchOfSteps);

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
		throw new ForgottenCaseError(KAFKA_SYNCHRONICITY);
	}

	public static final class KafkaError extends RuntimeException {
		KafkaError(Throwable cause) {
			super(cause);
		}
	}
}
