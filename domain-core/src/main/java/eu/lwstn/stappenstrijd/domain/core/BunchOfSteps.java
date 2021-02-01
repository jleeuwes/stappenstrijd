package eu.lwstn.stappenstrijd.domain.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import lombok.With;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.time.LocalDate;

/**
 * Represents a positive number of steps taken by a certain user on a certain day.
 * Is emitted as an event/message over Kafka using topic {@link #KAFKA_TOPIC}.
 */
@Data
@With
public class BunchOfSteps {
	/**
	 * The kafka topic that we use to communicate this type of message.
	 */
	public static final String KAFKA_TOPIC = "steps";

	@NonNull
	private final Username username;
	@NonNull
	private final LocalDate dateOfActivity;
	private final int numberOfSteps;

	/**
	 * Implementation note: {@code @Json*} annotations are necessary to let jackson deserialize using this constructor;
	 * {@code @NonNull} annotation are repeated here because we have a custom constructor (otherwise annotating the fields would be enough).
	 */
	@JsonCreator
	public BunchOfSteps(
			@NonNull @JsonProperty("username") Username username,
			@NonNull @JsonProperty("dateOfActivity") LocalDate dateOfActivity,
			@JsonProperty("numberOfSteps") int numberOfSteps
	) {
		if (numberOfSteps < 1) {
			throw new IllegalArgumentException("numberOfSteps must be a positive number");
		}

		this.username = username;
		this.dateOfActivity = dateOfActivity;
		this.numberOfSteps = numberOfSteps;
	}
}
