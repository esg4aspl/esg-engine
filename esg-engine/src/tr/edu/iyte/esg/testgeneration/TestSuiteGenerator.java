package tr.edu.iyte.esg.testgeneration;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import tr.edu.iyte.esg.esgbalancing.StronglyConnectedBalancedESGGenerator;
import tr.edu.iyte.esg.esgbalancing.StronglyConnectedBalancedESGUtilities;
import tr.edu.iyte.esg.esgtransforming.TransformedESGGenerator;
import tr.edu.iyte.esg.eventsequence.ESGWithDecisionTableEventSequence;
import tr.edu.iyte.esg.eventsequence.EventSequence;
import tr.edu.iyte.esg.eventsequencegeneration.EulerCycleToCompleteEventSequenceGenerator;
import tr.edu.iyte.esg.eventsequencegeneration.EventSequenceGeneratorHierholzerAlg;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.Vertex;
import tr.edu.iyte.esg.model.VertexRefinedBySequence;
import tr.edu.iyte.esg.model.decisiontable.Action;
import tr.edu.iyte.esg.model.decisiontable.DecisionTable;
import tr.edu.iyte.esg.model.decisiontable.Rule;
import tr.edu.iyte.esg.model.sequenceesg.Sequence;
import tr.edu.iyte.esg.testgeneration.faulty.FaultyEventSequenceGenerator;

public class TestSuiteGenerator {

	public TestSuiteGenerator() {

	}

	public TestSuite generateTestSuiteESGWithDecisionTable(ESG ESGWithDT) {
		Map<Vertex, DecisionTable> dtMap = ESGWithDT.getDecisionTableMap();
		
		Set<EventSequence> completeEventSequences = new LinkedHashSet<EventSequence>();

		for (Entry<Vertex, DecisionTable> entry : dtMap.entrySet()) {
			EventSequence eventSequence = new ESGWithDecisionTableEventSequence();
			Vertex vertexRefinedByDT = entry.getKey();
			DecisionTable dt = entry.getValue();
			ESGWithDecisionTableEventSequence eventSequenceDT = (ESGWithDecisionTableEventSequence) eventSequence;
			for (Rule rule : dt.getActionTable().keySet()) {

				Set<Action> actionSet = dt.getActionTable().get(rule);
				for(Action action : actionSet) {
					eventSequenceDT.getEventSequence().add(vertexRefinedByDT);
					int indexOfVertex = eventSequenceDT.getEventSequence().size() - 1;
					eventSequenceDT.addRuleVertexPair(indexOfVertex,vertexRefinedByDT, rule);
					eventSequenceDT.getEventSequence().add(action.getActionEvent());
				}
			}
			
			completeEventSequences.add(eventSequence);
		}
		TestSuite testSuite = new TestSuite(ESGWithDT);
		testSuite.setCompleteEventSequences(completeEventSequences);
		
		return testSuite;
	}
	/**
	 * Generates the test suite of a given ESG
	 * 
	 * @param ESG
	 * @return
	 */
	public TestSuite generateTestSuite(ESG testESG) {
		
		ESG ESG = new ESG(testESG);

		Set<EventSequence> completeEventSequences = generateCompleteEventSequenceSet(ESG);

		TestSuite testSuite = new TestSuite(ESG);
		testSuite.setCompleteEventSequences(completeEventSequences);
		return testSuite;

	}
	
	/**
	 * Generates the test suite of a given ESG
	 * 
	 * @param ESG
	 * @return
	 */
	public TestSuite generateTestSuiteWithPseudoEvents(ESG testESG) {
		ESG ESG = new ESG(testESG);

		Set<EventSequence> completeEventSequences = generateCompleteEventSequenceSetWithPseudoEvents(ESG);

		TestSuite testSuite = new TestSuite(ESG);
		testSuite.setCompleteEventSequences(completeEventSequences);
		return testSuite;

	}

