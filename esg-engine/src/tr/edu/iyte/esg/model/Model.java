package tr.edu.iyte.esg.model;

import java.util.List;
import java.util.ArrayList;

public class Model {

	final private String name;
	private List<ESG> esgList;

	public Model(String name) {
		this.name = name;
		esgList = new ArrayList<ESG>();
	}

	public String getName() {
		return name;
	}

	public List<ESG> getEsgList() {
		return esgList;
	}

	public void addESG(ESG esg) {
		esgList.add(esg);
	}

	public ESG findESGByName(String esgName) {
		for(ESG esg: esgList)
			if(esg.getName().equals(esgName)) return esg;
		return null;
	}
	
	@Override
	public String toString() {
		String model = name + "\n";
		
		for(ESG ESG:getEsgList()) {
			model += ESG.toString() + "\n";
		}
		
		return model;
	}
}
