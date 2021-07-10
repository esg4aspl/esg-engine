package tr.edu.iyte.esg.model.validation;

public interface IValidator<T> {

	/**
	 * This
	 */
	
	/**
	 * Validates the given context
	 * @param T- type of context to be validated
	 * @return {@link ValidationResult}}
	 */
	public ValidationResult validate(T context);
	
}
