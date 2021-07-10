package tr.edu.iyte.esg.model.decisiontable;

public class DCResult implements ConditionResult {

	private String dontCareTag;
	
	public DCResult(String tag) {
		this.dontCareTag = tag;
		
	}

	public String getDontCareTag() {
		return dontCareTag;
	}
	
	@Override
	public String toString() {
		return dontCareTag;
	}

	

}
