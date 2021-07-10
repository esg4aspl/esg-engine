package tr.edu.iyte.esg.applications;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import tr.edu.iyte.esg.conversion.mxe.MXEFiletoESGConverter;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.testgeneration.TestSuite;
import tr.edu.iyte.esg.testgeneration.TestSuiteGenerator;


public class NegativeTestSuiteGenerationApp {

	public static void main(String[] args) {
		String fileName = "files/Cases/BankAccountPL/bankAccountProduct-baseProduct.mxe";
		ESG ESG = null;
		try {
			ESG = MXEFiletoESGConverter.parseMXEFileForESGSimpleCreation(fileName);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		TestSuiteGenerator testSuiteGenerator = new TestSuiteGenerator();
		TestSuite negativeTestSuite = testSuiteGenerator.generateNegativeTestSuite(3, ESG);
		System.out.println(negativeTestSuite.toString());
		
		
	}

}
