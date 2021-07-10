package tr.edu.iyte.esg.applications;

import tr.edu.iyte.esg.conversion.ESGConversionUtilities;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.validation.ESGValidator;

public class ESGValidationApp {

	public static void main(String[] args) {

		ESG bankAccount = ESGConversionUtilities.readESGFromMXEFile(
				"files/Cases/BankAccountPL/bankAccountProduct-cancellable.mxe", 1, "bankAccountProduct-cancellable");

		ESGValidator esgValidator = new ESGValidator();
		esgValidator.validate(bankAccount);

	}

}
