package tr.edu.iyte.esg.model;

public class VertexSimple extends Vertex {

	public VertexSimple(int ID, Event event) {
		super(ID, event);	
	}

	public boolean isPseudoStartVertex() {
		if (event.getName().equals("[")) return true;
		else return false;
	}

	public boolean isPseudoEndVertex() {
		if (event.getName().equals("]")) return true;
		else return false;
	}

}
