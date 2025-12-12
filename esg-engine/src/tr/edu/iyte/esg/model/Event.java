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

	public boolean isPseudoStartEvent() {
		return name.trim().equals("[");
	}

	public boolean isPseudoEndEvent() {
		return name.trim().equals("]");
	}

	@Override
	public String toString() {
		return name;
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

		if (other instanceof Event) {
			return (this.getID() == ((Event) other).getID());
		} else
			return false;
	}

}