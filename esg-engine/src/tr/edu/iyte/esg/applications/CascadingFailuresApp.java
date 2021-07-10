package tr.edu.iyte.esg.applications;

import tr.edu.iyte.esg.model.validation.ValidationResult;

public class CascadingFailuresApp {

	static private ValidationResult validationResult;
	
	public static void main(String[] args) {
		validationResult = new ValidationResult();
		validationResult.add(null);
		validationResult.addAll(null);		
	}

}
