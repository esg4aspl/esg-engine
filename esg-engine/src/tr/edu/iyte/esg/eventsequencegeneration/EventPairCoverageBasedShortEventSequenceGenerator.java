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

import tr.edu.iyte.esg.coverageanalysis.TestSequenceCoverageAnalysisFromFile;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.Vertex;

public class EventPairCoverageBasedShortEventSequenceGenerator {

	private static Graph<String, DefaultEdge> directedGraph;

	public static void eventSequenceGenerator(String mxeFileName) {
		ESG productESG = TestSequenceCoverageAnalysisFromFile.buildESGFromMXEFile(mxeFileName);
		directedGraph = constructGraphForShortestPath(productESG);
		//		printTestSuite(testSequenceGenerator(productESG));
		printTestSuiteESGEngineFormat(testSequenceGenerator(productESG));
	}

	public static List<Edge> findStartEdges(ESG productESG) throws NullPointerException {
		List<Edge> startEdges = new ArrayList<Edge>();
		for (Vertex event : productESG.getVertexList()) {
			if (event.isPseudoStartVertex()) {
				//				System.out.println("First Event:" + event.getEvent().toString());
				for (Edge edge : productESG.getEdgeList()) {
					if(edge.getSource().equals(event)) {
						//						System.out.println("Edge: " + edge.toString());
						startEdges.add(edge);
					}
				}
			}
		}
		return startEdges;
	}

	public static List<Edge> findEndEdges(ESG productESG) throws NullPointerException {
		List<Edge> endEdges = new ArrayList<Edge>();
		for (Vertex event : productESG.getVertexList()) {
			if (event.isPseudoEndVertex()) {
				for (Edge edge : productESG.getEdgeList()) {
					if(edge.getTarget().equals(event)) {
						endEdges.add(edge);
					}
				}
			}
		}
		return endEdges;
	}

	public static Graph<String, DefaultEdge> constructGraphForShortestPath(ESG productESG) {
		Graph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		for(Edge edge : productESG.getEdgeList()) {
			//			String vertex = edge.getSource() + " --> " + edge.getTarget();
			//			System.out.println(vertex);
			//			System.out.println(edge.toString());
			//			directedGraph.addVertex(vertex);
			directedGraph.addVertex(edge.toString());
		}

		for(Edge edgeWithTarget : productESG.getEdgeList()) {
			for(Edge edgeWithSource : productESG.getEdgeList()) {
				if(edgeWithTarget.getTarget().getID() == edgeWithSource.getSource().getID()) {
					directedGraph.addEdge(edgeWithTarget.toString(), edgeWithSource.toString());
				}
			}
		}

		//		// Source Edge
		//		for (Vertex sourceFromVertex : productESG.getVertexMap().keySet()) {
		//			for (Vertex sourceToVertex : productESG.getVertexMap().get(sourceFromVertex)) {
		//				// Target Edge
		//				for(Vertex targetFromVertex : productESG.getVertexMap().get(sourceToVertex)) {
		//					for(Vertex targetToVertex : productESG.getVertexMap().get(targetFromVertex)) {
		//						String edgeSource = sourceFromVertex.getEvent().getName() + " --> " + sourceToVertex.getEvent().getName();
		//						String edgeTarget = targetFromVertex.getEvent().getName() + " --> " + targetToVertex.getEvent().getName();
		//						// set edges
		//						directedGraph.addEdge(edgeSource, edgeTarget);
		//					}
		//				}
		//			}
		//		}
		return directedGraph;
	}

	public static GraphPath<String, DefaultEdge> findShortestPathBetweenTwoEvents(Edge sourceEdge, Edge destinationEdge) {
		// Prints the shortest path from vertex i to vertex c. This certainly
		// exists for our particular directed graph.
		// System.out.println("Shortest path from i to c:");
		DijkstraShortestPath<String, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(directedGraph);
		SingleSourcePaths<String, DefaultEdge> iPaths = dijkstraAlg.getPaths(sourceEdge.toString());
		// System.out.println(iPaths.getPath(destinationVertex.getEvent().getName()) +
		// "\n");
		return iPaths.getPath(destinationEdge.toString());
	}

