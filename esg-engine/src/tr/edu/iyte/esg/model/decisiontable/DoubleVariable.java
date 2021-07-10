package tr.edu.iyte.esg.model.decisiontable;

public class DoubleVariable extends Variable {


	private Double value;
	
	public DoubleVariable(int ID, String variableName) {
		super(ID,variableName);
		
	}

	public Double getValue() {
		return value;
	}
	
	//TODO will be implemented as boolean SAT solution
	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		DoubleVariable doubleVariable = (DoubleVariable) obj;
		return doubleVariable != null && value.equals(doubleVariable.value);
	}

	@Override
	public int compareTo(Variable variable) {
		DoubleVariable doubleVariable = (DoubleVariable) variable;
		return value.compareTo(doubleVariable.value);
	}

	@Override
	public String toString() {
		return super.getVariableName() + ":" + this.getValue();
	}


}
