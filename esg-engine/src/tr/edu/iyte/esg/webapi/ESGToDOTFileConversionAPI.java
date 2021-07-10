package tr.edu.iyte.esg.webapi;

import tr.edu.iyte.esg.conversion.dot.ESGToDOTFileConverter;
import tr.edu.iyte.esg.model.ESG;


public class ESGToDOTFileConversionAPI {
	
	
	public void convertESGToDOTFile(ESG ESG, String filePathAndName) {
		ESGToDOTFileConverter.buildDOTFileFromESG(ESG, filePathAndName);
	}
	
}
