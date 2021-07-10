package tr.edu.iyte.esg.model;

public abstract class Vertex {

	final private int ID;
	final protected Event event;
	private int degree;
	
	public Vertex(int ID, Event event) {
		this.ID = ID;
		this.event = event;
		degree = 0;
	}

	public int getID() {
		return ID;
	}

	public Event getEvent() {
		return event;
	}

	public boolean isPseudoStartVertex() {
		return false;
	}

	public boolean isPseudoEndVertex() {
		return false;
	}
	
	public String toString() {
		return getEvent().getName() + " ";
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
	public boolean equals(Object obj) {
		if(this.getEvent().getName().equals(((Vertex)obj).getEvent().getName())) {
			return true;
		}
		return false;
	}
}
