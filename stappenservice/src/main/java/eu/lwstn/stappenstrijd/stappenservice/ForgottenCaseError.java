package eu.lwstn.stappenstrijd.stappenservice;

/**
 * Error indication an implementation error,
 * specifically a forgotten case in a switch(-like) statement.
 */
public class ForgottenCaseError extends Error {
	public ForgottenCaseError(Object value) {
		super("Implementation error: forgot case for " + value);
	}
}