	public static List<GraphPath<String, DefaultEdge>> testSequenceGenerator(ESG productESG) {
		List<GraphPath<String, DefaultEdge>> testSuite = new ArrayList<GraphPath<String, DefaultEdge>>();

		// treating edges as vertices
		List<Edge> esg = new ArrayList<Edge>();
		esg.addAll(productESG.getEdgeList());

		// initialize the start and end event-pairs
		List<Edge> startEdges = findStartEdges(productESG);
		List<Edge> endEdges = findEndEdges(productESG);

		boolean startFlag = true;
		boolean endFlag = true;
		Random rand = new Random();
		while (!esg.isEmpty()) {
			int randomNumber = rand.nextInt(esg.size());
			GraphPath<String, DefaultEdge> firstHalfOfSequence = null;
			GraphPath<String, DefaultEdge> secondHalfOfSequence = null;
			// Guarantee that a valid path is generated
			while(firstHalfOfSequence == null) {
				// trying to ensure that every start event-pair is included
				if(startEdges.size() == 0) {
					startEdges = findStartEdges(productESG);
					startFlag = false;
				}
				int randomStartNumber = rand.nextInt(startEdges.size());
				Edge edge = esg.get(randomNumber);
				List<Edge> startAndEndEdges = new ArrayList<Edge>();
				startAndEndEdges.addAll(startEdges);
				startAndEndEdges.addAll(endEdges);
				// exit while loop if no intermediate event-pair left
				if(startAndEndEdges.containsAll(esg)) {
					System.out.println("Exit first");
					break;
				}
				// if start event-pair generated find a new intermediate event-pair
				else if(startEdges.contains(edge) || endEdges.contains(edge)) {
					randomNumber = rand.nextInt(esg.size());
					continue;
				}
				else {
					Edge startEdge = startEdges.get(randomStartNumber);
					firstHalfOfSequence = findShortestPathBetweenTwoEvents(startEdge, edge);
					if(startFlag)
						startEdges.remove(startEdge);
				}
			}
			// Generate the second half of the test sequence
			while(secondHalfOfSequence == null) {
				// trying to ensure that every end event-pair is included
				if(endEdges.size() == 0) {
					endEdges = findEndEdges(productESG);
					endFlag = false;
				}
				List<Edge> startAndEndEdges = new ArrayList<Edge>();
				startAndEndEdges.addAll(startEdges);
				startAndEndEdges.addAll(endEdges);
				// exit while loop if no intermediate event-pair left
				if(startAndEndEdges.containsAll(esg)) {
					System.out.println("Exit second");
					break;
				}
				int randomEndNumber = rand.nextInt(endEdges.size());
				Edge endEdge = endEdges.get(randomEndNumber);
				secondHalfOfSequence = findShortestPathBetweenTwoEvents(esg.get(randomNumber), endEdge);
				if(endFlag)
					endEdges.remove(endEdge);
			}
			/* enter block if there is no intermediate event-pair left,
			 * so it generates a sequence from any start event-pair and end-event pair
			 */
			List<Edge> startAndEndEdges = new ArrayList<Edge>();
			startAndEndEdges.addAll(startEdges);
			startAndEndEdges.addAll(endEdges);
			// exit while loop if no intermediate event-pair left
			if(startAndEndEdges.containsAll(esg)) {
				System.out.println("and here we are");
				while(firstHalfOfSequence == null) {
					int randomStartNumber = rand.nextInt(startEdges.size());
					int randomEndNumber = rand.nextInt(endEdges.size());
					firstHalfOfSequence = findShortestPathBetweenTwoEvents(startEdges.get(randomStartNumber), endEdges.get(randomEndNumber));
				}
			}
			// enter else block if intermediate event-pairs still exist
			else {
				firstHalfOfSequence.getVertexList().addAll(secondHalfOfSequence.getVertexList());
				firstHalfOfSequence.getEdgeList().addAll(secondHalfOfSequence.getEdgeList());
			}
			// add generated test sequence to test suite
			testSuite.add(firstHalfOfSequence);
			deleteVertexByName(firstHalfOfSequence.getVertexList(), esg);
			System.out.println("Remaining: " + esg.size());
			System.out.println(esg.toString());
		}
		
		System.out.println("Test Suite Size Before Minimization: " + testSuite.size());
		testSuiteMinimizationByCoveredTestSuite(testSuite);
		return testSuite;
	}

	private static void deleteVertexByName(List<String> edgeList, List<Edge> esg) {
		Iterator<Edge> it = esg.iterator();
		while (it.hasNext()) {
			Edge esgEdge = it.next();
			for (String vertex : edgeList) {
				if (esgEdge.toString().equals(vertex)) {
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

	public static void printTestSuiteESGEngineFormat(List<GraphPath<String, DefaultEdge>> testSuite) {
		System.out.println("Test Suite Size: " + testSuite.size());
		int totalEvent = 0;
		for (GraphPath<String, DefaultEdge> test : testSuite) {
//						System.out.println(test.toString());
			int numberOfEvents = test.getVertexList().size() - 1;
			System.out.print(numberOfEvents + " : ");
			for(int i=0; i<test.getEdgeList().size(); i++) {
				if(i<test.getEdgeList().size()-1) {
					String vertex[] = test.getEdgeList().get(i).toString().split(":");				
					System.out.print(vertex[0].substring(1) + ", ");
				}
				else {
					String vertex[] = test.getEdgeList().get(i).toString().split(":");				
					System.out.print(vertex[0].substring(1));
					System.out.println(" , " + vertex[1].substring(1));
				}
			}
			//			totalEvent = totalEvent + test.getVertexList().size();
			totalEvent = totalEvent + numberOfEvents;
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
					deletedTests.add(testSuite.get(i));
					break;
				}
				else if (testSuite.get(i).getVertexList().containsAll(testSuite.get(j).getVertexList())) {
					deletedTests.add(testSuite.get(j));
					break;
				}
			}

		}
		for (GraphPath<String, DefaultEdge> test : deletedTests) {
			testSuite.remove(test);
		}
	}
}
