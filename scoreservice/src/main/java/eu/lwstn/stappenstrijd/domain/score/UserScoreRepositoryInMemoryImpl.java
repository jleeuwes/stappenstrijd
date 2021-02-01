package eu.lwstn.stappenstrijd.domain.score;

import eu.lwstn.stappenstrijd.domain.core.Username;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserScoreRepositoryInMemoryImpl implements UserScoreRepository {
	private final Map<Username,UserScore> scores;

	public UserScoreRepositoryInMemoryImpl() {
		this.scores = new HashMap<>();
	}

	@Override
	public Iterable<UserScore> findAll() {
		synchronized (scores) {
			// We make a copy to be able to handle concurrency
			return new ArrayList<>(scores.values());
		}
	}

	@Override
	public Optional<UserScore> findById(Username username) {
		synchronized (scores) {
			return Optional.ofNullable(scores.get(username));
		}
	}

	@Override
	public UserScore save(UserScore userScore) {
		synchronized (scores) {
			Username id = userScore.getUsername();
			UserScore old = this.scores.get(id);
			if (old != null) {
				if (old.getVersion() != userScore.getVersion()) {
					throw new OptimisticLockingFailureException("Tried to save version " + userScore.getVersion() + " but version in repository is " + old.getVersion());
				}
				userScore = userScore.withVersion(old.getVersion() + 1);
			}
			this.scores.put(id, userScore);
			return userScore;
		}
	}
}
