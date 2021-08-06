package tr.edu.iyte.esg.applications;

import tr.edu.iyte.esg.conversion.mxe.MXEFileToESGRefinedConverter;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.refinedesg.ESGRefinedToESGSimpleConversion;
import tr.edu.iyte.esg.testgeneration.TestSuite;
import tr.edu.iyte.esg.testgeneration.TestSuiteGenerator;

public class ESGRefinedTestGenerationApp {

	public static void main(String[] args) {
		String fileName = "files/MXEFiles/refinedESGWith2SubESGs.mxe";
		ESG refinedESG = null;
		try {
			refinedESG = MXEFileToESGRefinedConverter.parseMXEFileForESGRefinedCreation(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(refinedESG);
		ESGRefinedToESGSimpleConversion refinedESGTestGeneration = new ESGRefinedToESGSimpleConversion();
		ESG ESG = refinedESGTestGeneration.convertESGRefinedToESGSimple(refinedESG);
		System.out.println(ESG.toString());
		
		TestSuiteGenerator testSuiteGenerator = new TestSuiteGenerator();
		TestSuite testSuite = testSuiteGenerator.generateTestSuite(ESG);
		System.out.println(testSuite.toString());
	}
}
