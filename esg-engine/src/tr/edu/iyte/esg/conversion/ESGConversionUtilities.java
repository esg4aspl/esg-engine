package tr.edu.iyte.esg.conversion;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import tr.edu.iyte.esg.conversion.mxe.MXEFiletoESGConverter;
import tr.edu.iyte.esg.model.ESG;

public class ESGConversionUtilities {
	
	public static ESG readESGFromMXEFile(String fileName, int esgID, String esgName) {
		ESG ESG = null;

		try {
			ESG = MXEFiletoESGConverter.parseMXEFileForESGSimpleCreation(fileName, esgID, esgName);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {  
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		return ESG;
	}

}
