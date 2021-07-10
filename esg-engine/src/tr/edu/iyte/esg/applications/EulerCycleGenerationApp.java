package tr.edu.iyte.esg.applications;

import java.util.ArrayList;
import java.util.List;


import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import tr.edu.iyte.esg.conversion.*;
import tr.edu.iyte.esg.conversion.json.JSONFileToModelConverterWithSchemaValidation;
import tr.edu.iyte.esg.eventsequencegeneration.EventSequenceGeneratorHierholzerAlg;
import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.Model;
import tr.edu.iyte.esg.model.Vertex;

public class EulerCycleGenerationApp {
	public static void main(String[] args) {
		
		String schemaFile = "files/JSONFiles/ESGSimpleWithMultipleEdgesFromVertexSchema.json";
		String JSONFile = "files/JSONFiles/ESGEulerianWithMultipleEdgesFromVertex.json";
		
		JSONFileToModelConverterWithSchemaValidation jsonFileToModelConverterWithSchemaValidation = new JSONFileToModelConverterWithSchemaValidation(schemaFile, JSONFile);
		
		jsonFileToModelConverterWithSchemaValidation.JSONSchemaValidation();
		Model model = jsonFileToModelConverterWithSchemaValidation.JSONFileToModelConverter();
		
		ESGToJgraphConverter eulerianJGraphConverter = new ESGToJgraphConverter();

		List<Graph<Vertex, Edge>> jgraphList = eulerianJGraphConverter.buildJGraphListFromModel(model);

		EventSequenceGeneratorHierholzerAlg eventSequenceGenerator = new EventSequenceGeneratorHierholzerAlg();
		List<GraphPath<Vertex, Edge>> eulerCycleList = new ArrayList<GraphPath<Vertex, Edge>>();

		for (Graph<Vertex, Edge> graph : jgraphList) {
			GraphPath<Vertex, Edge> eulerCycle = eventSequenceGenerator.getEulerianCycle(graph);
			eulerCycleList.add(eulerCycle);
		}

		for (GraphPath<Vertex, Edge> graphPath : eulerCycleList) {
						
			for(Vertex vertex: graphPath.getVertexList()) {
				System.out.print(vertex.toString());
			}
			System.out.println();
		}
		
		
	}

}
