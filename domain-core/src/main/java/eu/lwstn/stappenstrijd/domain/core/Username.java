package eu.lwstn.stappenstrijd.domain.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.NonNull;

import java.util.regex.Pattern;

@Data
public final class Username {
	private final static Pattern VALID_USERNAME = Pattern.compile("^[a-z0-9]+$");

	@NonNull
	@JsonValue
	private final String string;

	@JsonCreator(mode=JsonCreator.Mode.DELEGATING)
	public Username(@NonNull String string) {
		if (!VALID_USERNAME.matcher(string).matches()) {
			throw new IllegalArgumentException("Username does not match " + VALID_USERNAME);
		}

		this.string = string;
	}

	public String toString() {
		return string;
	}
}