	/**
	 * Transforms the given ESG "coverageLength - 2" times and generates its test suite 
	 * If coverageLength is k, it generates "coverageLength-1"_ESG
	 * For example when coverageLength=2, 1_ESG is generated and it covers event pairs
	 * 
	 * @param coverageLength
	 * @param testESG
	 * @return
	 */
	public TestSuite generateTestSuite(int coverageLength, ESG testESG) {
		
		ESG ESG = new ESG(testESG);

		TransformedESGGenerator transformedESGGenerator = new TransformedESGGenerator();
		double startTime1 = System.nanoTime();
		ESG transformedESG = transformedESGGenerator.generateTransformedESG(coverageLength, ESG);
		double stopTime1 = System.nanoTime();
		double timeElapsed1 = stopTime1 - startTime1;
//		System.out.println("Execution time of ESG transformation in miliseconds  : "+ timeElapsed1 / (double) 1000000);
		
		double startTime2 = System.nanoTime();
		Set<EventSequence> completeEventSequences = generateCompleteEventSequenceSet(transformedESG);
		double stopTime2 = System.nanoTime();
		double timeElapsed2 = stopTime2 - startTime2;
		
		TestSuite testSuite = new TestSuite(transformedESG);
		Set<EventSequence> newCESs = new LinkedHashSet<EventSequence>();
		
		double startTime3 = System.nanoTime();
		for (EventSequence es : completeEventSequences) {
			newCESs.add(removeRepetitionsFromEventSequence(coverageLength, es));
		}
		double stopTime3 = System.nanoTime();
		double timeElapsed3 = stopTime3 - startTime3;
//		System.out.println("Execution time of test sequence generation in miliseconds  : "+ (timeElapsed2 + timeElapsed3) / (double) 1000000);
		
//		System.out.println("Total execution time of ESG test generation  : "+ (timeElapsed1 + timeElapsed2 + timeElapsed3) / (double) 1000000);
		
		testSuite.setCompleteEventSequences(newCESs);

		return testSuite;

	}
	
	/**
	 * Transforms the given ESG "coverageLength - 2" times and generates its test suite 
	 * If coverageLength is k, it generates "coverageLength-1"_ESG
	 * For example when coverageLength=2, 1_ESG is generated and it covers event pairs
	 * 
	 * @param coverageLength
	 * @param testESG
	 * @return
	 */
	public TestSuite generateNegativeTestSuite(int coverageLength, ESG testESG) {

		ESG ESG = new ESG(testESG);

		TransformedESGGenerator transformedESGGenerator = new TransformedESGGenerator();
		ESG transformedESG = transformedESGGenerator.generateTransformedESG(coverageLength , ESG);

		FaultyEventSequenceGenerator faultyEventSequenceGenerator = new FaultyEventSequenceGenerator(transformedESG);
		Set<EventSequence> faultyEventSequences = faultyEventSequenceGenerator.generateFaultyEventSequenceSet();

		TestSuite testSuite = new TestSuite(transformedESG);

		Set<EventSequence> newFCESs = new LinkedHashSet<EventSequence>();
		for (EventSequence es : faultyEventSequences) {
			EventSequence newFCES = removeRepetitionsFromEventSequence(coverageLength, es);
			if(!newFCESs.contains(newFCES)) {
				newFCESs.add(newFCES);
			}
			
		}

		testSuite.setCompleteEventSequences(newFCESs);

		return testSuite;

	}
	
	/**
	 * Transforms the given ESG "coverageLength - 2" times and generates its test suite 
	 * If coverageLength is k, it generates "coverageLength-1"_ESG
	 * For example when coverageLength=2, 1_ESG is generated and it covers event pairs
	 * 
	 * @param coverageLength
	 * @param testESG
	 * @return
	 */
	public TestSuite generateTestSuiteJgraphCPPBased(int coverageLength, ESG testESG) {
		ESG ESG = new ESG(testESG);

		TransformedESGGenerator transformedESGGenerator = new TransformedESGGenerator();
		ESG transformedESG = transformedESGGenerator.generateTransformedESG(coverageLength, ESG);
		
		TestSuiteGeneratorJGraphCPPBased alternativeTestGenerator = new TestSuiteGeneratorJGraphCPPBased();

		Set<EventSequence> completeEventSequences = alternativeTestGenerator.generateTestSuite(transformedESG);

		TestSuite testSuite = new TestSuite(transformedESG);

		Set<EventSequence> newCESs = new LinkedHashSet<EventSequence>();
		for (EventSequence es : completeEventSequences) {
			newCESs.add(removeRepetitionsFromEventSequence(coverageLength, es));
		}

		testSuite.setCompleteEventSequences(newCESs);

		return testSuite;

	}
	
