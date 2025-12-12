package tr.edu.iyte.esg.model;

public abstract class Vertex implements Convertable {

	final private int ID;
	final protected Event event;
	private int degree;
	protected String color;
	
	public Vertex(int ID, Event event) {
		this.ID = ID;
		this.event = event;
		degree = 0;
	}
	
	public Vertex(int ID, Event event, String color) {
		this.ID = ID;
		this.event = event;
		this.color = color;
		degree = 0;
	}

	public int getID() {
		return ID;
	}

	public Event getEvent() {
		return event;
	}

	public boolean isPseudoStartVertex() {
		return event.isPseudoStartEvent();
	}

	public boolean isPseudoEndVertex() {
		return event.isPseudoEndEvent();
	}
	
	@Override
	public String toString() {
		return getEvent().getName();
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}
	
	public void inDegree() {
		this.degree++;
	}
	
	public void outDegree() {
		this.degree--;
	}
	
	@Override
	public int hashCode() {
		return getID();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;

		if (other == null)
			return false;

		if (other instanceof Vertex) {
			return (this.getID() == ((Vertex) other).getID());
		} else
			return false;
	}
}