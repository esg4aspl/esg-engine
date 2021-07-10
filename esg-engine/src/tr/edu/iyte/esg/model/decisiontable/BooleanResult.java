package tr.edu.iyte.esg.model.decisiontable;

public class BooleanResult implements ConditionResult {

	private boolean result;
	
	public BooleanResult(boolean result) {
		this.result = result;
		
	}
	
	public boolean isResult() {
		return result;
	}
	
	@Override
	public String toString() {
		
		if(result) {
			return "T";
		}else {
			return "F";
		}
	}

}
