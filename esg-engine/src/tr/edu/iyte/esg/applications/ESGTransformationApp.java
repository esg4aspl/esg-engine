package tr.edu.iyte.esg.applications;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import tr.edu.iyte.esg.conversion.dot.ESGToDOTFileConverter;
import tr.edu.iyte.esg.conversion.mxe.MXEFiletoESGConverter;
import tr.edu.iyte.esg.esgtransforming.TransformedESGGenerator;
import tr.edu.iyte.esg.model.ESG;

public class ESGTransformationApp {

	public static void main(String[] args) {

		String fileName = "files/Cases/BankAccountPL/bankAccountProduct-baseProduct.mxe";
		ESG ESG = null;
		try {
			ESG = MXEFiletoESGConverter.parseMXEFileForESGSimpleCreation(fileName, 1, "bankAccountProduct_baseProduct");
		} catch (SAXException | IOException | ParserConfigurationException e) {

			e.printStackTrace();
		}
		final int N = 1;

		TransformedESGGenerator transformedESGGenerator = new TransformedESGGenerator();
		ESG esg = transformedESGGenerator.generateTransformedESG(N, ESG);

		ESGToDOTFileConverter.buildDOTFileFromESG(esg,
				"files/Cases/BankAccountPL/allProductsDOTFiles" + esg.getName() + ".dot");

	}

}
