package tr.edu.iyte.esg.applications;

import tr.edu.iyte.esg.conversion.json.JSONFileToRefinedESGConverter;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.refinedesg.ESGRefinedToESGSimpleConversion;
import tr.edu.iyte.esg.testgeneration.TestSuite;
import tr.edu.iyte.esg.testgeneration.TestSuiteGenerator;


public class JSONFileToRefinedESGApp {

	public static void main(String[] args) {

		String fileName = "files/JSONFiles/ESGRefined.json";
		
		ESG refinedESG = JSONFileToRefinedESGConverter.parseJSONFileForESGRefinedCreation(fileName);
		System.out.println(refinedESG);
		
		ESGRefinedToESGSimpleConversion refinedESGTestGeneration = new ESGRefinedToESGSimpleConversion();
		ESG ESG = refinedESGTestGeneration.convertESGRefinedToESGSimple(refinedESG);
		System.out.println(ESG.toString());
		
		TestSuiteGenerator testSuiteGenerator = new TestSuiteGenerator();
		TestSuite testSuite = testSuiteGenerator.generateTestSuite(ESG);
		System.out.println(testSuite.toString());

	}

}
