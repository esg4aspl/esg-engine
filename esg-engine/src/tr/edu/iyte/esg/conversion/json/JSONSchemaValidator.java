package tr.edu.iyte.esg.conversion.json;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONSchemaValidator {
	
	public JSONSchemaValidator() {
	}
	
	/*
	 * Using org.everit.json.schema
	 * JSON and its corresponding schema file is validated
	 */
	public void validateJSONUsingSchema(String schemaFile, String JSONFile) throws FileNotFoundException, ValidationException {

		FileInputStream schemaInputStream = new FileInputStream(schemaFile);
		JSONObject ESGSimpleSchema = new JSONObject(new JSONTokener(schemaInputStream));
		Schema ESGSimpleSchemaLoaded = SchemaLoader.load(ESGSimpleSchema);
		
		FileInputStream JSONInputStream = new FileInputStream(JSONFile);
		JSONObject ESGData = new JSONObject(new JSONTokener(JSONInputStream));
		
		ESGSimpleSchemaLoaded.validate(ESGData);		
	}

}
