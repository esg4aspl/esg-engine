package tr.edu.iyte.esg.testgeneration;

import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.cycle.ChinesePostman;
import org.jgrapht.graph.DefaultWeightedEdge;

import tr.edu.iyte.esg.conversion.ESGToJgraphConverter;
import tr.edu.iyte.esg.esgbalancing.StronglyConnectedBalancedESGUtilities;
import tr.edu.iyte.esg.eventsequence.EventSequence;
import tr.edu.iyte.esg.eventsequencegeneration.EulerCycleToCompleteEventSequenceGenerator;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Vertex;


public class TestSuiteGeneratorJGraphCPPBased {
	
	public Set<EventSequence> generateTestSuite(ESG ESG) {
		ESG stronglyConnectedESG = StronglyConnectedBalancedESGUtilities.transformESGtoStronglyConnectedESG(ESG);
		ESGToJgraphConverter esgtoJgraphConverter  = new ESGToJgraphConverter();
		Graph<Vertex, DefaultWeightedEdge> jgraphStronglyConnectedESG = esgtoJgraphConverter.buildDirectedWeightedJGraphFromESG(stronglyConnectedESG);
		
		ChinesePostman<Vertex, DefaultWeightedEdge> CP = new ChinesePostman<Vertex, DefaultWeightedEdge>();
			
		GraphPath<Vertex, DefaultWeightedEdge> eulerCycle = CP.getCPPSolution(jgraphStronglyConnectedESG);
				
		EulerCycleToCompleteEventSequenceGenerator testSuiteGenerator = new EulerCycleToCompleteEventSequenceGenerator();
		
		Set<EventSequence> CESs = testSuiteGenerator.CESgeneratorAlt(eulerCycle);
		
		//CESs.forEach(ces->System.out.println(ces));
		return CESs;
				
	}
	
	

}
