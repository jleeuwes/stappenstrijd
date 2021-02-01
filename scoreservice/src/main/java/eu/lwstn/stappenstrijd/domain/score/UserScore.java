package eu.lwstn.stappenstrijd.domain.score;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.lwstn.stappenstrijd.domain.core.Username;
import lombok.Data;
import lombok.NonNull;
import lombok.With;

/**
 * Represents the score (i.e. total number of steps since the start of the <i>strijd</i>) of a particular user.
 */
@Data
public class UserScore {
	@NonNull
	private final Username username;
	@With
	@JsonIgnore // we don't want it in the JSON serialization (we don't use deserialization, so ignoring it is okay)
	private final long version;
	@NonNull
	@With
	private final int totalNumberOfSteps;

	public UserScore(@NonNull Username username, long version, int totalNumberOfSteps) {
		if (version < 0) {
			throw new IllegalArgumentException("version must not be negative");
		}

		if (totalNumberOfSteps < 0) {
			throw new IllegalArgumentException("totalNumberOfSteps must not be negative");
		}

		this.username = username;
		this.version = version;
		this.totalNumberOfSteps = totalNumberOfSteps;
	}
}
