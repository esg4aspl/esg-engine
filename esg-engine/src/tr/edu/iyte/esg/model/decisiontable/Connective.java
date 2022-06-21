package tr.edu.iyte.esg.model.decisiontable;

public class Connective implements Evaluable {
	
	private String connective;
	
	public Connective(String connective) {
		this.connective = connective;
	}

	
	public String evaluate() {
		return " " + connective + " ";
	}
}