	public TestSuite generateTestSuiteCPPSolverBased(int coverageLength, ESG testESG) {

		ESG ESG = new ESG(testESG);

		TransformedESGGenerator transformedESGGenerator = new TransformedESGGenerator();
		ESG transformedESG = transformedESGGenerator.generateTransformedESG(coverageLength, ESG);
		
		ESG stronglyConnectedTransformedESG = StronglyConnectedBalancedESGUtilities.transformESGtoStronglyConnectedESG(transformedESG);
		
		TestSuiteGeneratorCPPSolverBased CPPSolver = new TestSuiteGeneratorCPPSolverBased(stronglyConnectedTransformedESG.getVertexList().size());
		CPPSolver.createEdgeInfo(stronglyConnectedTransformedESG);
		CPPSolver.solve();
		
		CPPSolver.printCPT(0);
		
		TestSuite testSuite = new TestSuite(transformedESG);

		return testSuite;

	}
	
	/**
	 * Generates the test suite of a given sequence ESG
	 * 
	 * @param ESG
	 * @param k
	 * @return
	 */
	public TestSuite generateTestSuiteFromSequenceESG(ESG sesg, int coverageLength) {
		Set<EventSequence> completeEventSequences = generateCompleteEventSequenceSet(sesg);
		TestSuite testSuite = new TestSuite(sesg);
		Set<EventSequence> newCESs = new LinkedHashSet<EventSequence>();
		for (EventSequence es : completeEventSequences) {
			newCESs.add(removeRepetitionsFromEventSequence(coverageLength, es));
		}
		testSuite.setCompleteEventSequences(newCESs);
		return testSuite;
	}
	
	/**
	 * Generates the test suite of a given sequence ESG by cleaning certain events
	 * 
	 * @param ESG
	 * @param k
	 * @param deletenames
	 * @return
	 */
	public TestSuite generateTestSuiteFromSequenceESG(ESG sesg, int coverageLength, List<String> deletenames) {
		Set<EventSequence> completeEventSequences = generateCompleteEventSequenceSet(sesg);
		TestSuite testSuite = new TestSuite(sesg);
		Set<EventSequence> newCESs = new LinkedHashSet<EventSequence>();
		for (EventSequence es : completeEventSequences) {
			if(es.length() > 0) {
				newCESs.add(removeRepetitionsFromEventSequence(coverageLength, es, deletenames));
			}
		}
		testSuite.setCompleteEventSequences(newCESs);
		return testSuite;
	}

	
	private Set<EventSequence> generateCompleteEventSequenceSet(ESG ESG) {
	
		EventSequenceGeneratorHierholzerAlg eventSequenceGenerator = new EventSequenceGeneratorHierholzerAlg();
		EulerCycleToCompleteEventSequenceGenerator cesGenerator = new EulerCycleToCompleteEventSequenceGenerator();
		//ESGtoJgraphConverter jGraphConverter = new ESGtoJgraphConverter();
		StronglyConnectedBalancedESGGenerator balancedESGGenerator = new StronglyConnectedBalancedESGGenerator();

		Graph<Vertex, Edge> balancedAndStronglyConnectedESG = balancedESGGenerator.generateBalancedAndStronglyConnectedESG(ESG);


		//Graph<Vertex, Edge> jGraph = jGraphConverter.buildJGraphFromESG(balancedAndStronglyConnectedESG);
		GraphPath<Vertex, Edge> eulerCycle = eventSequenceGenerator.getEulerianCycle(balancedAndStronglyConnectedESG);

		
		//System.out.println(eulerCycle.toString());
		Set<EventSequence> completeEventSequences = cesGenerator.CESgenerator(eulerCycle);
		//Set<EventSequence> completeEventSequences = cesGenerator.CESgeneratorWithPseudoEvents(eulerCycle);

		return completeEventSequences;
	}
	
	private Set<EventSequence> generateCompleteEventSequenceSetWithPseudoEvents(ESG ESG) {
		EventSequenceGeneratorHierholzerAlg eventSequenceGenerator = new EventSequenceGeneratorHierholzerAlg();
		EulerCycleToCompleteEventSequenceGenerator cesGenerator = new EulerCycleToCompleteEventSequenceGenerator();
	//	ESGtoJgraphConverter jGraphConverter = new ESGtoJgraphConverter();

		StronglyConnectedBalancedESGGenerator balancedESGGenerator = new StronglyConnectedBalancedESGGenerator();

		Graph<Vertex, Edge> balancedAndStronglyConnectedESG = balancedESGGenerator.generateBalancedAndStronglyConnectedESG(ESG);

	//	Graph<Vertex, Edge> jGraph = jGraphConverter.buildJGraphFromESG(balancedAndStronglyConnectedESG);
		GraphPath<Vertex, Edge> eulerCycle = eventSequenceGenerator.getEulerianCycle(balancedAndStronglyConnectedESG);
		Set<EventSequence> completeEventSequences = cesGenerator.CESgeneratorWithPseudoEvents(eulerCycle);

		return completeEventSequences;
	}

