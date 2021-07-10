package tr.edu.iyte.esg.model.decisiontable;

public class Expression {
	
	private int ID;
	private String expression;
	
	
	public Expression(int ID,String expression) {
		this.ID = ID;
		this.expression = expression;

	}
	
	public int getID() {
		return ID;
	}
	
	public String getExpression() {
		return expression;
	}
	
	public String toString() {
		return expression;
	}



}
