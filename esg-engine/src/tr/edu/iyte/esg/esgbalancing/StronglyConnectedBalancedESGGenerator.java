package tr.edu.iyte.esg.esgbalancing;

import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.MatchingAlgorithm;
import org.jgrapht.graph.DefaultWeightedEdge;

import tr.edu.iyte.esg.conversion.ESGToJgraphConverter;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.Vertex;


public class StronglyConnectedBalancedESGGenerator {

	public StronglyConnectedBalancedESGGenerator() {

	}	
	
	public Graph<Vertex, Edge> generateBalancedAndStronglyConnectedESG(ESG ESG) {
		
		ESG stronglyConnectedESG = StronglyConnectedBalancedESGUtilities.transformESGtoStronglyConnectedESG(ESG);
		
		PartitionGeneratorFromStronglyConnectedESG partitionGeneratorFromStronglyConnectedESG = new PartitionGeneratorFromStronglyConnectedESG();
		
		partitionGeneratorFromStronglyConnectedESG.generatePartitions(stronglyConnectedESG);
		ESGToJgraphConverter jGraphConverter = new ESGToJgraphConverter();
		Graph<Vertex, Edge> jGraphStronglyConnectedESG = jGraphConverter.buildJGraphFromESG(stronglyConnectedESG);
		

		StronglyConnectedBalancedESGUtilities.setFloydWarshallShortestPaths(jGraphStronglyConnectedESG);
		
		Graph<Vertex, DefaultWeightedEdge> bipartiteGraph = StronglyConnectedBalancedESGUtilities.generateBipartiteGraph(jGraphStronglyConnectedESG,
						partitionGeneratorFromStronglyConnectedESG
								.getPositiveDegreeVertexPartitionForBipartiteMatching(),
						partitionGeneratorFromStronglyConnectedESG
								.getNegativeDegreeVertexPartitionForBipartiteMatching());
		


		MatchingAlgorithm.Matching<Vertex, DefaultWeightedEdge> hungarianMatching = StronglyConnectedBalancedESGUtilities
				.getMatching(bipartiteGraph,
						partitionGeneratorFromStronglyConnectedESG
								.getPositiveDegreeVertexPartitionForBipartiteMatching(),
						partitionGeneratorFromStronglyConnectedESG
								.getNegativeDegreeVertexPartitionForBipartiteMatching());


		Graph<Vertex, Edge> balancedAndStronglyConnectedESG = BalancedESGGeneratorFromStronglyConnectedJgraph
				.generateBalancedESGFromStronlgyConnectedESG(jGraphStronglyConnectedESG, hungarianMatching);

		return balancedAndStronglyConnectedESG;
	}
	


}
