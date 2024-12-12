package tr.edu.iyte.esg.eventsequencegeneration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import tr.edu.iyte.esg.coverageanalysis.TestSequenceCoverageAnalyser;
import tr.edu.iyte.esg.coverageanalysis.TestSequenceCoverageAnalysisFromFile;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Vertex;

public class EventCoverageBasedEventSequenceGenerator {

	private static Graph<String, DefaultEdge> directedGraph;

	public static void eventSequenceGenerator(String mxeFileName) {
		ESG productESG = TestSequenceCoverageAnalysisFromFile.buildESGFromMXEFile(mxeFileName);
		directedGraph = constructGraphForShortestPath(productESG);
		printTestSuite(testSequenceGenerator(productESG));
	}

	public static Vertex findStartEvent(ESG productESG) throws NullPointerException {
		Vertex startEvent = null;
		for (Vertex event : productESG.getVertexList()) {
			if (event.isPseudoStartVertex()) {
				return event;
			}
		}
		return startEvent;
	}

	public static Vertex findEndEvent(ESG productESG) throws NullPointerException {
		Vertex endEvent = null;
		for (Vertex event : productESG.getVertexList()) {
			if (event.isPseudoEndVertex()) {
				return event;
			}
		}
		return endEvent;
	}

	public static GraphPath<String, DefaultEdge> findShortestPathBetweenTwoEvents(Vertex sourceVertex,
			Vertex destinationVertex) {
		// Prints the shortest path from vertex i to vertex c. This certainly
		// exists for our particular directed graph.
		// System.out.println("Shortest path from i to c:");
		DijkstraShortestPath<String, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(directedGraph);
		SingleSourcePaths<String, DefaultEdge> iPaths = dijkstraAlg.getPaths(sourceVertex.getEvent().getName());
		// System.out.println(iPaths.getPath(destinationVertex.getEvent().getName()) +
		// "\n");
		return iPaths.getPath(destinationVertex.getEvent().getName());
	}

	public static Graph<String, DefaultEdge> constructGraphForShortestPath(ESG productESG) {
		Graph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		for (Vertex vertex : productESG.getVertexList()) {
			directedGraph.addVertex(vertex.getEvent().getName());
		}

		for (Vertex fromVertex : productESG.getVertexMap().keySet()) {
			for (Vertex toVertex : productESG.getVertexMap().get(fromVertex)) {
				directedGraph.addEdge(fromVertex.getEvent().getName(), toVertex.getEvent().getName());
			}
		}
		return directedGraph;
	}

	public static List<GraphPath<String, DefaultEdge>> testSequenceGenerator(ESG productESG) {
		List<GraphPath<String, DefaultEdge>> testSuite = new ArrayList<GraphPath<String, DefaultEdge>>();

		List<Vertex> esg = new ArrayList<Vertex>();
		esg.addAll(productESG.getVertexList());

		// Remove '[' and ']' events from ESG so they are not randomly selected
		esg.remove(findStartEvent(productESG));
		esg.remove(findEndEvent(productESG));

		Random rand = new Random();
		while (!esg.isEmpty()) {
			int randomNumber = rand.nextInt(esg.size());
			GraphPath<String, DefaultEdge> firstHalfOfSequence = findShortestPathBetweenTwoEvents(
					findStartEvent(productESG), esg.get(randomNumber));
			GraphPath<String, DefaultEdge> secondHalfOfSequence = findShortestPathBetweenTwoEvents(
					esg.get(randomNumber), findEndEvent(productESG));
			// secondHalfOfSequence.getEdgeList().remove(0);
			firstHalfOfSequence.getVertexList().addAll(secondHalfOfSequence.getVertexList());
			firstHalfOfSequence.getEdgeList().addAll(secondHalfOfSequence.getEdgeList());
			// System.out.println(firstHalfOfSequence);
			testSuite.add(firstHalfOfSequence);
			deleteVertexByName(firstHalfOfSequence.getVertexList(), esg);
		}
		System.out.println("Test Suite Size Before Minimization: " + testSuite.size());
		testSuiteMinimizationByCoveredTestSuite(testSuite);
		return testSuite;
	}

	private static void deleteVertexByName(List<String> vertexList, List<Vertex> esg) {
		Iterator<Vertex> it = esg.iterator();
		while (it.hasNext()) {
			Vertex esgVertex = it.next();
			for (String vertex : vertexList) {
				if (esgVertex.getEvent().getName().equals(vertex)) {
					it.remove();
					break;
				}
			}
		}
	}

	public static void printTestSuite(List<GraphPath<String, DefaultEdge>> testSuite) {
		System.out.println("Test Suite Size: " + testSuite.size());
		int totalEvent = 0;
		for (GraphPath<String, DefaultEdge> test : testSuite) {
			System.out.println(test.toString());
			totalEvent = totalEvent + test.getVertexList().size();
		}
		Set<String> vertex = new HashSet<String>();
		for (GraphPath<String, DefaultEdge> test : testSuite) {
			vertex.addAll(test.getVertexList());
		}
//		System.out.println("Covered Events: "+vertex.size());
		System.out.println("Total Event: " + totalEvent);
	}

	public static void testSuiteMinimizationByCoveredTestSuite(List<GraphPath<String, DefaultEdge>> testSuite) {
		List<GraphPath<String, DefaultEdge>> copyOfTestSuite = new ArrayList<GraphPath<String, DefaultEdge>>();
		copyOfTestSuite.addAll(testSuite);
		List<GraphPath<String, DefaultEdge>> deletedTests = new ArrayList<GraphPath<String, DefaultEdge>>();
		for (int i = 0; i < testSuite.size(); i++) {
			for (int j = i + 1; j < testSuite.size(); j++) {
				if (testSuite.get(j).getVertexList().containsAll(testSuite.get(i).getVertexList())) {
//					System.out.println(testSuite.get(j));
//					System.out.println("2Contains");
//					System.out.println(testSuite.get(i));
					deletedTests.add(testSuite.get(i));
				}
				if (testSuite.get(i).getVertexList().containsAll(testSuite.get(j).getVertexList())) {
//					System.out.println(testSuite.get(i));
//					System.out.println("Contains");
//					System.out.println(testSuite.get(j));
					deletedTests.add(testSuite.get(i));
				}
			}

		}
		for (GraphPath<String, DefaultEdge> test : deletedTests) {
			testSuite.remove(test);
		}
	}

}
