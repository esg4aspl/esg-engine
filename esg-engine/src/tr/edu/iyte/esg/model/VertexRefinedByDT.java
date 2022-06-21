package tr.edu.iyte.esg.model;

import tr.edu.iyte.esg.model.decisiontable.DecisionTable;

public class VertexRefinedByDT extends Vertex {
	
	private DecisionTable decisionTable;
	
	public VertexRefinedByDT(int ID, Event event, DecisionTable decisionTable) {
		super(ID, event);
		this.decisionTable = decisionTable;
	}
	
	public VertexRefinedByDT(int ID, Event event) {
		super(ID, event);
	}
	
	public void setDecisionTable(DecisionTable decisionTable) {
		this.decisionTable = decisionTable;
	}

	public DecisionTable getDecisionTable() {
		return decisionTable;
	}
	
	public String getShape() {
		return "\", shape = tripleoctagon";
	}

	@Override
	public String getDotLanguageFormat() {
		return super.toString();
	}

	@Override
	public String getColor() {
		return "purple";
	}
}
