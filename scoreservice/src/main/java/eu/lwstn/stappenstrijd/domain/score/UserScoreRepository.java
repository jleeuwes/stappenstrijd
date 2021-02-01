package eu.lwstn.stappenstrijd.domain.score;

import eu.lwstn.stappenstrijd.domain.core.Username;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Repository for retrieving and storing the {@link UserScore} entity.
 * Similar to {@code CrudRepository<UserScore,Username>} but in memory only.
 */
public interface UserScoreRepository {
	Iterable<UserScore> findAll();
	Optional<UserScore> findById(Username username);
	UserScore save(UserScore userScore);
}