package tr.edu.iyte.esg.applications;

import tr.edu.iyte.esg.conversion.json.JSONFileToModelConverterWithSchemaValidation;


public class JSONFileWithSchemaValidationApp {

	public static void main(String[] args) {

		String schemaFile = "files/JSONFiles/ESGSimpleWithMultipleEdgesFromVertexSchema.json";
		String JSONFile = "files/JSONFiles/ESGEulerianWithMultipleEdgesFromVertex.json";

		JSONFileToModelConverterWithSchemaValidation jsonFileToModelConverterWithSchemaValidation = new JSONFileToModelConverterWithSchemaValidation(
				schemaFile, JSONFile);

		jsonFileToModelConverterWithSchemaValidation.JSONSchemaValidation();
	}

}
