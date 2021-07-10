package tr.edu.iyte.esg.model.decisiontable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public class DecisionTable {

	final private int ID;
	final private String name;
	private int lastExpressionID, lastConditionID, lastActionID, lastVariableID, lastRuleID;
	private ArrayList<Condition> conditionList;
	private ArrayList<Action> actionList;
	private ArrayList<Rule> ruleList;
	private HashMap<Rule, Set<Action>> actionTable;
	
	public DecisionTable(int ID, String name) {
		this.ID = ID;
		this.name = name;
		conditionList = new ArrayList<Condition>();
		actionList = new ArrayList<Action>();
		ruleList = new ArrayList<Rule>();
		actionTable = new LinkedHashMap<Rule, Set<Action>>();
		lastExpressionID = -1;
		lastConditionID = -1;
		lastActionID = -1;
		lastVariableID = -1;
		lastRuleID = -1;
	}

	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Condition> getConditionList() {
		return conditionList;
	}

	public ArrayList<Action> getActionList() {
		return actionList;
	}

	public ArrayList<Rule> getRuleList() {
		return ruleList;
	}

	public HashMap<Rule, Set<Action>> getActionTable() {
		return actionTable;
	}

	public void put(Rule rule, Action action) {
		
		if(actionTable.containsKey(rule)) {
			actionTable.get(rule).add(action);
		}else {
			Set<Action> actionSet = new LinkedHashSet<Action>();
			actionSet.add(action);
			actionTable.put(rule, actionSet);
		}
		
	}
	
	public Set<Action> getAction(Rule rule) {
		return actionTable.get(rule);
	}
	
	public void addCondition(Condition condition) {
		conditionList.add(condition);
	}

	public void addAction(Action action) {
		actionList.add(action);
	}

	public void addRule(Rule rule) {
		ruleList.add(rule);
	}
	
	public String toString() {
		String str = "";
		
		for(Rule rule : actionTable.keySet()) {
			str += rule.toString() + "-->" + actionTable.get(rule).toString() + "\n";
		}
		
		return str;
	}

	public int getLastActionID() {
		return lastActionID;
	}
	
	public int getNextActionID() {
		lastActionID++;
		return lastActionID;
	}

	public int getLastConditionID() {
		return lastConditionID;
	}
	
	public int getNextConditionID() {
		lastConditionID++;
		return lastConditionID;
	}

	public int getLastRuleID() {
		return lastRuleID;
	}
	
	public int getNextRuleID() {
		lastRuleID++;
		return lastRuleID;
	}

	public int getLastExpressionID() {
		return lastExpressionID;
	}
	
	public int getNextExpressionID() {
		lastExpressionID++;
		return lastExpressionID;
	}

	public int getLastVariableID() {
		return lastVariableID;
	}
	
	public int getNextVariableID() {
		lastVariableID++;
		return lastVariableID;
	}


}
