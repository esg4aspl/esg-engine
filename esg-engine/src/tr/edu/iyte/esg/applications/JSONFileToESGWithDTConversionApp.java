package tr.edu.iyte.esg.applications;

import java.io.FileNotFoundException;

import org.everit.json.schema.ValidationException;

import tr.edu.iyte.esg.conversion.json.JSONFileToESGConverter;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Vertex;
import tr.edu.iyte.esg.testgeneration.TestSuite;
import tr.edu.iyte.esg.testgeneration.TestSuiteGenerator;

public class JSONFileToESGWithDTConversionApp {

	public static void main(String[] args) throws FileNotFoundException, ValidationException {

		String JSONFile = "files/JSONFiles/EnterSpecialsESGWithDT.json";

		ESG ESG = JSONFileToESGConverter.parseJSONFileForESGWithDTCreation(JSONFile);

		System.out.println(ESG.toString());
		for (Vertex vertex : ESG.getDecisionTableMap().keySet()) {
			System.out.println(ESG.getDecisionTableMap().get(vertex).toString());
		}

		TestSuiteGenerator testSuiteGenerator = new TestSuiteGenerator();
		TestSuite testSuite = testSuiteGenerator.generateTestSuiteESGWithDecisionTable(ESG);
		System.out.println(testSuite.toString());

	}

}
