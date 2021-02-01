package eu.lwstn.stappenstrijd.scoreservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import eu.lwstn.stappenstrijd.domain.core.BunchOfSteps;
import eu.lwstn.stappenstrijd.domain.core.Username;
import eu.lwstn.stappenstrijd.domain.score.UserScore;
import eu.lwstn.stappenstrijd.domain.score.UserScoreRepository;
import eu.lwstn.stappenstrijd.domain.score.UserScoreRepositoryInMemoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
public class ScorebordController {
	@Autowired
	private UserScoreRepository userScoreRepository;

	@GetMapping(
			value="/scorebord",
			produces = "application/json"
	)
	public ResponseEntity<?> viewScorebord() {
		ArrayNode results = new ObjectMapper().createArrayNode();

		List<UserScore> scores = new ArrayList<>();
		userScoreRepository.findAll().forEach(scores::add);
		scores.sort(Comparator.comparing(UserScore::getTotalNumberOfSteps).reversed());

		for (UserScore userScore : scores) {
			results.addPOJO(userScore);
		}

		return ResponseEntity.ok(results);
	}
}