	/**
	 * removes repeated sequences in a given event sequence
	 * @param coverageLength
	 * @param eventSequence
	 * @return
	 */
	public EventSequence removeRepetitionsFromEventSequence(int coverageLength, EventSequence eventSequence) {
		int numberOfTransformations = coverageLength - 2;
		EventSequence newEventSequence = new EventSequence();
		List<Vertex> eventSequenceList = new LinkedList<Vertex>();

		/*
		 * System.out.println("Event sequence " + eventSequence.length()); for (int i =
		 * 0; i < eventSequence.length(); i++) { Vertex vertex =
		 * eventSequence.getEventSequence().get(i); System.out.print(" " +
		 * vertex.toString()); } System.out.println();
		 */
		for (int i = 0; i < eventSequence.length(); i++) {

			Vertex vertex = eventSequence.getEventSequence().get(i);
			Sequence<Vertex> sequence = ((VertexRefinedBySequence) vertex).getSequence();
			/*
			 * System.out.println("Sequence " + sequence.getSize()); for (int d = 0; d <
			 * sequence.getSize(); d++) {
			 * 
			 * System.out.print(" " + sequence.getElement(d).getName()); }
			 * System.out.println();
			 */

			if (i == 0) {
				for (int j = 0; j < sequence.getSize(); j++) {
					// System.out.println("sequence j " + j + " " + sequence.getElement(j));
					eventSequenceList.add(sequence.getElement(j));
				}
			} else {
				if (sequence.getSize() == 1) {
					eventSequenceList.add(sequence.getElement(0));

				} else {
					for (int k = numberOfTransformations; k < sequence.getSize(); k++) {
						// System.out.println("sequence k " + k + " " + sequence.getElement(k));
						eventSequenceList.add(sequence.getElement(k));
					}
				}
			}
			// System.out.println();

		}
		newEventSequence.setEventSequence(eventSequenceList);

		return newEventSequence;
	}
 
	/**
	 * removes repeated sequences in a given event sequence by also deleting certain events
	 * @param coverageLength
	 * @param eventSequence
	 * @param deletenames
	 * @return
	 */
	private EventSequence removeRepetitionsFromEventSequence(int coverageLength, EventSequence eventSequence, List<String> deletenames) {
		int numberOfTransformations = coverageLength - 2;
		EventSequence newEventSequence = new EventSequence();
		List<Vertex> eventSequenceList = new LinkedList<Vertex>();
		
		if(eventSequence.length() > 0) {
			int i=0;
			boolean notAdded = true;
			while(notAdded) {
//System.out.println("--- "+eventSequence.toString()+" - "+i);
				VertexRefinedBySequence vertex = (VertexRefinedBySequence)eventSequence.getEventSequence().get(i);
				Sequence<Vertex> sequence = vertex.getSequence();
				for (int j = 0; j < sequence.getSize(); j++) {
					Vertex v = sequence.getElement(j);
					if(!deletenames.contains(v.getEvent().getName())) {
						eventSequenceList.add(sequence.getElement(j));
						notAdded = false;
					}
				}
				i++;
			}
			while(i<eventSequence.length()) {
				VertexRefinedBySequence vertex = (VertexRefinedBySequence)eventSequence.getEventSequence().get(i);
				Sequence<Vertex> sequence = vertex.getSequence();
				int j = numberOfTransformations;
				if(sequence.getSize() < numberOfTransformations) {
					j = 0;
				}
				while(j < sequence.getSize()) {
					Vertex v = sequence.getElement(j);
					if(!deletenames.contains(v.getEvent().getName())) {
						eventSequenceList.add(sequence.getElement(j));
					}
					j++;
				}
				i++;
			}
		}
		newEventSequence.setEventSequence(eventSequenceList);
		return newEventSequence;
	}

}
