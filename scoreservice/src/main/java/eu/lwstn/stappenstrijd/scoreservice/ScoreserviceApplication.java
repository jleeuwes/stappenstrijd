package eu.lwstn.stappenstrijd.scoreservice;

import eu.lwstn.stappenstrijd.domain.core.BunchOfSteps;
import eu.lwstn.stappenstrijd.domain.core.Username;
import eu.lwstn.stappenstrijd.domain.score.UserScore;
import eu.lwstn.stappenstrijd.domain.score.UserScoreRepository;
import eu.lwstn.stappenstrijd.domain.score.UserScoreRepositoryInMemoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;

@SpringBootApplication
public class ScoreserviceApplication {
	@Autowired
	private UserScoreRepository userScoreRepository;

	public static void main(String[] args) {
		SpringApplication.run(ScoreserviceApplication.class, args);
	}

	@Bean
	public UserScoreRepository userScoreRepository() {
		return new UserScoreRepositoryInMemoryImpl();
	}

	/**
	 * Receive {@link BunchOfSteps} through Kafka and
	 * update the corresponding {@link UserScore} in the repository.
	 */
	@KafkaListener(topics = BunchOfSteps.KAFKA_TOPIC)
	public void receiveSteps(@Payload BunchOfSteps bunchOfSteps) {
		System.out.println("Received " + bunchOfSteps);

		Username username = bunchOfSteps.getUsername();
		UserScore userScore = userScoreRepository.findById(username).orElseGet(() -> new UserScore(username, 0, 0));
		userScore = userScore.withTotalNumberOfSteps(userScore.getTotalNumberOfSteps() + bunchOfSteps.getNumberOfSteps());
		userScore = userScoreRepository.save(userScore);

		System.out.println("Updated " + userScore);
	}
}
