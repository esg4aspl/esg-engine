package tr.edu.iyte.esg.webapi;

import tr.edu.iyte.esg.conversion.ESGConversionUtilities;
import tr.edu.iyte.esg.model.ESG;

public class MXEFileToESGConversionAPI {
	
	public ESG parseMXEFile(String filePath, int esgID, String esgName) {
		ESG ESG = ESGConversionUtilities.readESGFromMXEFile(filePath, esgID, esgName);
		return ESG;
	}

}
