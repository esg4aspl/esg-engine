package tr.edu.iyte.esg.model.validation;

import tr.edu.iyte.esg.model.Event;
import tr.edu.iyte.esg.model.EventSimple;

public class EventValidator extends AbstractValidator implements IValidator<Event> {

	public EventValidator() {
		super();
	}

	@Override
	public ValidationResult validate(Event context) {
		//TODO refactor using polymorphism
		if (context instanceof EventSimple) {
			EventSimpleValidator eventSimpleValidator = new EventSimpleValidator();
			return eventSimpleValidator.validate((EventSimple)context);
		}
		return null;
	}

}

