package tr.edu.iyte.esg.applications;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import tr.edu.iyte.esg.conversion.json.JSONFileToESGConverter;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Model;
import tr.edu.iyte.esg.model.sequenceesg.ESGtoSequenceESGConverter;
import tr.edu.iyte.esg.model.sequenceesg.SequenceESGTransformer;

public class SequenceESGTransformationApp {
	public static void main(String[] args) {
		final int N = 5;
		String JSONFile = "files/JSONFiles/InputContractTestingOfGUIsFigure2.json";

		JSONFileToESGConverter parser = new JSONFileToESGConverter();
		Model model = null;
		try {
			model = parser.parseJSONFileForModelCreation(JSONFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ESGtoSequenceESGConverter c = new ESGtoSequenceESGConverter();
		SequenceESGTransformer t = new SequenceESGTransformer();
		List<ESG> newESGs = new LinkedList<ESG>();
		for (ESG esg : model.getEsgList()) {
			System.out.println(esg);

			ESG oneESG = c.convert(esg);
			newESGs.add(oneESG);
			System.out.println(oneESG);
			// SequenceESGUtilities.printEdges(oneESG);
			System.out.println();

			ESG kESG = oneESG;
			for (int i = 1; i <= N; i++) {
				kESG = t.transformIncludingShorterSequences(kESG, oneESG);
				newESGs.add(kESG);
				System.out.println(kESG);
				// SequenceESGUtilities.printEdges(kESG);
				System.out.println();
			}
		}
	}
}
