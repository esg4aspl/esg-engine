package tr.edu.iyte.esg.model.decisiontable;

import java.util.LinkedList;
import java.util.List;

public class Condition {

	private int ID;
	private List<Evaluable> evaluableList;
	
	
	public Condition(int ID) {
		this.ID = ID;
		this.evaluableList = new LinkedList<Evaluable>();
	}

	public int getID() {
		return ID;
	}

	public List<Evaluable> getEvaluableList() {
		return evaluableList;
	}
	
	public void addEvaluable(Evaluable evaluable) {
		this.evaluableList.add(evaluable);
	}
	
	@Override
	public String toString() {
		String str = "";
		
		for(Evaluable eval: evaluableList) {
			str += eval.evaluate();
		}
		
		return str;
	}

	
}
