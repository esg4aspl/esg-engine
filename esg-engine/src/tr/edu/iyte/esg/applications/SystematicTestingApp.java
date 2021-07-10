package tr.edu.iyte.esg.applications;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import tr.edu.iyte.esg.conversion.dot.ESGToDOTFileConverter;
import tr.edu.iyte.esg.eventsequence.EventSequenceUtilties;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.systematictesting.ESGVariationsGenerator;
import tr.edu.iyte.esg.testgeneration.TestSuite;
import tr.edu.iyte.esg.testgeneration.TestSuiteGenerator;

public class SystematicTestingApp {
	

	public static void main(String[] args) {
		
		Set<String> eventSet = new LinkedHashSet<String>();
		eventSet.add("a");
		eventSet.add("b");
		eventSet.add("c");
		eventSet.add("d");
		eventSet.add("e");


		String variantESGFolderName = "VariantESG_5Vertex";
		ESGVariationsGenerator esgVariationsGenerator = new ESGVariationsGenerator();
		Set<ESG> ESGSet = esgVariationsGenerator.getESGVariationsSet(10, eventSet);
		
		TestSuiteGenerator testSuiteGenerator = new TestSuiteGenerator();
		int ESGId = 0;
		for (ESG ESG : ESGSet) {
			ESGId += 1;
			String name = "VariantESG";
			name += name + ESGId;
			ESGToDOTFileConverter.buildDOTFileFromESG(ESG, "files/SystematicTesting/" + variantESGFolderName +"/DOTFiles/" + name + ".dot");
		}
		ESGId = 0;
		
		for (ESG ESG : ESGSet) {
			ESGId += 1;
			String name = "VariantESG";
			name += name + ESGId;
			for (int coverageLength = 2; coverageLength < 4; coverageLength++) {
				TestSuite positiveTestSuite = testSuiteGenerator.generateTestSuite(coverageLength, ESG);
				TestSuite negativeTestSuite = testSuiteGenerator.generateNegativeTestSuite(coverageLength, ESG);
				String CESFilePath = "files/SystematicTesting/" + variantESGFolderName +"/TestSequences/CESs/" + name + "_length"
						+ coverageLength + ".txt";
				String FCESFilePath = "files/SystematicTesting/" + variantESGFolderName +"/TestSequences/FCESs/" + name
						+ "_length" + coverageLength + ".txt";
				try {
					EventSequenceUtilties.esgEventSequenceSetFileWriter(positiveTestSuite.getCompleteEventSequences(),
							CESFilePath);
					EventSequenceUtilties.esgEventSequenceSetFileWriter(negativeTestSuite.getCompleteEventSequences(),
							FCESFilePath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}


}
