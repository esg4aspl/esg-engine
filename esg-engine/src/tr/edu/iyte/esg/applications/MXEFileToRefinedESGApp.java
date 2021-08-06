package tr.edu.iyte.esg.applications;

import tr.edu.iyte.esg.conversion.mxe.MXEFileToESGRefinedConverter;
import tr.edu.iyte.esg.model.ESG;


public class MXEFileToRefinedESGApp {

	public static void main(String[] args) {

		String fileName = "files/MXEFiles/refinedESGWith2SubESGs.mxe";
		ESG refinedESG = null;
		try {
			refinedESG = MXEFileToESGRefinedConverter.parseMXEFileForESGRefinedCreation(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(refinedESG);

	}

}
