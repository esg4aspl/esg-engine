package tr.edu.iyte.esg.coverageanalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import tr.edu.iyte.esg.conversion.mxe.MXEFiletoESGConverter;
import tr.edu.iyte.esg.esgtransforming.TransformedESGGenerator;
import tr.edu.iyte.esg.eventsequence.EventSequence;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.Vertex;
import tr.edu.iyte.esg.model.VertexRefinedBySequence;
import tr.edu.iyte.esg.model.sequenceesg.Sequence;



public class TestSequenceCoverageAnalyser {

	public static void analyseCoverageFromFile(int coverageLength, String ESGFilePath, String testCaseFilePath,
			String coverageAnalysisFilePath) {
		ESG productESG = buildESGFromMXEFile(ESGFilePath);

		List<String> testCases = testCasesFromFile(testCaseFilePath);
		List<String> edgeList = esgEdgeListForTestCasesFromFile(coverageLength, productESG);

		Map<String, Integer> edgeCoverageMap = edgeCoverageMap(edgeList, testCases);
		List<String> uncoveredEdgeList = uncoveredEdgeList(edgeCoverageMap);

		try {
			coverageAnalysisFileWriter(coverageAnalysisFilePath, edgeCoverageMap, productESG.getVertexList().size(),
					productESG.getEdgeList().size(), testCases.size(), numberOfEvents(testCases), uncoveredEdgeList.size(),
					percentageOfCoverage(edgeList, uncoveredEdgeList));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void analyseCoverage(int coverageLength, ESG productESG, Set<EventSequence> cesSet,String coverageAnalysisFilePath) {

		List<String> testCases = testCasesFromEventSequenceSet(cesSet);
		List<String> edgeList = esgEdgeListForTestCasesFromEventSequenceSet(coverageLength, productESG);

		Map<String, Integer> edgeCoverageMap = edgeCoverageMap(edgeList, testCases);
		List<String> uncoveredEdgeList = uncoveredEdgeList(edgeCoverageMap);

		try {
			coverageAnalysisFileWriter(coverageAnalysisFilePath, edgeCoverageMap, productESG.getVertexList().size(),
					productESG.getEdgeList().size(), testCases.size(), numberOfEvents(testCases), uncoveredEdgeList.size(),
					percentageOfCoverage(edgeList, uncoveredEdgeList));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<String> testCasesFromFile(String filePath) {
		List<String> lineList = new LinkedList<String>();
		File file = new File(filePath);

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			try {
				while ((line = br.readLine()) != null) {
					if(!line.equals(""))
						lineList.add(line);
				}
			} catch (IOException e) {

				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		return lineList;
	}

	public static List<String> testCasesFromEventSequenceSet(Set<EventSequence> cesSet) {
		List<String> lineList = new LinkedList<String>();
		Iterator<EventSequence> cesSetIterator = cesSet.iterator();
		String line;
		while (cesSetIterator.hasNext()) {
			line = cesSetIterator.next().toString();
			lineList.add(line);
		}

		return lineList;
	}

	public static ESG buildESGFromMXEFile(String fileName) {
		ESG ESG = null;
		try {
			ESG = MXEFiletoESGConverter.parseMXEFileForESGSimpleCreation(fileName);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ESG;
	}
	
	public static ESG buildESGFromDOTFile(String fileName) {
		ESG ESG = null;

		return ESG;
	}

	public static List<String> esgEdgeListForTestCasesFromFile(int coverageLength, ESG ESG) {
		int numberOfTransformations = coverageLength -2 ;
		if (coverageLength == 2) {
			return nonTransformedESGEdgeListForTestCasesFromFile(ESG);
		} else {

			TransformedESGGenerator transformedESGGenerator = new TransformedESGGenerator();
			ESG transformedESG = transformedESGGenerator.generateTransformedESG(coverageLength, ESG);

			List<String> edgeList = new LinkedList<String>();

			for (Edge edge : transformedESG.getRealEdgeList()) {

				Vertex source = edge.getSource();
				Sequence<Vertex> sourceSequence = ((VertexRefinedBySequence) source).getSequence();
				Vertex target = edge.getTarget();
				Sequence<Vertex> targetSequence = ((VertexRefinedBySequence) target).getSequence();

				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < sourceSequence.getSize(); i++) {
					sb.append(sourceSequence.getElement(i).toString().trim().replaceAll(" ", "_"));
					sb.append(", ");
				}
				sb.append(targetSequence.getElement(numberOfTransformations).toString().trim().replaceAll(" ", "_"));

				edgeList.add(sb.toString());
			}
			return edgeList;
		}
	}

	public static List<String> esgEdgeListForTestCasesFromEventSequenceSet(int coverageLength, ESG ESG) {
		int numberOfTransformations = coverageLength - 2;
		if (coverageLength == 2) {
			return nonTransformedESGEdgeListForTestCasesFromEventSequenceSet(ESG);
		} else {

			TransformedESGGenerator transformedESGGenerator = new TransformedESGGenerator();
			ESG transformedESG = transformedESGGenerator.generateTransformedESG(coverageLength, ESG);

			List<String> edgeList = new LinkedList<String>();

			for (Edge edge : transformedESG.getRealEdgeList()) {

				Vertex source = edge.getSource();
				Sequence<Vertex> sourceSequence = ((VertexRefinedBySequence) source).getSequence();
				Vertex target = edge.getTarget();
				Sequence<Vertex> targetSequence = ((VertexRefinedBySequence) target).getSequence();

				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < sourceSequence.getSize(); i++) {
					sb.append(sourceSequence.getElement(i).toString().trim());
					sb.append(", ");
				}
				sb.append(targetSequence.getElement(numberOfTransformations).toString().trim());

				edgeList.add(sb.toString());
			}
			return edgeList;
		}
	}

	public static List<String> nonTransformedESGEdgeListForTestCasesFromFile(ESG ESG) {
		List<String> edgeList = new LinkedList<String>();

		for (Edge edge : ESG.getRealEdgeList()) {
			String edgeName = edge.getSource().getEvent().getName().replaceAll(" ", "_") + ", "
					+ edge.getTarget().getEvent().getName().replaceAll(" ", "_");
			edgeList.add(edgeName);
		}

		return edgeList;

	}

	public static List<String> nonTransformedESGEdgeListForTestCasesFromEventSequenceSet(ESG ESG) {
		List<String> edgeList = new LinkedList<String>();

		for (Edge edge : ESG.getRealEdgeList()) {
			String edgeName = edge.getSource().getEvent().getName() + ", " + edge.getTarget().getEvent().getName();
			edgeList.add(edgeName);
		}

		return edgeList;

	}

	public static void coverageAnalysisFileWriter(String filePathAndName, Map<String, Integer> edgeCoverageMap,
			int numberOfNodes, int numberOfEdges, int numberOfCESs, int numberOfEvents, int numberOfUncoveredEdges,
			double coverage) throws IOException {
		Writer fileWriter = new FileWriter(filePathAndName);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.println("Number of nodes: " + numberOfNodes);
		printWriter.println("Number of edges: " + numberOfEdges);
		printWriter.println("Number of CESs: " + numberOfCESs);
		printWriter.println("Number of events: " + numberOfEvents);
		printWriter.println("Number of uncovered edges: " + numberOfUncoveredEdges);
		printWriter.println("Coverage: " + coverage);

		printWriter.println("Number of coverage\tEdge");
		for (Entry<String, Integer> entry : edgeCoverageMap.entrySet()) {
			printWriter.println(entry.getValue() + "\t" + entry.getKey());
		}

		if (numberOfUncoveredEdges > 0) {
			List<String> uncoveredEdgeList = uncoveredEdgeList(edgeCoverageMap);
			printWriter.println("Uncovered edges: ");
			for (String edge : uncoveredEdgeList) {
				printWriter.println(edge);
			}
		}

		printWriter.close();
	}

	public static int numberOfEvents(List<String> lineList) {
		int count = 0;

		for (String line : lineList) {
//System.out.println(line);
//System.out.println("line.substring(0,1) " + line.substring(0,1));
//System.out.println("line.substring(1,4) " + line.substring(1,4));
//System.out.println("line.substring(0,2) " + line.substring(0,2));
			if (line.substring(1, 4).equals(" : ")) {
				count += Integer.parseInt(line.substring(0, 1));
			} else {
				count += Integer.parseInt(line.substring(0, 2));
				
			}
		}

		return count;
	}

	public static Map<String, Integer> edgeCoverageMap(List<String> edgeList, List<String> lineList) {
		Map<String, Integer> edgeCoverageMap = new LinkedHashMap<String, Integer>();

		int zeroCounter = 0;

		for (String edge : edgeList) {
			int counter = 0;
			//System.out.println("edge " + edge);
			for (String line : lineList) {
				//System.out.println("line " + line);
				if (line.contains(edge)) {
					counter += count(line, edge);
					//System.out.println("counter " + counter);
				}
			}
			if (counter == 0) {
				zeroCounter++;
			}

			edgeCoverageMap.put(edge, counter);
		}
		edgeCoverageMap.put("Number of uncovered edges ", zeroCounter);

		return edgeCoverageMap;
	}

	public static int count(String text, String find) {
		int index = 0, count = 0, length = find.length();
		while ((index = text.indexOf(find, index)) != -1) {
			index += length;
			count++;
		}
		return count;
	}

	public static List<String> uncoveredEdgeList(Map<String, Integer> edgeCoverageMap) {

		List<String> uncoveredEdgeList = new LinkedList<String>();
		for (Entry<String, Integer> entry : edgeCoverageMap.entrySet()) {

			if (entry.getValue() == 0) {
				if (!entry.getKey().equals("Number of uncovered edges "))
					uncoveredEdgeList.add(entry.getKey());
			}

		}

		return uncoveredEdgeList;
	}

	public static double percentageOfCoverage(List<String> edgeList, List<String> uncoveredEdgeList) {

		double coverage = ((double) uncoveredEdgeList.size()) / ((double) edgeList.size()) * 100.0;

		if (uncoveredEdgeList.size() == 0) {
			return 100.0;
		} else {
			// System.out.printf("Coverage %.2f %s\n", 100.0 - coverage, "%");
			return 100.0 - coverage;
		}

	}

	public static void edgeCoverageMapPrinter(Map<String, Integer> edgeCoverageMap) {

		for (Entry<String, Integer> entry : edgeCoverageMap.entrySet()) {
			System.out.println(entry.getKey() + "	" + entry.getValue());
		}
	}

}
