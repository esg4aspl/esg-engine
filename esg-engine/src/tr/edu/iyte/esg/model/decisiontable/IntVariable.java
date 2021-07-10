package tr.edu.iyte.esg.model.decisiontable;


public class IntVariable extends Variable {

	private Integer value;

	public IntVariable(int ID,String variableName) {
		super(ID,variableName);
	}

	public Integer getValue() {
		return value;
	}
	
	//TODO will be implemented as boolean SAT solution
	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		IntVariable intVariable = (IntVariable) obj;
		return intVariable != null && value.equals(intVariable.value);
	}

	@Override
	public int compareTo(Variable variable) {
		IntVariable intVariable = (IntVariable) variable;
		return value.compareTo(intVariable.value);
	}

	@Override
	public String toString() {
		return super.getVariableName() + ":" + this.getValue();
	}
	
}
