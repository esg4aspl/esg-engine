package tr.edu.iyte.esg.model.decisiontable;


public abstract class Variable implements Comparable<Variable> {

	private int ID;
	private String variableName;
	
	public Variable(int ID, String variableName) {
		this.ID = ID;
		this.variableName = variableName;
	}
	
	public int getID() {
		return ID;
	}

	
	public String getVariableName() {
		return variableName;
	}

	
	@Override
	public int compareTo(Variable o) {
		return 0;
	}






	

	

}
