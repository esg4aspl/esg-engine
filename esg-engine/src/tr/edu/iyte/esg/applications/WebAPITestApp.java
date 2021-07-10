package tr.edu.iyte.esg.applications;


import tr.edu.iyte.esg.conversion.json.JSONFileToESGConverter;
import tr.edu.iyte.esg.model.ESG;

import tr.edu.iyte.esg.testgeneration.TestSuite;
import tr.edu.iyte.esg.webapi.TestGenerationAPI;


public class WebAPITestApp {

	public static void main(String[] args) {
		String json = "files/JSONFiles/ElevatorPL.json";
		//JSONFileToFeaturedESGConversionAPI JSONFileToFeaturedESGConversionAPI = new JSONFileToFeaturedESGConversionAPI();

		/*FeaturedESG featuredESG = JSONFileToFeaturedESGConversionAPI.parseJSONFile(json);
		System.out.println(featuredESG);
		TestSequenceCompositionAPI testSequenceCompositionAPI = new TestSequenceCompositionAPI();
		Set<EventSequence> seq = testSequenceCompositionAPI.composeTestSequences(featuredESG, 2);
		EventSequenceUtilities.esgEventSequenceSetPrinter(seq);*/
		
		ESG ESG = JSONFileToESGConverter.parseJSONFileForESGCreation(json);
		TestGenerationAPI testGenerationAPI = new TestGenerationAPI();
		TestSuite ts = testGenerationAPI.generateTestSuite(ESG, 2);
		System.out.println(ts);
		
	}

}
