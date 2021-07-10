package tr.edu.iyte.esg.model.validation;

import tr.edu.iyte.esg.model.*;

public class VertexRefinedByESGValidator extends AbstractValidator implements IValidator<VertexRefinedByESG> {

	@Override
	public ValidationResult validate(VertexRefinedByESG context) {
		if (context.getEvent().equals(null)) {
			validationResult.add(new ValidationFailure("VertexRefinedByESG", "Vertex.Event is null", context));
		}
		if (context.getSubESG().equals(null)) {
			validationResult.add(new ValidationFailure("VertexRefinedByESG", "Vertex.SubESG is null", context));
		}
		validationResult.addAll(validateESG(context.getSubESG()));
		return validationResult;
	}

	private ValidationResult validateESG(ESG context) {
		ValidationResult esgValidationResult = new ValidationResult();
		ESGValidator esgValidator = new ESGValidator();
		esgValidator.validate(context);
		return esgValidationResult;	
	}

}

