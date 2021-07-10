package tr.edu.iyte.esg.testgeneration;

import java.util.LinkedHashSet;
import java.util.Set;

import tr.edu.iyte.esg.eventsequence.EventSequence;
import tr.edu.iyte.esg.model.ESG;

public class TestSuite {

	/**
	 * ESG which has the test suite
	 */
	private ESG ESG;

	/**
	 * Set of complete event sequences which belongs to ESG
	 */
	private Set<EventSequence> completeEventSequenceSet;

	/**
	 * Constructor
	 * 
	 * @param ESG
	 */
	public TestSuite(ESG ESG) {
		setESG(ESG);
		setCompleteEventSequences(new LinkedHashSet<EventSequence>());
	}

	public ESG getESG() {
		return ESG;
	}

	public void setESG(ESG esg) {
		ESG = esg;
	}

	public Set<EventSequence> getCompleteEventSequences() {
		return completeEventSequenceSet;
	}

	public void setCompleteEventSequences(Set<EventSequence> completeEventSequences) {
		if (completeEventSequences != null) {
			this.completeEventSequenceSet = completeEventSequences;
		}
	}

	@Override
	public String toString() {
		String testSuite = "";
		testSuite += /*ESG.toString() +*/ "Complete Event Sequences:\n";

		int noOfEvents = 0;

		for (EventSequence es : completeEventSequenceSet) {
			noOfEvents += es.length();
			//testSuite += es.length() + ": ";
			testSuite += es.toString() + "\n";
		}

		testSuite += "No. of CESs: " + completeEventSequenceSet.size() + "\n";
		testSuite += "No. of Events: " + noOfEvents;

		return testSuite;
	}
}
