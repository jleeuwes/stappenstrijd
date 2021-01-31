package eu.lwstn.stappenstrijd.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.With;
import org.springframework.lang.NonNull;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.time.LocalDate;

@Data
@With
public class BunchOfSteps {
	@NonNull
	private final LocalDate dateOfActivity;
	private final int numberOfSteps;

	@JsonCreator // annotations are necessary to let jackson deserialize using this constructor
	public BunchOfSteps(@JsonProperty("dateOfActivity") LocalDate dateOfActivity, @JsonProperty("numberOfSteps") int numberOfSteps) {
		// TODO null check or does lombok add this?

		if (numberOfSteps < 1) {
			throw new IllegalArgumentException("numberOfSteps must be a positive number");
		}

		this.dateOfActivity = dateOfActivity;
		this.numberOfSteps = numberOfSteps;
	}
}
