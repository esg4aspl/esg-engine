package tr.edu.iyte.esg.model;

public class VertexRefinedByESG extends Vertex {

	private ESG subESG;
	
	public VertexRefinedByESG(int ID, Event event, ESG subESG) {
		super(ID, event);
		this.subESG = subESG;
	}

	public ESG getSubESG() {
		return subESG;
	}	

}
