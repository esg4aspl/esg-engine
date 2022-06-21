package tr.edu.iyte.esg.model.guardcondition;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GuardCondition {
	
	private int ID;
	private String conditionName;
	private boolean result;
	private Map<String,List<String>> edgesToBeRemoved;
	
	public GuardCondition() {
		edgesToBeRemoved = new LinkedHashMap<String,List<String>>();
	}
	
	public GuardCondition(int ID, String conditionName, boolean result) {
		setID(ID);
		setConditionName(conditionName);
		setResult(result);
		edgesToBeRemoved = new LinkedHashMap<String,List<String>>();
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getConditionName() {
		return conditionName;
	}

	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public Map<String,List<String>> getEdgesToBeRemoved() {
		return edgesToBeRemoved;
	}

	public void addEdgeToBeRemoved(String sourceEventName, String targetEventName) {
		
		if(edgesToBeRemoved.containsKey(sourceEventName)) {
			edgesToBeRemoved.get(sourceEventName).add(targetEventName);
		}else {
			List<String> targetEventNameList = new LinkedList<>();
			targetEventNameList.add(targetEventName);
			edgesToBeRemoved.put(sourceEventName, targetEventNameList);
		}
		
	}
	
	@Override
	public String toString() {
		return conditionName + " " + result;
	}
	



}
