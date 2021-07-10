package tr.edu.iyte.esg.model.validation;

import java.util.List;
import java.util.ArrayList;

public class ValidationResult {

	private List<ValidationFailure> errorList;
	public boolean isValid;

	public ValidationResult() {
		errorList = new ArrayList<ValidationFailure>();
	}

	public List<ValidationFailure> getErrorList() {
		return errorList;
	}

	public boolean isValid() {
		return isValid;
	}

	public void add(ValidationFailure validationFailure) {
		if (validationFailure == null) return;
		errorList.add(validationFailure);
	}
	
	public void addAll(ValidationResult validationResult) {
		if (validationResult == null) return;
		for(ValidationFailure validationFailure : validationResult.getErrorList())
			errorList.add(validationFailure);
	}

	@Override
	public String toString() {
		return "ValidationResult [errorList=" + errorList + "]";
	}

}
