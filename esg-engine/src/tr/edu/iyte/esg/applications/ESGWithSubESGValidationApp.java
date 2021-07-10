package tr.edu.iyte.esg.applications;

import tr.edu.iyte.esg.model.*;
import tr.edu.iyte.esg.model.validation.*;

public class ESGWithSubESGValidationApp {
	static Model model;
	static ESG esgMain, esgSub;
	static Event event0, event1, event2, event3;
	static Event event10, event11, event12, event13;
	static Vertex vertex0, vertex1, vertex2, vertex3;
	static Vertex vertex10, vertex11, vertex12, vertex13;
	static Edge edge0, edge1, edge2;
	static Edge edge10, edge11, edge12;
	
	public static void main(String[] args) {
		model = new Model("The Model");
		initializeSubESG();
		initializeMainESG();
		validateAll();
	}

	private static void initializeMainESG() {
		esgMain = new ESG(1, "Refined By SubESG");
		model.addESG(esgMain);

		event0 = new EventSimple(0, "[");
		esgMain.addEvent(event0);

		vertex0 = new VertexSimple(0, event0);
		esgMain.addVertex(vertex0);

		event1 = new EventSimple(1, "First Simple Event");
		esgMain.addEvent(event1);
		
		vertex1 = new VertexRefinedByESG(1, event1, esgSub);
		esgMain.addVertex(vertex1);
		
		event2 = new EventSimple(2, "Second Simple Event");
		esgMain.addEvent(event2);
		
		vertex2 = new VertexSimple(2, event2);
		esgMain.addVertex(vertex2);
		
		event3 = new EventSimple(3, "]");
		esgMain.addEvent(event3);
		
		vertex3 = new VertexSimple(3, event3);
		esgMain.addVertex(vertex3);

		edge0 = new EdgeSimple(0, vertex0, vertex1);
		esgMain.addEdge(edge0);		
		//Test with comment out 
		//edge0 = new EdgeSimple(0, vertex0, vertex1); 
		//esg.addEdge(edge0);
		//to see "all vertex reaching to end vertex" error is caught
		//Currently not implemented

		edge1 = new EdgeSimple(1, vertex1, vertex2);
		//Test with edge1 = new EdgeSimple(1, vertex1, vertex1); to see error is caught
		esgMain.addEdge(edge1);		

		edge2 = new EdgeSimple(2, vertex2, vertex3);
		esgMain.addEdge(edge2);		
	}	

	private static void initializeSubESG() {
		esgSub = new ESG(2, "Simple");
		model.addESG(esgSub);

		event10 = new EventSimple(10, "[");
		esgSub.addEvent(event10);

		vertex10 = new VertexSimple(10, event10);
		esgSub.addVertex(vertex10);

		event11 = new EventSimple(11, "SubESG First Simple Event");
		esgSub.addEvent(event11);
		
		vertex11 = new VertexSimple(11, event11);
		esgSub.addVertex(vertex11);
		
		event12 = new EventSimple(12, "SubESG Second Simple Event");
		esgSub.addEvent(event12);
		
		vertex12 = new VertexSimple(12, event12);
		esgSub.addVertex(vertex12);
		
		event13 = new EventSimple(13, "]");
		esgSub.addEvent(event13);
		
		vertex13 = new VertexSimple(13, event13);
		esgSub.addVertex(vertex13);

		edge10 = new EdgeSimple(10, vertex10, vertex11);
		//Test with comment out edge10 = new EdgeSimple(10, vertex10, vertex11); to see error is caught
		esgSub.addEdge(edge10);		

		edge11 = new EdgeSimple(11, vertex11, vertex12);
		//Test with edge11 = new EdgeSimple(11, vertex11, vertex11); to see error is caught
		esgSub.addEdge(edge11);		

		edge12 = new EdgeSimple(12, vertex12, vertex13);
		//Test with comment out edge12 = new EdgeSimple(12, vertex12, vertex13); to see error is caught
		esgSub.addEdge(edge12);		
	}	

	private static void validateAll() {
		System.out.println("ValidationESGWithSubESGApp");
		ModelValidator modelValidator = new ModelValidator();
		ValidationResult modelValidationResult = modelValidator.validate(model);
		System.out.println("Model " + modelValidationResult);
	}

}
