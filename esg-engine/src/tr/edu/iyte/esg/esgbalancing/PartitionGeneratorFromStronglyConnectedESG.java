package tr.edu.iyte.esg.esgbalancing;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Vertex;
import tr.edu.iyte.esg.model.VertexSimple;

public class PartitionGeneratorFromStronglyConnectedESG {

	
	private final List<Vertex> positiveDegreeVertexPartitionForBipartiteMatching;
	private final List<Vertex> negativeDegreeVertexPartitionForBipartiteMatching;
	private final Map<Vertex, Vertex> cloneToOriginalMap;
	private final AtomicInteger cloneIdCounter = new AtomicInteger(1_000_000);

	public PartitionGeneratorFromStronglyConnectedESG() {
		this.positiveDegreeVertexPartitionForBipartiteMatching = new ArrayList<>();
		this.negativeDegreeVertexPartitionForBipartiteMatching = new ArrayList<>();
		this.cloneToOriginalMap = new LinkedHashMap<>();
	}

	public List<Vertex> getPositiveDegreeVertexPartitionForBipartiteMatching() {
//		positiveDegreeVertexPartitionForBipartiteMatching.forEach(e -> System.out.println("Positive partition " + e.getEvent().getName() + " degree=" + e.getDegree() + " id=" + e.getID()));
		return Collections.unmodifiableList(positiveDegreeVertexPartitionForBipartiteMatching);
	}

	public List<Vertex> getNegativeDegreeVertexPartitionForBipartiteMatching() {
//		negativeDegreeVertexPartitionForBipartiteMatching.forEach(e -> System.out.println("Negative partition " + e.getEvent().getName() + " degree=" + e.getDegree() + " id=" + e.getID()));
		return Collections.unmodifiableList(negativeDegreeVertexPartitionForBipartiteMatching);
	}

	/**
	 * Map from each clone used in the bipartite graph to its corresponding original
	 * vertex in the strongly connected ESG.
	 */
	public Map<Vertex, Vertex> getCloneToOriginalMap() {
		return Collections.unmodifiableMap(cloneToOriginalMap);
	}

	public void generatePartitions(ESG stronglyConnectedESG) {
		positiveDegreeVertexPartitionForBipartiteMatching.clear();
		negativeDegreeVertexPartitionForBipartiteMatching.clear();
		cloneToOriginalMap.clear();

		for (Vertex vertex : stronglyConnectedESG.getVertexList()) {
//			System.out.println("Vertex " + vertex.getEvent().getName() + " degree=" + vertex.getDegree());

			int degree = vertex.getDegree();

			if (degree > 0) {
				for (int i = 0; i < degree; i++) {
					Vertex clone = createClone(vertex, +1);
//					System.out.println("  -> Positive copy " + i + " of " + vertex.getEvent().getName() + " id=" + clone.getID());
					positiveDegreeVertexPartitionForBipartiteMatching.add(clone);
					cloneToOriginalMap.put(clone, vertex);
				}
			} else if (degree < 0) {
				for (int i = 0; i < -degree; i++) {
					Vertex clone = createClone(vertex, -1);
//					System.out.println("  -> Negative copy " + i + " of " + vertex.getEvent().getName() + " id=" + clone.getID());
					negativeDegreeVertexPartitionForBipartiteMatching.add(clone);
					cloneToOriginalMap.put(clone, vertex);
				}
			}
		}
	}

	/**
	 * Create a new vertex instance used only in the bipartite graph. 
	 */
	private Vertex createClone(Vertex original, int unitDegree) {
		int newId = cloneIdCounter.getAndIncrement();

		VertexSimple clone = new VertexSimple(newId, original.getEvent());

		clone.setDegree(unitDegree); // ±1 unit

		return clone;
	}
}
