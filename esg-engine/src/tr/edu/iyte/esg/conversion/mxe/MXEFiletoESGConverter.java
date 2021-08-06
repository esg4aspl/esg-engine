package tr.edu.iyte.esg.conversion.mxe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;

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
import tr.edu.iyte.esg.conversion.mxe.MXEFiletoESGConverter;
import tr.edu.iyte.esg.model.ESG;

import java.util.HashMap;

import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.EdgeSimple;
import tr.edu.iyte.esg.model.Event;
import tr.edu.iyte.esg.model.EventSimple;
import tr.edu.iyte.esg.model.Model;
import tr.edu.iyte.esg.model.Vertex;
import tr.edu.iyte.esg.model.VertexSimple;

public class MXEFiletoESGConverter {

	private Model model;

	public MXEFiletoESGConverter() {
		model = new Model("The model from MXE");
	}

	/**
	 * Parse the given XML file to create a model with a list of ESGs
	 * 
	 * @param fileName
	 * @return
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public Model parseMXEFileForModelCreation(String fileName)
			throws SAXException, IOException, ParserConfigurationException {
		ESG ESG = parseMXEFileForESGSimpleCreation(fileName);
		model.addESG(ESG);
		return model;
	}

	/**
	 * Parse the given file to create a simple ESG
	 * 
	 * @param fileName
	 * @return
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static ESG parseMXEFileForESGSimpleCreation(String fileName)
			throws SAXException, IOException, ParserConfigurationException {
		ESG ESG = parseMXEFileForESGSimpleCreation(fileName, -1, fileName);
		return ESG;
	}

	/**
	 * 
	 * @param fileName
	 * @param esgName
	 * @param esgID
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static ESG parseMXEFileForESGSimpleCreation(String fileName, int esgID, String esgName)
			throws SAXException, IOException, ParserConfigurationException {
		ESG ESG = new ESG(esgID, esgName);
		try {

			File fXmlFile = new File(fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList mxCellList = doc.getElementsByTagName("mxCell");
			NodeList eventNodeList = doc.getElementsByTagName("de.upb.adt.tsd.EventNode");

			List<String> vertexIdList = parseNodeList(mxCellList, "vertex", "id");
			List<String> vertexNameList = parseNodeList(eventNodeList, "name");
			List<String> edgeList = parseNodeList(mxCellList, "edge", "source", "target");

			Map<String, Vertex> vertexIDvertexMap = new HashMap<String, Vertex>();

			for (int temp = 0; temp < vertexIdList.size(); temp++) {
				String eventName = vertexNameList.get(temp);
				eventName = EventNameModifier.modifyEventName(eventName);

				Event event = new EventSimple(ESG.getNextEventID(), eventName);
				ESG.addEvent(event);

				Vertex vertex = new VertexSimple(ESG.getNextVertexID(), event);
				ESG.addVertex(vertex);

				vertexIDvertexMap.put(vertexIdList.get(temp), vertex);

			}

			for (int temp = 0; temp < edgeList.size(); temp++) {
				String[] edges = edgeList.get(temp).split(",");
				String source = edges[0];
				String target = edges[1];

				Vertex sourceVertex = vertexIDvertexMap.get(source);
				Vertex targetVertex = vertexIDvertexMap.get(target);

				Edge edge = new EdgeSimple(ESG.getNextEdgeID(), sourceVertex, targetVertex);

				ESG.addEdge(edge);

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return ESG;
	}

	public static List<String> parseNodeList(NodeList nodeList, String... attribute) {

		List<String> attributeList = new ArrayList<String>();

		for (int i = 0; i < nodeList.getLength(); i++) {

			Node node = nodeList.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {

				Element element = (Element) node;

				if (attribute[0].equals("vertex")) {
					if (element.getAttribute(attribute[0]).equals("1")) {
						String id = element.getAttribute(attribute[1]);
						attributeList.add(id);
					}

				} else if (attribute[0].equals("edge")) {
					if (element.getAttribute(attribute[0]).equals("1")) {
						String source = element.getAttribute(attribute[1]);
						String target = element.getAttribute(attribute[2]);
						String edge = source + "," + target;
						attributeList.add(edge);
					}
				} else {
					String eventName = element.getAttribute(attribute[0]);
					attributeList.add(eventName);
				}
			}
		}
		return attributeList;
	}
	

	


}
