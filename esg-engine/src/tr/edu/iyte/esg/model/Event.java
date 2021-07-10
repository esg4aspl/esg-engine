package tr.edu.iyte.esg.model;

public abstract class Event {

	final private int ID;
	final private String name;
	
	public Event(int ID, String name) {
		this.ID = ID;
		this.name = name;
	}

	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		
		return name;
	}

}
