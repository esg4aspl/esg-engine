package tr.edu.iyte.esg.applications;

import tr.edu.iyte.esg.conversion.mxe.MXEFiletoESGConverter;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.testgeneration.TestSuite;
import tr.edu.iyte.esg.testgeneration.TestSuiteGenerator;

public class TransformedESGTestGeneration {

	public static void main(String[] args) {
		String fileName = "files/MXEFiles/UYMS2019CaseStudyModels/ProductSpanishCheckers.mxe";

		TestSuiteGenerator testSuiteGenerator = new TestSuiteGenerator();
		ESG ESG = null;
		try {
			ESG = MXEFiletoESGConverter.parseMXEFileForESGSimpleCreation(fileName);
			System.out.println(ESG.getName());
			// System.out.println(ESG.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		TestSuite ts = testSuiteGenerator.generateTestSuite(2, ESG);
		System.out.println(ts.toString());
	}

}
