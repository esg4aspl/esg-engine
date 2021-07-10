package tr.edu.iyte.esg.applications;

import tr.edu.iyte.esg.model.*;
import tr.edu.iyte.esg.model.validation.*;

public class ESGSimpleValidationApp {

	static Model model;
	static ESG esg;
	static Event event0, event1, event2, event3;
	static Vertex vertex0, vertex1, vertex2, vertex3;
	static Edge edge0, edge1, edge2;
	
	public static void main(String[] args) {
		model = new Model("The Model");
		initialize();
		validateAll();
	}

	private static void initialize() {
		esg = new ESG(1, "Simple");
		model.addESG(esg);

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

	private static void validateAll() {
		System.out.println("ValidationESGSimpleApp");
		ModelValidator modelValidator = new ModelValidator();
		ValidationResult modelValidationResult = modelValidator.validate(model);
		System.out.println("Model " + modelValidationResult);
	}

}
