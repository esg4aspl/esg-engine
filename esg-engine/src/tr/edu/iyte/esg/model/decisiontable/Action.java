package tr.edu.iyte.esg.model.decisiontable;

import tr.edu.iyte.esg.model.Vertex;

public class Action {
	
	private int ID;
	private Vertex actionEvent;
	
	
	public Action(int ID, Vertex actionEvent) {
		this.ID = ID;
		this.actionEvent = actionEvent;
	}
	
	public int getID() {
		return ID;
	}


	public Vertex getActionEvent() {
		return actionEvent;
	}
	
	@Override
	public String toString() {
		return actionEvent.getEvent().getName();
	}



	
	
	
}
