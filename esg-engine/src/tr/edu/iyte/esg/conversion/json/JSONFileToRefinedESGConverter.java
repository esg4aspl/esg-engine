package tr.edu.iyte.esg.conversion.json;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import tr.edu.iyte.esg.model.ESG;

public class JSONFileToRefinedESGConverter extends JSONFileToESGConverter {
	
	public JSONFileToRefinedESGConverter() {
		super();
	}
	
	
	/**
	 * Parse the given file to create a refined ESG
	 * 
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	public static ESG parseJSONFileForESGRefinedCreation(String fileName) {
		JSONTokener tokener = null;
		try {
			tokener = new JSONTokener(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parseJSONObjectForESGRefinedCreation((JSONObject) tokener.nextValue());
	}
	
	/**
	 * Parse the given json object to create a refined ESG
	 *
	 * @param esgJsonObject
	 * @return ESG object
	 */
	public static ESG parseJSONObjectForESGRefinedCreation(JSONObject refinedESGJSONObject) {
		ESG ESG = new ESG(-1,"tempName");
		try {
			int ID = refinedESGJSONObject.getInt("ID");
			String name = refinedESGJSONObject.getString("name");
			ESG = new ESG(ID, name);
			
			JSONArray JSONVertices = refinedESGJSONObject.getJSONArray("vertices");
			createESGEvents(ESG, JSONVertices);
			createESGVertices(ESG, JSONVertices);

			JSONArray JSONEdges = refinedESGJSONObject.getJSONArray("edges");
			createESGEdges(ESG, JSONEdges);
			
		}catch (JSONException e) {
			e.printStackTrace();
		}
		
		return ESG;
		
	}
	

}
