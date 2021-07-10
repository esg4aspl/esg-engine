package tr.edu.iyte.esg.esgbalancing;
import java.util.LinkedHashSet;
import java.util.Set;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Vertex;
import tr.edu.iyte.esg.model.VertexSimple;

public class PartitionGeneratorFromStronglyConnectedESG {

	private Set<Vertex> positiveDegreeVertexPartitionForBipartiteMatching;
	private Set<Vertex> negativeDegreeVertexPartitionForBipartiteMatching;

	//private List<Vertex> positiveDegreeVertexPartitionForCostMatrix;
	//private List<Vertex> negativeDegreeVertexPartitionForCostMatrix;

	public PartitionGeneratorFromStronglyConnectedESG() {
		positiveDegreeVertexPartitionForBipartiteMatching = new LinkedHashSet<Vertex>();
		negativeDegreeVertexPartitionForBipartiteMatching = new LinkedHashSet<Vertex>();
		//positiveDegreeVertexPartitionForCostMatrix = new ArrayList<Vertex>();
		//negativeDegreeVertexPartitionForCostMatrix = new ArrayList<Vertex>();
	}

	public Set<Vertex> getPositiveDegreeVertexPartitionForBipartiteMatching() {

		return positiveDegreeVertexPartitionForBipartiteMatching;
	}

	public Set<Vertex> getNegativeDegreeVertexPartitionForBipartiteMatching() {
		return negativeDegreeVertexPartitionForBipartiteMatching;
	}
/*
	public List<Vertex> getPositiveDegreeVertexPartitionForCostMatrix() {
		return positiveDegreeVertexPartitionForCostMatrix;
	}

	public List<Vertex> getNegativeDegreeVertexPartitionForCostMatrix() {
		return negativeDegreeVertexPartitionForCostMatrix;
	}
*/
	public void generatePartitions(ESG stronglyConnectedESG) {
		for (Vertex vertex : stronglyConnectedESG.getVertexList()) {

			if (vertex.getDegree() > 0) {
				int positiveDegree = vertex.getDegree();

				if (positiveDegree == 1) {
					positiveDegreeVertexPartitionForBipartiteMatching.add(vertex);
					//positiveDegreeVertexPartitionForCostMatrix.add(vertex);
				} else {
					positiveDegreeVertexPartitionForBipartiteMatching.add(vertex);
					//positiveDegreeVertexPartitionForCostMatrix.add(vertex);
					positiveDegree--;

					while (positiveDegree > 0) {
						Vertex repititiveVertex = new VertexSimple(vertex.getID(), vertex.getEvent());
						repititiveVertex.setDegree(vertex.getDegree());

						positiveDegreeVertexPartitionForBipartiteMatching.add(repititiveVertex);
						//positiveDegreeVertexPartitionForCostMatrix.add(vertex);
						positiveDegree--;
					}
				}
			} else if (vertex.getDegree() < 0) {
				int negativeDegree = vertex.getDegree();

				if (vertex.getDegree() == -1) {
					negativeDegreeVertexPartitionForBipartiteMatching.add(vertex);
					//negativeDegreeVertexPartitionForCostMatrix.add(vertex);
				} else {
					negativeDegreeVertexPartitionForBipartiteMatching.add(vertex);
					//negativeDegreeVertexPartitionForCostMatrix.add(vertex);
					negativeDegree++;

					while (negativeDegree < 0) {
						Vertex repititiveVertex = new VertexSimple(vertex.getID(), vertex.getEvent());
						repititiveVertex.setDegree(vertex.getDegree());

						negativeDegreeVertexPartitionForBipartiteMatching.add(repititiveVertex);
						//negativeDegreeVertexPartitionForCostMatrix.add(vertex);
						negativeDegree++;
					}
				}
			}
		}
	}

}
