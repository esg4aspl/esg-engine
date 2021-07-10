package tr.edu.iyte.esg.model.validation;

public class AbstractValidator {
	
	/**
	 * 
	 */
	protected ValidationResult validationResult;
	
	/**
	 * Super class constructor
	 */
	public AbstractValidator() {
		validationResult = new ValidationResult();
	}

}
