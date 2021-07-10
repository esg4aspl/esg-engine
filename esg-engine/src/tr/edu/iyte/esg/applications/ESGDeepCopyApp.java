package tr.edu.iyte.esg.applications;


import tr.edu.iyte.esg.conversion.json.JSONFileToModelConverterWithSchemaValidation;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.EdgeSimple;
import tr.edu.iyte.esg.model.Event;
import tr.edu.iyte.esg.model.EventSimple;
import tr.edu.iyte.esg.model.Model;
import tr.edu.iyte.esg.model.Vertex;
import tr.edu.iyte.esg.model.VertexSimple;

public class ESGDeepCopyApp {
	
	static Model model1, model2;
	static ESG esg;
	static Event event0, event1, event2, event3;
	static Vertex vertex0, vertex1, vertex2, vertex3;
	static Edge edge0, edge1, edge2;
	
	public static void main(String[] args) {

		// first example
		model1 = new Model("The Model");
		initialize();
		ESG newEsg = new ESG(esg);
		EdgeSimple edge3 = new EdgeSimple(3, vertex1, vertex3);
		esg.addEdge(edge3);
		System.out.print("old ESG: " + esg.toString());
		System.out.print("new ESG: " + newEsg.toString());

		// Second example
		String schemaFile = "files/JSONFiles/ESGEulerianWithMultipleEdgesFromVertex.json";
		String JSONFile = "files/JSONFiles/ESGEulerianWithMultipleEdgesFromVertex.json";
		JSONFileToModelConverterWithSchemaValidation jsonFileToModelConverterWithSchemaValidation = new JSONFileToModelConverterWithSchemaValidation(
				schemaFile, JSONFile);

		jsonFileToModelConverterWithSchemaValidation.JSONSchemaValidation();
		model2 = jsonFileToModelConverterWithSchemaValidation.JSONFileToModelConverter();
		for (ESG oldEsg : model2.getEsgList()) {
			ESG copyEsg = new ESG(oldEsg);
			oldEsg.addEdge(new EdgeSimple(50, oldEsg.getVertexByID(2), oldEsg.getVertexByID(4)));
			System.out.print("old ESG: " + oldEsg.toString());
			System.out.print("new ESG: " + copyEsg.toString());
		}
	
		
	}

	private static void initialize() {
		esg = new ESG(1, "Simple");
		model1.addESG(esg);

		event0 = new EventSimple(0, "[");
		esg.addEvent(event0);

		vertex0 = new VertexSimple(0, event0);
		esg.addVertex(vertex0);

		event1 = new EventSimple(1, "First Simple Event");
		esg.addEvent(event1);
		
		vertex1 = new VertexSimple(1, event1);
		esg.addVertex(vertex1);
		
		event2 = new EventSimple(2, "Second Simple Event");
		esg.addEvent(event2);
		
		vertex2 = new VertexSimple(2, event2);
		esg.addVertex(vertex2);
		
		event3 = new EventSimple(3, "]");
		esg.addEvent(event3);
		
		vertex3 = new VertexSimple(3, event3);
		esg.addVertex(vertex3);

		edge0 = new EdgeSimple(0, vertex0, vertex1);
		esg.addEdge(edge0);		
		//Test with comment out 
		//edge0 = new EdgeSimple(0, vertex0, vertex1); 
		//esg.addEdge(edge0);
		//to see "all vertex reaching to end vertex" error is caught
		//Currently not implemented

		edge1 = new EdgeSimple(1, vertex1, vertex2);
		//Test with edge1 = new EdgeSimple(1, vertex1, vertex1); to see uniqueness error is caught
		esg.addEdge(edge1);		

		edge2 = new EdgeSimple(2, vertex2, vertex3);
		esg.addEdge(edge2);		
	}	

}
