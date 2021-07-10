package tr.edu.iyte.esg.model.validation;

import tr.edu.iyte.esg.model.*;


public class VertexSimpleValidator extends AbstractValidator implements IValidator<VertexSimple> {

	@Override
	public ValidationResult validate(VertexSimple context) {
		if (context.getEvent().equals(null)) {
			validationResult.add(new ValidationFailure("VertexSimple", "Vertex.Event is null", context));
		}
		return validationResult;
	}
		
}
