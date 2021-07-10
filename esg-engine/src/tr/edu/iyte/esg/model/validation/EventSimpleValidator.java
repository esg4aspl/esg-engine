package tr.edu.iyte.esg.model.validation;

import tr.edu.iyte.esg.model.*;

public class EventSimpleValidator extends AbstractValidator implements IValidator<EventSimple> {

	@Override
	public ValidationResult validate(EventSimple context) {
		if (context.getName().equals(null)) {
			validationResult.add(new ValidationFailure("EventSimple", "Event.Name is null", context));
			return validationResult;
		}
		return null;
	}
	
}
