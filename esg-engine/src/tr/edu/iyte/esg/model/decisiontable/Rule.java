package tr.edu.iyte.esg.model.decisiontable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class Rule {

	private int ID;
	private HashMap<Condition, ConditionResult> column;
	private List<Variable> variableList;
	
	public Rule(int ID) {
		this.ID = ID;
		column = new LinkedHashMap<Condition, ConditionResult>();
		variableList = new LinkedList<Variable>();
	}

	public int getID() {
		return ID;
	}

	public HashMap<Condition, ConditionResult> getColumn() {
		return column;
	}
	
	//TODO will be implemented as expression evaluation method
	public void put(Condition condition, ConditionResult conditionResult) {
		column.put(condition, conditionResult);
	}
	
	public ConditionResult get(Condition condition) {
		return column.get(condition);
	}
	
	public List<Variable> getVariableList() {
		return variableList;
	}

	public void addVariable(Variable variable) {
		this.variableList.add(variable);
	}
	
	@Override
	public String toString() {
		String str = "(";
		
		for(Variable variable : variableList) {
			str += variable.toString() + ",";
		}
		String newStr1 = str.substring(0, str.length() - 1);
		newStr1 += "=>";
		
		for(Condition condition : column.keySet()) {
			newStr1 += "C" + condition.getID() + ":" + column.get(condition).toString() + ",";
		}
		String newStr2 = newStr1.substring(0, newStr1.length() - 1);
		
		newStr2 += "=>";
		newStr2 += "R" + this.ID;
		newStr2 += ")";
		
		return newStr2;
	}

	

	
}
