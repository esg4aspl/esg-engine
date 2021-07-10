package tr.edu.iyte.esg.applications;

import java.io.FileNotFoundException;

import org.everit.json.schema.ValidationException;

import tr.edu.iyte.esg.conversion.dot.ESGToDOTFileConverter;
import tr.edu.iyte.esg.conversion.json.JSONFileToModelConverterWithSchemaValidation;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Model;

public class ESGToDOTFileApp {

	public static void main(String[] args) throws FileNotFoundException, ValidationException {
		String schemaFile = "files/JSONFiles/ESGSimpleWithMultipleEdgesFromVertexSchema.json";
		String JSONFile = "files/JSONFiles/InputContractTestingOfGUIsFigure2_4ESG.json";


		JSONFileToModelConverterWithSchemaValidation jsonFileToModelConverterWithSchemaValidation = new JSONFileToModelConverterWithSchemaValidation(schemaFile, JSONFile);

		jsonFileToModelConverterWithSchemaValidation.JSONSchemaValidation();
		Model model = jsonFileToModelConverterWithSchemaValidation.JSONFileToModelConverter();

		for(ESG esg: model.getEsgList()) {
			//System.out.println(esg.toString());
			String filePathAndName = "files/DotFiles/InputContractTestingOfGUIsFigure2_4ESG.dot";
			ESGToDOTFileConverter.buildDOTFileFromESG(esg, filePathAndName);
		}

	}

}
