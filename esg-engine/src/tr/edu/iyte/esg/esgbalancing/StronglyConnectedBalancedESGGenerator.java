package tr.edu.iyte.esg.esgbalancing;

import java.util.LinkedHashSet;


import java.util.Set;

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

	    PartitionGeneratorFromStronglyConnectedESG partitionGenerator = new PartitionGeneratorFromStronglyConnectedESG();
	    partitionGenerator.generatePartitions(stronglyConnectedESG);
	    
	    ESGToJgraphConverter jGraphConverter = new ESGToJgraphConverter();
	    Graph<Vertex, Edge> jGraphStronglyConnectedESG =
	        jGraphConverter.buildJGraphFromESG(stronglyConnectedESG);
	    
	    StronglyConnectedBalancedESGUtilities.initShortestPaths(jGraphStronglyConnectedESG);

	    Set<Vertex> positiveDegreeVertexPartitionSet =
	        new LinkedHashSet<>(partitionGenerator.getPositiveDegreeVertexPartitionForBipartiteMatching());

	    Set<Vertex> negativeDegreeVertexPartitionSet =
	        new LinkedHashSet<>(partitionGenerator.getNegativeDegreeVertexPartitionForBipartiteMatching());

	    Graph<Vertex, DefaultWeightedEdge> bipartiteGraph =
	        StronglyConnectedBalancedESGUtilities.generateBipartiteGraph(
	            jGraphStronglyConnectedESG,
	            positiveDegreeVertexPartitionSet,
	            negativeDegreeVertexPartitionSet,
	            partitionGenerator.getCloneToOriginalMap());

	    MatchingAlgorithm.Matching<Vertex, DefaultWeightedEdge> hungarianMatching =
	        StronglyConnectedBalancedESGUtilities.getMatching(
	            bipartiteGraph,
	            new LinkedHashSet<>(partitionGenerator.getPositiveDegreeVertexPartitionForBipartiteMatching()),
	            new LinkedHashSet<>(partitionGenerator.getNegativeDegreeVertexPartitionForBipartiteMatching()));

	    Graph<Vertex, Edge> balancedAndStronglyConnectedESG =
	        BalancedESGGeneratorFromStronglyConnectedJgraph
	            .generateBalancedESGFromStronlgyConnectedESG(
	                jGraphStronglyConnectedESG,
	                hungarianMatching,
	                partitionGenerator.getCloneToOriginalMap());

	    recomputeVertexDegrees(balancedAndStronglyConnectedESG);

	    return balancedAndStronglyConnectedESG;
	}

	
	private static void recomputeVertexDegrees(Graph<Vertex, Edge> graph) {
	    for (Vertex v : graph.vertexSet()) {
	        int in  = graph.incomingEdgesOf(v).size();
	        int out = graph.outgoingEdgesOf(v).size();
	        
	        int deg = in - out;
	        v.setDegree(deg);
	    }
	}

	
}
