package tr.edu.iyte.esg.model.validation;

public class ValidationFailure {

	String propertyName;
	String errorMessage;
	Object attemptedValue;
	
	public ValidationFailure(String propertyName, String errorMessage) {
		this(propertyName, errorMessage, null);
	}

	public ValidationFailure(String propertyName, String errorMessage, Object attemptedValue) {
		this.propertyName = propertyName;
		this.errorMessage = errorMessage;
		this.attemptedValue = attemptedValue;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public Object getAttemptedValue() {
		return attemptedValue;
	}

	@Override
	public String toString() {
		return "ValidationFailure [propertyName=" + propertyName + ", errorMessage=" + errorMessage
				+ ", attemptedValue=" + attemptedValue + "]";
	}
	
}
