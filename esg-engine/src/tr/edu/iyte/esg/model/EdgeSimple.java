package tr.edu.iyte.esg.model;

public class EdgeSimple extends Edge {


	
	public EdgeSimple(int ID, Vertex source, Vertex target) {
		super(ID, source, target);
	}

	public EdgeSimple(int ID, Vertex source, Vertex target, String color) {
		super(ID, source, target, color);
	}

	@Override
	public String getColor() {
		return color;
	}
}
