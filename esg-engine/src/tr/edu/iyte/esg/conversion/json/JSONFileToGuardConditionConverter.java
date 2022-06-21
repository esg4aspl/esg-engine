package tr.edu.iyte.esg.conversion.json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import tr.edu.iyte.esg.model.guardcondition.GuardCondition;

public class JSONFileToGuardConditionConverter {

	public static List<GuardCondition> parseJSONFileForGuardConditionCreation(String fileName) throws FileNotFoundException {
		JSONTokener tokener = new JSONTokener(new FileReader(fileName));
		
		List<GuardCondition> guardConditionList = new LinkedList<GuardCondition>();

		try {
			
			JSONObject JSONObject = (JSONObject) tokener.nextValue();
			JSONArray guardConditionsArray = JSONObject.getJSONArray("guardConditions");

			for (int i = 0; i < guardConditionsArray.length(); i++) {
				
				JSONObject JSONGuardCondition = guardConditionsArray.getJSONObject(i);
				GuardCondition guardCondition = new GuardCondition();
				
				int ID = JSONGuardCondition.getInt("ID");
				String conditionName = JSONGuardCondition.getString("conditionName");
				boolean result = JSONGuardCondition.getBoolean("result");
				JSONArray edgesToBeRemoved = JSONGuardCondition.getJSONArray("edgesToBeRemoved");
				
				guardCondition.setID(ID);
				guardCondition.setConditionName(conditionName);
				guardCondition.setResult(result);
				
				for (int j = 0; j < edgesToBeRemoved.length(); j++) {
					String edgeToBeRemoved = edgesToBeRemoved.getString(j);
					String[] edge = edgeToBeRemoved.split("->");
					String source = edge[0];
					String target = edge[1];
					source.trim();
					target.trim();
					
					guardCondition.addEdgeToBeRemoved(source, target);
				}
				
				guardConditionList.add(guardCondition);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return guardConditionList;
	}

}
