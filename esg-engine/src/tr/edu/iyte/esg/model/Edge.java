package tr.edu.iyte.esg.model;

public abstract class Edge {

	final private int ID;
	final private Vertex source;
	final private Vertex target;
	final private Label label;
	protected String color;
	
	public Edge(int ID, Vertex source, Vertex target){
		this.ID = ID;
		this.source = source;
		this.target = target;
		label = null;
	}

	public Edge(int ID, Vertex source, Vertex target, Label label){
		this.ID = ID;
		this.source = source;
		this.target = target;
		this.label = label;
	}
	
	public Edge(int ID, Vertex source, Vertex target, String color){
		this.ID = ID;
		this.source = source;
		this.target = target;
		this.color = color;
		label = null;
	}

	public int getID() {
		return ID;
	}

	public Vertex getSource() {
		return source;
	}

	public Vertex getTarget() {
		return target;
	}

	public Label getLabel() {
		return label;
	}
	
	public abstract String getColor();
	
	public String toString() {
		return "<" + getSource().getEvent().getName() + " - " + getTarget().getEvent().getName() + ">"; 
	}

}
