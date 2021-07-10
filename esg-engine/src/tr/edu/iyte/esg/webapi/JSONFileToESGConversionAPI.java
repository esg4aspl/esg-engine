package tr.edu.iyte.esg.webapi;


import org.json.JSONObject;

import tr.edu.iyte.esg.conversion.json.JSONFileToESGConverter;
import tr.edu.iyte.esg.model.ESG;

public class JSONFileToESGConversionAPI {
	

	public ESG parseJSONFile(String fileName) {
		ESG ESG = JSONFileToESGConverter.parseJSONFileForESGCreation(fileName);
		return ESG;
	}
	

	public ESG parseJSONObject(JSONObject JSONObject) {
		ESG ESG = JSONFileToESGConverter.parseJSONObjectForESGSimpleCreation(JSONObject);
		return ESG;
	}

}
