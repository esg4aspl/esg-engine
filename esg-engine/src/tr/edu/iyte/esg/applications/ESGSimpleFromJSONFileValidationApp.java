package tr.edu.iyte.esg.applications;

import java.io.FileNotFoundException;

import org.everit.json.schema.ValidationException;

import tr.edu.iyte.esg.conversion.json.JSONFileToModelConverterWithSchemaValidation;
import tr.edu.iyte.esg.model.Model;
import tr.edu.iyte.esg.model.validation.*;

public class ESGSimpleFromJSONFileValidationApp {
	
	public static void main(String[] args)  throws FileNotFoundException, ValidationException {
		String schemaFile = "files/JSONFiles/ESGSimpleWithMultipleEdgesFromVertexSchema.json";
		String JSONFile = "files/JSONFiles/ESGSimpleWithMultipleEdgesFromVertex.json";
		

		JSONFileToModelConverterWithSchemaValidation jsonFileToModelConverterWithSchemaValidation = new JSONFileToModelConverterWithSchemaValidation(schemaFile, JSONFile);
		
		jsonFileToModelConverterWithSchemaValidation.JSONSchemaValidation();
		Model model = jsonFileToModelConverterWithSchemaValidation.JSONFileToModelConverter();

		/*
		 * Model is validated
		 */
		ModelValidator modelValidator = new ModelValidator();
		ValidationResult validationResult = modelValidator.validate(model);

		System.out.println(validationResult.toString());
	}

}
