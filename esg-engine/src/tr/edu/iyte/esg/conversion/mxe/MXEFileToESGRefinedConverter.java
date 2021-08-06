package tr.edu.iyte.esg.conversion.mxe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import tr.edu.iyte.esg.conversion.EventNameModifier;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.EdgeSimple;
import tr.edu.iyte.esg.model.Event;
import tr.edu.iyte.esg.model.EventSimple;
import tr.edu.iyte.esg.model.Vertex;
import tr.edu.iyte.esg.model.VertexRefinedByESG;
import tr.edu.iyte.esg.model.VertexSimple;

public class MXEFileToESGRefinedConverter {

	public static ESG parseMXEFileForESGRefinedCreation(String fileName) {

		ESG refinedESG = null;
		ESG finalRefinedESG = null;
		try {
			List<ESG> subESGList = parseMXEFileForSubESGCreation(fileName);
			List<String> refinedVertexEventNameList = getRefinedVertexEventNameList(fileName);
			String refinedESGXMLStr = getRefinedESGXMLToString(fileName);
			int index = fileName.indexOf(".");
			String refESGFileName = fileName.substring(0, index) + "_refESG.mxe";
			File file = new File(refESGFileName);
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(refinedESGXMLStr);
			writer.close();
			refinedESG = MXEFiletoESGConverter.parseMXEFileForESGSimpleCreation(refESGFileName);
			finalRefinedESG = new ESG(refinedESG.getID(), refinedESG.getName());

			for (Vertex vertex : refinedESG.getVertexList()) {
				String eventName = vertex.getEvent().getName();
				if (refinedVertexEventNameList.contains(eventName)) {
					int i = refinedVertexEventNameList.indexOf(eventName);
					ESG currentSubESG = subESGList.get(i);
					Event currentEvent = new EventSimple(finalRefinedESG.getNextEventID(), eventName);
					finalRefinedESG.addEvent(currentEvent);
					Vertex currentVertex = new VertexRefinedByESG(finalRefinedESG.getNextVertexID(), currentEvent,
							currentSubESG);
					finalRefinedESG.addVertex(currentVertex);
				} else {
					Event currentEvent = new EventSimple(finalRefinedESG.getNextEventID(), eventName);
					finalRefinedESG.addEvent(currentEvent);
					Vertex currentVertex = new VertexSimple(finalRefinedESG.getNextVertexID(), currentEvent);
					finalRefinedESG.addVertex(currentVertex);
				}
			}

			for (Edge edge : refinedESG.getEdgeList()) {
				Vertex sourceVertex = edge.getSource();
				Event sourceEvent = sourceVertex.getEvent();
				String sourceEventName = sourceEvent.getName();

				Vertex targetVertex = edge.getTarget();
				Event targetEvent = targetVertex.getEvent();
				String targetEventName = targetEvent.getName();

				Vertex currentSourceVertex = finalRefinedESG.getVertexByEventName(sourceEventName);
				Vertex currentTargetVertex = finalRefinedESG.getVertexByEventName(targetEventName);
				
				Edge currentEdge = new EdgeSimple(finalRefinedESG.getNextEdgeID(), currentSourceVertex,
						currentTargetVertex);
				finalRefinedESG.addEdge(currentEdge);
			}
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		return finalRefinedESG;
	}

	public static List<ESG> parseMXEFileForSubESGCreation(String fileName)
			throws SAXException, IOException, ParserConfigurationException {

		List<ESG> subESGList = new LinkedList<ESG>();
		try {
			File fXmlFile = new File(fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			NodeList mxGraphModelList = doc.getElementsByTagName("mxGraphModel");
			for (int i = mxGraphModelList.getLength() - 1; i > 0; i--) {
				Node node = mxGraphModelList.item(i);
				ESG ESG = new ESG(1, fileName + "_sub");
				Element element = (Element) node;
				parseMXGraphModel(ESG, element);
				subESGList.add(ESG);
			}
		} catch (Exception e) {
		}
		return subESGList;

	}

	public static void parseMXGraphModel(ESG ESG, Element mxGraphModel)
			throws SAXException, IOException, ParserConfigurationException {

		NodeList mxCellList = mxGraphModel.getElementsByTagName("mxCell");
		NodeList eventNodeList = mxGraphModel.getElementsByTagName("de.upb.adt.tsd.EventNode");

		List<String> vertexIdList = MXEFiletoESGConverter.parseNodeList(mxCellList, "vertex", "id");
		List<String> vertexNameList = MXEFiletoESGConverter.parseNodeList(eventNodeList, "name");
		List<String> edgeList = MXEFiletoESGConverter.parseNodeList(mxCellList, "edge", "source", "target");

		Map<String, Vertex> vertexIDvertexMap = new HashMap<String, Vertex>();
		for (int i = 0; i < vertexIdList.size(); i++) {
			String eventName = vertexNameList.get(i);
			eventName = EventNameModifier.modifyEventName(eventName);

			Event event = new EventSimple(ESG.getNextEventID(), eventName);
			ESG.addEvent(event);
			Vertex vertex = new VertexSimple(ESG.getNextVertexID(), event);
			ESG.addVertex(vertex);
			vertexIDvertexMap.put(vertexIdList.get(i), vertex);

		}
		for (int j = 0; j < edgeList.size(); j++) {
			String[] edges = edgeList.get(j).split(",");
			String source = edges[0];
			String target = edges[1];
			Vertex sourceVertex = vertexIDvertexMap.get(source);
			Vertex targetVertex = vertexIDvertexMap.get(target);
			Edge edge = new EdgeSimple(ESG.getNextEdgeID(), sourceVertex, targetVertex);
			ESG.addEdge(edge);
		}

	}

	private static String getRefinedESGXMLToString(String fileName) {
		String xmlFileToString = readXMLToString(fileName);
		int numberOfmxGraphModelStr = numberOfOccurrencesOfSubstring(xmlFileToString, "mxGraphModel");

		StringBuilder stringBuilder = new StringBuilder(xmlFileToString);

		for (int n = 1; n < numberOfmxGraphModelStr / 2; n++) {
			String xmlFileStr = stringBuilder.toString();
			int openIndexOfmxGraphModelStr = nThOccurrenceIndexOfSubstring(xmlFileStr, "mxGraphModel", 2);
			int closeIndexOfmxGraphModelStr = nThOccurrenceIndexOfSubstring(xmlFileStr, "mxGraphModel", 3);

			stringBuilder.delete(openIndexOfmxGraphModelStr - 1,
					closeIndexOfmxGraphModelStr + "mxGraphModel".length() + 1);
			String newXMLFileStr = stringBuilder.toString();
			stringBuilder.setLength(0);
			stringBuilder.append(newXMLFileStr);
		}

		return stringBuilder.toString();
	}

	private static List<String> getRefinedVertexEventNameList(String fileName) {
		String xmlFileToString = readXMLToString(fileName);
		List<String> eventNameList = new LinkedList<String>();

		int numberOfSubESGs = numberOfOccurrencesOfSubstring(xmlFileToString, "mxGraphModel as=\"graph\"");
		for (int n = numberOfSubESGs; n > 0; n--) {
			int indexOfSubESG = nThOccurrenceIndexOfSubstring(xmlFileToString, "mxGraphModel as=\"graph\"", n);

			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(xmlFileToString.substring(0, indexOfSubESG));
			stringBuilder.reverse();
			String reversedStr = stringBuilder.toString();
			int indexOfFirstDoubleQute = nThOccurrenceIndexOfSubstring(reversedStr, "\"", 1);
			int indexOfSecondDoubleQute = nThOccurrenceIndexOfSubstring(reversedStr, "\"", 2);
			String reversedEventName = reversedStr.substring(indexOfFirstDoubleQute + 1, indexOfSecondDoubleQute);
			stringBuilder.setLength(0);
			stringBuilder.append(reversedEventName);
			String eventName = stringBuilder.reverse().toString();
			eventNameList.add(eventName);

		}

		return eventNameList;
	}

	private static String readXMLToString(String fileName) {
		File xmlFile = new File(fileName);
		Reader fileReader = null;
		try {
			fileReader = new FileReader(xmlFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader bufReader = new BufferedReader(fileReader);

		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			line = bufReader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (line != null) {
			sb.append(line).append("\n");
			try {
				line = bufReader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String xmlToString = sb.toString();
		return xmlToString;
	}

	private static int nThOccurrenceIndexOfSubstring(String str1, String str2, int n) {

		String tempString = str1;
		int tempIndex = -1;
		int finalIndex = 0;
		for (int occurrence = 0; occurrence < n; ++occurrence) {
			tempIndex = tempString.indexOf(str2);
			if (tempIndex == -1) {
				finalIndex = 0;
				break;
			}
			tempString = tempString.substring(++tempIndex);
			finalIndex += tempIndex;
		}
		return --finalIndex;
	}

	private static int numberOfOccurrencesOfSubstring(String str, String subStr) {
		int lastIndex = 0;
		int count = 0;

		while (lastIndex != -1) {

			lastIndex = str.indexOf(subStr, lastIndex);

			if (lastIndex != -1) {
				count++;
				lastIndex += subStr.length();
			}
		}
		return count;
	}

}
