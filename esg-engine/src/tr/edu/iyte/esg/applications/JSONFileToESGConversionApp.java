package tr.edu.iyte.esg.applications;

import java.io.FileNotFoundException;

import org.everit.json.schema.ValidationException;

import tr.edu.iyte.esg.conversion.json.JSONFileToESGConverter;
import tr.edu.iyte.esg.model.ESG;

public class JSONFileToESGConversionApp {

	public static void main(String[] args) throws FileNotFoundException, ValidationException {

		String JSONFile = "files/JSONFiles/DoesDepthReallyMatterFigure2.json";

		ESG ESG = JSONFileToESGConverter.parseJSONFileForESGSimpleCreation(JSONFile);

		System.out.println(ESG.toString());

	}

}
