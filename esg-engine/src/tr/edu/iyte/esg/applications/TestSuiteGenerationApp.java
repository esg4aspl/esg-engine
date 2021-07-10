package tr.edu.iyte.esg.applications;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import tr.edu.iyte.esg.conversion.mxe.MXEFiletoESGConverter;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.testgeneration.TestSuite;
import tr.edu.iyte.esg.testgeneration.TestSuiteGenerator;

public class TestSuiteGenerationApp {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {

		String fileName = "files/Cases/BankAccountPL/bankAccountProduct-dailyLimit.mxe";
		ESG ESG = MXEFiletoESGConverter.parseMXEFileForESGSimpleCreation(fileName);

		List<TestSuite> testSuites = new ArrayList<TestSuite>();
		TestSuiteGenerator testSuiteGenerator = new TestSuiteGenerator();

		TestSuite testSuite = testSuiteGenerator.generateTestSuite(ESG);

		testSuites.add(testSuite);
		System.out.println(testSuite.toString());

	}

}