package tr.edu.iyte.esg.applications;

import java.io.FileNotFoundException;

import org.everit.json.schema.ValidationException;


import tr.edu.iyte.esg.conversion.json.JSONFileToESGConverter;
import tr.edu.iyte.esg.conversion.json.JSONSchemaValidator;
import tr.edu.iyte.esg.model.Model;
import tr.edu.iyte.esg.model.validation.*;

public class JSONMappingApp {

	public static void main(String[] args)  throws FileNotFoundException, ValidationException {
		String schemaFile = "files/JSONFiles/ESGSimpleWithMultipleEdgesFromVertexSchema.json";
		String JSONFile = "files/JSONFiles/ESGSimpleWithMultipleEdgesFromVertex.json";

		/*
		 * JSON data file is validated by using corresponding schema
		 */
		JSONSchemaValidator JSONValidator = new JSONSchemaValidator();
		try {
			JSONValidator.validateJSONUsingSchema(schemaFile, JSONFile);
		} catch (ValidationException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		/*
		 * JSON file is parsed Model is created from parsing 
		 * Necessary validation is
		 * performed
		 */
		JSONFileToESGConverter parser = new JSONFileToESGConverter();
		Model model = null;
		try {
			model = parser.parseJSONFileForModelCreation(JSONFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		/*
		 * Model is validated
		 */
		ModelValidator modelValidator = new ModelValidator();
		ValidationResult validationResult = modelValidator.validate(model);

		System.out.println(validationResult.toString());

	}

}
