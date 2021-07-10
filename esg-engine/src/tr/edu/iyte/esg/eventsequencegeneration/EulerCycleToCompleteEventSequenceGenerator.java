package tr.edu.iyte.esg.eventsequencegeneration;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import java.util.Iterator;
import java.util.List;

import java.util.Set;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import tr.edu.iyte.esg.eventsequence.EventSequence;
import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.Vertex;

public class EulerCycleToCompleteEventSequenceGenerator {

	public EulerCycleToCompleteEventSequenceGenerator() {
	}

	public Set<EventSequence> CESgenerator(GraphPath<Vertex, Edge> eulerCycle) {

		Set<EventSequence> CESsOfESG = new LinkedHashSet<EventSequence>();

		List<Vertex> vertexList = eulerCycle.getVertexList();
		
		//vertexList.forEach(e-> System.out.print(e.getEvent().getName() + " "));System.out.println();

		List<Vertex> copyVertexList = new ArrayList<Vertex>();
		copyVertexList.addAll(vertexList);

		int indexOfFirstPseudoEnd = indexOfFirstPseudoEnd(copyVertexList);


		List<Vertex> modifiedCycle = copyVertexList.subList(indexOfFirstPseudoEnd + 1, copyVertexList.size());
		
		/**
		 * The first vertex is ignored since it is both at the end and start.
		 */
		List<Vertex> lastHalf = copyVertexList.subList(1, indexOfFirstPseudoEnd + 1);

		modifiedCycle.addAll(lastHalf);

		for (int i = 0; i < modifiedCycle.size(); i++) {
			Vertex vertex = modifiedCycle.get(i);
			//System.out.println(vertex.getEvent().getName());

			if (vertex.isPseudoStartVertex()) {
				Iterator<Vertex> cycleIterator = modifiedCycle.listIterator(i);
				ArrayList<Vertex> CES = new ArrayList<Vertex>();

				while (cycleIterator.hasNext()) {
					Vertex eventVertex = (Vertex) cycleIterator.next();

					if (eventVertex.isPseudoEndVertex()) {
						EventSequence completeEventSequence = new EventSequence();

						completeEventSequence.setEventSequence(CES);
						CESsOfESG.add(completeEventSequence);
						break;
					} else if (eventVertex.isPseudoStartVertex()) {
						continue;
					} else {
						CES.add(eventVertex);
					}
				}
			}
		}
		///CESsOfESG.forEach(ces->System.out.println(ces));
		return CESsOfESG;

	}
	
	public Set<EventSequence> CESgeneratorAlt(GraphPath<Vertex, DefaultWeightedEdge> eulerCycle) {

		Set<EventSequence> CESsOfESG = new LinkedHashSet<EventSequence>();

		List<Vertex> vertexList = eulerCycle.getVertexList();

		List<Vertex> copyVertexList = new ArrayList<Vertex>();
		copyVertexList.addAll(vertexList);

		int indexOfFirstPseudoEnd = indexOfFirstPseudoEnd(copyVertexList);


		List<Vertex> modifiedCycle = copyVertexList.subList(indexOfFirstPseudoEnd + 1, copyVertexList.size());
		
		/**
		 * The first vertex is ignored since it is both at the end and start.
		 */
		List<Vertex> lastHalf = copyVertexList.subList(1, indexOfFirstPseudoEnd + 1);

		modifiedCycle.addAll(lastHalf);

		for (int i = 0; i < modifiedCycle.size(); i++) {
			Vertex vertex = modifiedCycle.get(i);

			if (vertex.isPseudoStartVertex()) {
				Iterator<Vertex> cycleIterator = modifiedCycle.listIterator(i);
				ArrayList<Vertex> CES = new ArrayList<Vertex>();

				while (cycleIterator.hasNext()) {
					Vertex eventVertex = (Vertex) cycleIterator.next();

					if (eventVertex.isPseudoEndVertex()) {
						EventSequence completeEventSequence = new EventSequence();

						completeEventSequence.setEventSequence(CES);
						CESsOfESG.add(completeEventSequence);
						break;
					} else if (eventVertex.isPseudoStartVertex()) {
						continue;
					} else {
						CES.add(eventVertex);
					}
				}
			}
		}

		return CESsOfESG;

	}

	public Set<EventSequence> CESgeneratorWithPseudoEvents(GraphPath<Vertex, Edge> eulerCycle) {

		Set<EventSequence> CESsOfESG = new LinkedHashSet<EventSequence>();

		List<Vertex> vertexList = eulerCycle.getVertexList();

		List<Vertex> copyVertexList = new ArrayList<Vertex>();
		copyVertexList.addAll(vertexList);

		int indexOfFirstPseudoEnd = indexOfFirstPseudoEnd(copyVertexList);


		List<Vertex> modifiedCycle = copyVertexList.subList(indexOfFirstPseudoEnd, copyVertexList.size());//copyVertexList.subList(indexOfFirstPseudoEnd, copyVertexList.size());
		
		/**
		 * The first vertex is ignored since it is both at the end and start.
		 */
		List<Vertex> lastHalf = copyVertexList.subList(1, indexOfFirstPseudoEnd + 1);

		modifiedCycle.addAll(lastHalf);

		for (int i = 0; i < modifiedCycle.size(); i++) {
			Vertex vertex = modifiedCycle.get(i);

			if (vertex.isPseudoStartVertex()) {
				Iterator<Vertex> cycleIterator = modifiedCycle.listIterator(i);
				ArrayList<Vertex> CES = new ArrayList<Vertex>();

				while (cycleIterator.hasNext()) {
					Vertex eventVertex = (Vertex) cycleIterator.next();

					if (eventVertex.isPseudoEndVertex()) {
						CES.add(eventVertex);//
						EventSequence completeEventSequence = new EventSequence();

						completeEventSequence.setEventSequence(CES);
						CESsOfESG.add(completeEventSequence);
						break;
					} else if (eventVertex.isPseudoStartVertex()) {
						CES.add(eventVertex);//continue;
					} else {
						CES.add(eventVertex);
					}
				}
			}
		}

		//CESsOfESG.forEach(ces->System.out.println(ces));
		return CESsOfESG;

	}
	
	private int indexOfFirstPseudoEnd(List<Vertex> vertexList) {

		int index = -1;

		for (int i = 0; i < vertexList.size(); i++) {
			if (vertexList.get(i).isPseudoEndVertex()) {
				index = i;
				break;
			}
		}

		return index;
	}

}
