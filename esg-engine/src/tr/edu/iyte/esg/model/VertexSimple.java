package tr.edu.iyte.esg.model;

public class VertexSimple extends Vertex {

	public VertexSimple(int ID, Event event) {
		super(ID, event);	
	}
	
	public VertexSimple(int ID, Event event, String color) {
		super(ID, event, color);
	}


	public boolean isPseudoStartVertex() {
		if (event.getName().equals("[")) return true;
		else return false;
	}

	public boolean isPseudoEndVertex() {
		if (event.getName().equals("]")) return true;
		else return false;
	}

	public String getShape() {
		return "\", shape = ellipse";
	}

	public String getDotLanguageFormat() {
		return super.toString();
	}

	@Override
	public String getColor() {
		return color;
	}
}
