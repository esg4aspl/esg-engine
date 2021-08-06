package tr.edu.iyte.esg.applications;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import tr.edu.iyte.esg.conversion.mxe.MXEFiletoESGConverter;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Model;
import tr.edu.iyte.esg.model.validation.ModelValidator;
import tr.edu.iyte.esg.model.validation.ValidationResult;


public class MXEFileToModelApp {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		String fileName = "files/Cases/ElevatorPL/overloaded.mxe";
		ESG ESG = null;
		try {
			ESG = MXEFiletoESGConverter.parseMXEFileForESGSimpleCreation(fileName);

		} catch (Exception e) {
			e.printStackTrace();
		}

		Model model = new Model("The Model");
		model.addESG(ESG);

		System.out.println("ValidationESGSimpleApp");
		ModelValidator modelValidator = new ModelValidator();
		ValidationResult modelValidationResult = modelValidator.validate(model);
		System.out.println("Model " + modelValidationResult);

	}

}
