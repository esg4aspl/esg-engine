package tr.edu.iyte.esg.model.decisiontable;


public class StringVariable extends Variable {

	private String value;

	public StringVariable(int ID,String variableName) {
		super(ID,variableName);
	}

	public String getValue() {
		return value;
	}
	
	//TODO will be implemented as boolean SAT solution
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		StringVariable stringVariable = (StringVariable) obj;
		return stringVariable != null && value.equals(stringVariable.value);
	}

	@Override
	public int compareTo(Variable variable) {
		StringVariable stringVariable = (StringVariable) variable;
		return value.compareTo(stringVariable.value);
	}
	
	@Override
	public String toString() {
		return super.getVariableName() + ":" + this.getValue();
	}

}
