package tr.edu.iyte.esg.model.comparators;

import java.util.Comparator;
import tr.edu.iyte.esg.model.Vertex;

public class VertexComparatorEventNameBased implements Comparator<Vertex> {

	@Override
	public int compare(Vertex vertex0, Vertex vertex1) {
		return vertex0.getEvent().getName().compareTo(vertex1.getEvent().getName());
	}
	
}
