package tr.edu.iyte.esg.webapi;

import java.util.Set;

import tr.edu.iyte.esg.eventsequence.EventSequence;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.testgeneration.TestSuite;
import tr.edu.iyte.esg.testgeneration.TestSuiteGenerator;

public class TestGenerationAPI {

	public TestSuite generateTestSuite(ESG ESG, int coverageLength) {
		TestSuiteGenerator testSuiteGenerator = new TestSuiteGenerator();
		TestSuite testSuite = testSuiteGenerator.generateTestSuite(coverageLength, ESG);
		return testSuite;
	}

	public Set<EventSequence> generateTestSequences(ESG ESG, int coverageLength) {
		TestSuite testSuite = generateTestSuite(ESG, coverageLength);
		return testSuite.getCompleteEventSequences();
	}

}
