package tr.edu.iyte.esg.coverageanalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import tr.edu.iyte.esg.conversion.mxe.MXEFiletoESGConverter;
import tr.edu.iyte.esg.esgtransforming.TransformedESGGenerator;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.Vertex;
import tr.edu.iyte.esg.model.VertexRefinedBySequence;
import tr.edu.iyte.esg.model.sequenceesg.Sequence;

public class TestSequenceCoverageAnalysisFromFile {

	
	public static void analyseCoverageFromFile(int coverageLength, String ESGFilePath, String testCaseFilePath,
			String coverageAnalysisFilePath) {
		ESG productESG = buildESGFromMXEFile(ESGFilePath);

		List<String> testCases = testCasesFromFile(testCaseFilePath);
		List<String> edgeList = esgEdgeListForTestCasesFromFile(coverageLength, productESG);

		Map<String, Integer> edgeCoverageMap = TestSequenceCoverageAnalyser.edgeCoverageMap(edgeList, testCases);
		List<String> uncoveredEdgeList = TestSequenceCoverageAnalyser.uncoveredEdgeList(edgeCoverageMap);

		try {
			TestSequenceCoverageAnalyser.coverageAnalysisFileWriter(coverageAnalysisFilePath, edgeCoverageMap, productESG.getVertexList().size(),
					productESG.getEdgeList().size(), testCases.size(), TestSequenceCoverageAnalyser.numberOfEvents(testCases), uncoveredEdgeList.size(),
					TestSequenceCoverageAnalyser.percentageOfCoverage(edgeList, uncoveredEdgeList));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	private static List<String> esgEdgeListForTestCasesFromFile(int coverageLength, ESG ESG) {
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
	
	private static List<String> nonTransformedESGEdgeListForTestCasesFromFile(ESG ESG) {
		List<String> edgeList = new LinkedList<String>();

		for (Edge edge : ESG.getRealEdgeList()) {
			String edgeName = edge.getSource().getEvent().getName().replaceAll(" ", "_") + ", "
					+ edge.getTarget().getEvent().getName().replaceAll(" ", "_");
			edgeList.add(edgeName);
		}

		return edgeList;

	}
}
