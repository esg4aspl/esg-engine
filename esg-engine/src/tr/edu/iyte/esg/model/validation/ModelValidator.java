package tr.edu.iyte.esg.model.validation;

import java.util.List;

import tr.edu.iyte.esg.model.*;

public class ModelValidator extends AbstractValidator implements IValidator<Model> {
	
	public ModelValidator() {
		super();
	}

	@Override
	public ValidationResult validate(Model context) {
		if (context.getName().equals(null)) {
			validationResult.add(new ValidationFailure("Model", "Model.Name is null", context));
			return validationResult;
		}
		validationResult.addAll(validateEsgs(context.getEsgList()));		
		return validationResult;
	}

	private ValidationResult validateEsgs(List<ESG> esgList) {
		ValidationResult esgsValidationResult = null;
		for(ESG esg : esgList) {
			ESGValidator esgValidator = new ESGValidator();
			esgsValidationResult = esgValidator.validate(esg);
		}
		return esgsValidationResult;
	}

}
