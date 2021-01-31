package eu.lwstn.stappenstrijd.stappenservice;

import lombok.Data;
import lombok.With;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Data
@With
public class BunchOfSteps {
	@NonNull
	private final LocalDate dateOfActivity;
	private final int numberOfSteps;

	public BunchOfSteps(LocalDate dateOfActivity, int numberOfSteps) {
		// TODO null check or does lombok add this?

		if (numberOfSteps < 1) {
			throw new IllegalArgumentException("numberOfSteps must be a positive number");
		}

		this.dateOfActivity = dateOfActivity;
		this.numberOfSteps = numberOfSteps;
	}
}
