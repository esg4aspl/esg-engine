package tr.edu.iyte.esg.applications;

import java.io.FileNotFoundException;
import org.everit.json.schema.ValidationException;
import tr.edu.iyte.esg.conversion.dot.ESGToDOTFileConverter;
import tr.edu.iyte.esg.conversion.json.JSONFileToModelConverterWithSchemaValidation;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Model;
import tr.edu.iyte.esg.model.validation.ModelValidator;
import tr.edu.iyte.esg.model.validation.ValidationResult;
import tr.edu.iyte.esg.testgeneration.TestSuite;
import tr.edu.iyte.esg.testgeneration.TestSuiteGenerator;

public class ESGWithDTToDOTFileApp {

	public static void main(String[] args) throws FileNotFoundException, ValidationException {
		String schemaFile = "files/JSONFiles/ESGSimpleWithMultipleEdgesFromVertexSchema.json";
		String JSONFile = "files/JSONFiles/decision_table.json";

		TestSuiteGenerator testSuiteGenerator = new TestSuiteGenerator();
		JSONFileToModelConverterWithSchemaValidation jsonFileToModelConverterWithSchemaValidation = new JSONFileToModelConverterWithSchemaValidation(schemaFile, JSONFile);

		jsonFileToModelConverterWithSchemaValidation.JSONSchemaValidation();
		Model model = jsonFileToModelConverterWithSchemaValidation.JSONFileToModelConverterWithDT();

		validateESG(model);

		for(ESG esg: model.getEsgList()) {
			printESG(esg);
			generateTestSuits(esg, testSuiteGenerator);
			constructDOTFile(esg);
		}
	}

	private static void validateESG(Model model) {
		System.out.println("********** ESG Validation task started. **********");
		ModelValidator modelValidator = new ModelValidator();
		ValidationResult modelValidationResult = modelValidator.validate(model);
		System.out.println("Model " + modelValidationResult);
	}

	private static void printESG(ESG esg) {
		System.out.println("********** ESG Print task started. **********");
		System.out.println(esg.toString());
	}

	private static void generateTestSuits(ESG esg, TestSuiteGenerator generator) {
		System.out.println("********** ESG Test Suite Generation task started. **********");
		TestSuite testSuite = generator.generateTestSuite(esg);
		System.out.println(testSuite.toString());
	}

	private static void constructDOTFile(ESG esg) {
		String filePathAndName = System.getProperty("user.dir") + "/files/DotFiles/decision_table.dot";
		ESGToDOTFileConverter.buildDOTFileFromESG(esg, filePathAndName);
	}
}
