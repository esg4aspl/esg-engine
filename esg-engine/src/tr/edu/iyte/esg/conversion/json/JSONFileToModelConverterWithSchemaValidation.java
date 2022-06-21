package tr.edu.iyte.esg.conversion.json;

import java.io.FileNotFoundException;

import org.everit.json.schema.ValidationException;

import tr.edu.iyte.esg.model.Model;

public class JSONFileToModelConverterWithSchemaValidation {

	private String schemaFile;
	private String JSONFile;

	public JSONFileToModelConverterWithSchemaValidation(String schemaFile, String JSONFile) {
		this.schemaFile = schemaFile;
		this.JSONFile = JSONFile;
	}

	/**
	 * JSON data file is validated by using corresponding schema
	 */
	public void JSONSchemaValidation() {
		JSONSchemaValidator JSONValidator = new JSONSchemaValidator();
		try {
			JSONValidator.validateJSONUsingSchema(schemaFile, JSONFile);
		} catch (ValidationException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * JSON file is parsed Model is created from parsing Necessary validation is
	 * performed
	 * @return Model
	 */
	public Model JSONFileToModelConverter() {
		JSONFileToESGConverter parser = new JSONFileToESGConverter();
		Model model = null;
		try {
			model = parser.parseJSONFileForModelCreation(JSONFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return model;
	}
	
	/**
	 * JSON file is parsed with DecisionTable Model is created from parsing Necessary validation is
	 * performed
	 * @return Model
	 */
	public Model JSONFileToModelConverterWithDT() {
		JSONFileToESGConverter parser = new JSONFileToESGConverter();
		Model model = null;
		try {
			model = parser.parseJSONFileForModelCreationWithDT(JSONFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return model;
	}
}

