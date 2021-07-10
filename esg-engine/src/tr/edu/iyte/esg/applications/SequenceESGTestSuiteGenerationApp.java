package tr.edu.iyte.esg.applications;

import tr.edu.iyte.esg.conversion.json.JSONFileToModelConverterWithSchemaValidation;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Model;
import tr.edu.iyte.esg.testgeneration.TestSuite;
import tr.edu.iyte.esg.testgeneration.TestSuiteGenerator;

public class SequenceESGTestSuiteGenerationApp {
	public static void main(String[] args) {

		String schemaFile = "files/JSONFiles/ESGSimpleWithMultipleEdgesFromVertexSchema.json";
		String JSONFile = "files/JSONFiles/InputContractTestingOfGUIsFigure2.json";
		JSONFile = "files/JSONFiles/ESG_cxp.json";
		final int N = 3;
		JSONFileToModelConverterWithSchemaValidation jsonFileToModelConverterWithSchemaValidation = new JSONFileToModelConverterWithSchemaValidation(
				schemaFile, JSONFile);
		jsonFileToModelConverterWithSchemaValidation.JSONSchemaValidation();

		Model model = jsonFileToModelConverterWithSchemaValidation.JSONFileToModelConverter();

		TestSuiteGenerator testSuiteGenerator = new TestSuiteGenerator();

		for (ESG ESG : model.getEsgList()) {

			TestSuite testSuite = testSuiteGenerator.generateTestSuite(N, ESG);

			System.out.println(testSuite.toString());

		}

	}
}
