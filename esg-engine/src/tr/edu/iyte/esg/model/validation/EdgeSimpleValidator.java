package tr.edu.iyte.esg.model.validation;

import tr.edu.iyte.esg.model.*;

public class EdgeSimpleValidator extends AbstractValidator implements IValidator<Edge> {

	@Override
	public ValidationResult validate(Edge context) {
		if (context.getID() < 0) {
			validationResult
				.add(new ValidationFailure("EdgeSimple", "Edge.ID is negative", context));
		}
		if (context.getSource() == null) {
			validationResult
				.add(new ValidationFailure("EdgeSimple", "Edge.Source is null", context));
		}
		if (context.getTarget() == null) {
			validationResult
				.add(new ValidationFailure("EdgeSimple", "Edge.Target is null", context));
		}
		if (context.getSource() != null && context.getTarget() != null) {
			if (context.getSource().isPseudoStartVertex() && context.getTarget().isPseudoStartVertex()) {
				validationResult.add(new ValidationFailure("EdgeSimple", "Self edge on Start Vertex", context));
			}
			if (context.getSource().isPseudoEndVertex() && context.getTarget().isPseudoEndVertex()) {
				validationResult.add(new ValidationFailure("EdgeSimple", "Self edge on End vertex", context));
			}
		}
		return validationResult;
	}

}
