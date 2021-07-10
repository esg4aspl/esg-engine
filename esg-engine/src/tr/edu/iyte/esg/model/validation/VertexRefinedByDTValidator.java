package tr.edu.iyte.esg.model.validation;

import tr.edu.iyte.esg.model.VertexRefinedByDT;
import tr.edu.iyte.esg.model.decisiontable.*;

public class VertexRefinedByDTValidator extends AbstractValidator implements IValidator<VertexRefinedByDT> {

	@Override
	public ValidationResult validate(VertexRefinedByDT context) {
		if (context.getEvent().equals(null)) {
			validationResult.add(new ValidationFailure("VertexRefinedByDT", "Vertex.Event is null", context));
		}
		if (context.getDecisionTable().equals(null)) {
			validationResult.add(new ValidationFailure("VertexRefinedByDT", "Vertex.DT is null", context));
		}
		validationResult.addAll(validateDecisionTable(context.getDecisionTable()));
		return validationResult;
	}

	private ValidationResult validateDecisionTable(DecisionTable context) {
		ValidationResult decisionTableValidationResult = new ValidationResult();
		DecisionTableValidator decisionTableValidator = new DecisionTableValidator();
		decisionTableValidator.validate(context);
		return decisionTableValidationResult;	
	}
}

