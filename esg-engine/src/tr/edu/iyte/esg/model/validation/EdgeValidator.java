package tr.edu.iyte.esg.model.validation;

import tr.edu.iyte.esg.model.Edge;

public class EdgeValidator extends AbstractValidator implements IValidator<Edge> {

	public EdgeValidator() {
		super();
	}

	@Override
	public ValidationResult validate(Edge context) {
		//TODO refactor using polymorphism
		if (context instanceof Edge) {
			EdgeSimpleValidator edgeSimpleValidator = new EdgeSimpleValidator();
			return edgeSimpleValidator.validate((Edge)context);
		}
		return null;
	}

}
