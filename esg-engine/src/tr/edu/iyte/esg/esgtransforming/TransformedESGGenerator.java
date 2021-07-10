package tr.edu.iyte.esg.esgtransforming;

import java.util.LinkedList;
import java.util.List;

import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.sequenceesg.ESGtoSequenceESGConverter;
import tr.edu.iyte.esg.model.sequenceesg.SequenceESGTransformer;

public class TransformedESGGenerator {

	public TransformedESGGenerator() {
		
	}
	
	/**
	 * 
	 * @param coverageLength
	 * @param ESG
	 * @return
	 */
	public ESG generateTransformedESG(int coverageLength, ESG ESG) {
		int numberOfTransformations = coverageLength - 2;
		ESGtoSequenceESGConverter esgToSequenceESGConverter = new ESGtoSequenceESGConverter();
		SequenceESGTransformer sequenceESGTransformer = new SequenceESGTransformer();

		ESG oneESG = esgToSequenceESGConverter.convert(ESG);
		ESG transformedESG = oneESG;

		
		for (int i = 1; i <= numberOfTransformations; i++) {
			transformedESG = sequenceESGTransformer.transform(transformedESG, oneESG);
		}

		return transformedESG;

	}
	
	public List<ESG> generateTransformedESGList(int coverageLength, ESG ESG) {
		int numberOfTransformations = coverageLength - 2;
		
		List<ESG> transformedESGList = new LinkedList<ESG>();

		ESGtoSequenceESGConverter esgToSequenceESGConverter = new ESGtoSequenceESGConverter();
		SequenceESGTransformer sequenceESGTransformer = new SequenceESGTransformer();

		ESG oneESG = esgToSequenceESGConverter.convert(ESG);
		transformedESGList.add(oneESG);

		ESG transformedESG = oneESG;
		for (int i = 1; i <= numberOfTransformations; i++) {
			transformedESG = sequenceESGTransformer.transform(transformedESG, oneESG);
			transformedESGList.add(transformedESG);
		}

		return transformedESGList;

	}

}
