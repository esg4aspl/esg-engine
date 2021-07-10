package tr.edu.iyte.esg.model.decisiontable;

import java.util.LinkedList;
import java.util.List;

public class Condition {

	private int ID;
	private List<Expression> expressionList;
	
	
	public Condition(int ID) {
		this.ID = ID;
		this.expressionList = new LinkedList<Expression>();
	}

	public int getID() {
		return ID;
	}

	public List<Expression> getExpressionList() {
		return expressionList;
	}
	
	public void addExpression(Expression expression) {
		this.expressionList.add(expression);
	}
	
	@Override
	public String toString() {
		String str = "";
		
		if(expressionList.size() > 1) {
			for(int i = 0; i < expressionList.size() - 1; i++) {
				str += expressionList.get(i).toString() + " AND ";
			}
			str += expressionList.get(expressionList.size() - 1).toString();
		}else
			str += expressionList.get(0).toString();
		
		return str;
		

		
	}

	
}
